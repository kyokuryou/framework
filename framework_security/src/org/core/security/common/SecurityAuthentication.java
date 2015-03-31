package org.core.security.common;

import org.core.bean.SystemConfig;
import org.core.security.bean.UserSecurity;
import org.core.security.service.SecurityService;
import org.core.utils.DateUtil;
import org.core.utils.LogicUtil;
import org.core.utils.SystemConfigUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 后台权限认证
 */
public class SecurityAuthentication implements UserDetailsService {
    private SecurityService securityService;

    public UserSecurity loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Map<String, Object> admin = securityService.getAdmByUserName(username);
        if (LogicUtil.isEmptyMap(admin)) {
            throw new UsernameNotFoundException("管理员[" + username + "]不存在!");
        }
        // 解除管理员账户锁定
        unLockAdm(admin);


        List<GrantedAuthority> gas = getGrantedAuthorities(admin.get("pk_id").toString());
        return new UserSecurity(admin, gas);
    }

    /**
     * 解除管理员账户锁定
     */
    public void unLockAdm(Map<String, Object> admin) {
        SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
        if (!(Boolean) admin.get("is_locked")) {
            return;
        }
        int lft = systemConfig.getLoginFailureLockTime();
        if (lft == 0) {
            return;
        }
        Date ld = (Date) admin.get("locked_date");
        Date nlt = DateUtil.addMinute(ld, lft);
        if (nlt.before(new Date())) {
            admin.put("login_failure_count", 0);
            admin.put("is_locked", false);
            admin.put("locked_date", null);
            securityService.updateAdm(admin);
        }
    }

    /**
     * 获得管理角色List
     *
     * @param adminId 管理员ID
     * @return 角色List
     */
    public List<GrantedAuthority> getGrantedAuthorities(String adminId) {
        List<GrantedAuthority> grantedAuthorities = new LinkedList<GrantedAuthority>();
        List<Map<String, Object>> roles = securityService.getRolListByAdmin(adminId);
        for (Map<String, Object> role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.get("value").toString()));
        }
        return grantedAuthorities;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}