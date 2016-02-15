package org.smarty.security.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.SpringUtil;
import org.smarty.security.filter.SecurityFilter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * Security工具
 */
public class SecurityUtil {
	private static Log logger = LogFactory.getLog(SecurityUtil.class);

	/**
	 * 刷新SpringSecurity权限信息
	 */
	public static void flushSpringSecurity() {
		try {
			FilterInvocationSecurityMetadataSource factoryBean = SpringUtil.getBean("securityDefinitionSource", FilterInvocationSecurityMetadataSource.class);
			SecurityFilter sf = SpringUtil.getBean("securityFilter", SecurityFilter.class);
			if (sf == null) {
				return;
			}
			sf.setSecurityMetadataSource(factoryBean);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
