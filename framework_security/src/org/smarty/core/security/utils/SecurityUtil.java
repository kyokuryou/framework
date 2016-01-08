package org.smarty.core.security.utils;

import org.smarty.core.io.RuntimeLogger;
import org.smarty.core.security.common.SecurityFilter;
import org.smarty.core.utils.LogicUtil;
import org.smarty.core.utils.SpringUtil;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.List;
import java.util.Map;

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
    public static String toRoleString(List<Map<String,Object>> roleList) {
        StringBuilder stringBuffer = new StringBuilder();
        if(LogicUtil.isEmptyCollection(roleList)) {
            return "";
        }
        for (Map<String,Object> role : roleList) {
            stringBuffer.append(",").append(role.get("value"));
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(0);
        }
        return stringBuffer.toString();
    }
}
