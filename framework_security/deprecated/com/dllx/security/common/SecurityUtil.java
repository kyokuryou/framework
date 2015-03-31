package com.dllx.security.common;

import com.dllx.logger.RuntimeLogger;
import com.dllx.security.bean.Role;
import com.dllx.utils.LogicUtil;
import com.dllx.utils.SpringUtil;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.List;

/**
 * Security工具
 */
public class SecurityUtil {
    private static RuntimeLogger logger = new RuntimeLogger(SecurityUtil.class);

    /**
     * 刷新SpringSecurity权限信息
     */
    public static void flushSpringSecurity() {
        try {
            FilterInvocationSecurityMetadataSource factoryBean = SpringUtil.getBean("securityDefinitionSource", FilterInvocationSecurityMetadataSource.class);
            SecurityFilter securityFilter = SpringUtil.getBean("securityFilter", SecurityFilter.class);
            securityFilter.setSecurityMetadataSource(factoryBean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 获取权限字符串（以分隔符间隔）
     */
    public static String toRoleString(List<Role> roleList) {
        StringBuilder stringBuffer = new StringBuilder();
        if(LogicUtil.isEmptyCollection(roleList)) {
            return "";
        }
        for (Role role : roleList) {
            stringBuffer.append(",").append(role.getValue());
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(0);
        }
        return stringBuffer.toString();
    }
}
