package org.smarty.core.security.common;

import org.smarty.core.bean.SystemConfig;
import org.smarty.core.security.service.SecurityService;
import org.smarty.core.utils.LogicUtil;
import org.smarty.core.utils.SystemConfigUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Date;
import java.util.Map;

/**
 * 监听器 - 后台登录成功、登录失败处理
 */
public class SecurityListener implements ApplicationListener {
    private SecurityService securityService;

    public void onApplicationEvent(ApplicationEvent event) {
        // 登录成功：记录登录IP、清除登录失败次数
        if (event instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
            successLogin((Authentication) authEvent.getSource());
        }


        // 登录失败：增加登录失败次数
        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent authEvent = (AuthenticationFailureBadCredentialsEvent) event;
            failLogin((Authentication) authEvent.getSource());
        }
    }

    /**
     * 登录成功
     */
    private void successLogin(Authentication authentication) {
        String loginIp = ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
        String username = authentication.getName();
        Map<String, Object> admin = securityService.getAdmByUserName(username);

        if (!(Boolean) admin.get("is_locked")) {
            admin.put("login_ip", loginIp);
            admin.put("login_date", new Date());
            admin.put("login_failure_count", 0);
        }
        securityService.updateAdm(admin);
    }

    /**
     * 登录失败
     */
    private void failLogin(Authentication authentication) {
        String username = authentication.getName();
        Map<String, Object> admin = securityService.getAdmByUserName(username);

        if (LogicUtil.isEmptyMap(admin) || !(Boolean) admin.get("is_locked")) {
            return;
        }
        SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
        int lfc = (Integer) admin.get("login_failure_count") + 1;

        if (lfc >= systemConfig.getLoginFailureLockCount()) {
            admin.put("is_locked", true);
            admin.put("locked_date", new Date());
        }
        admin.put("login_failure_count", lfc);
        securityService.updateAdm(admin);
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}