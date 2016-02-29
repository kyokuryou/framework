package org.smarty.security.config;

import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.config.statement.SecurityFilterStatement;
import org.smarty.web.config.WebConfigurer;
import org.smarty.web.config.WebInitializer;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Security Initializer
 */
public abstract class SecurityInitializer<T extends SecurityBuilder> extends WebInitializer<T> {

	@Override
	public void onInitialize() throws Exception {
		super.onInitialize();
	}

	@Override
	protected WebApplicationContext createApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(SecurityConfigurer.class);
		String cl = getConfigLocation();
		if (!ObjectUtil.isEmpty(cl)) {
			applicationContext.setConfigLocation(cl);
		}
		return applicationContext;
	}

	protected String getSecurityChainMapping() {
		return "/admin/*";
	}

	protected String getSecurityCaptchaMapping() {
		return "/admin/loginVerify";
	}

	@Override
	protected void customizeBuilder(T contextBuilder) {
		super.customizeBuilder(contextBuilder);
		String scm = getSecurityCaptchaMapping();
		if (!ObjectUtils.isEmpty(scm)) {
			contextBuilder.addFilter(new SecurityFilterStatement(SecurityConfigurer.SECURITY_CAPTCHA_FILTER_NAME, scm));
		}
		contextBuilder.addFilter(getSecurityFilterChain());
	}

	private SecurityFilterStatement getSecurityFilterChain() {
		String sfcm = getSecurityChainMapping();
		Assert.notNull(sfcm, "[Assertion failed] - getSecurityChainMapping() is required; it must not be null");
		return new SecurityFilterStatement(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, getSecurityChainMapping());
	}
}
