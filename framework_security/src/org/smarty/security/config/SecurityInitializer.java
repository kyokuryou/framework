package org.smarty.security.config;

import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.config.build.SecurityBuilder;
import org.smarty.security.config.statement.SecurityFilterStatement;
import org.smarty.web.config.WebInitializer;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Security Initializer
 */
public abstract class SecurityInitializer<T extends SecurityBuilder> extends WebInitializer<T> {

	@Override
	public void onInitialize() throws Exception {
		super.onInitialize();
	}

	@Override
	protected Class<?> getAnnotatedClasses() {
		return SecurityConfig.class;
	}

	protected String[] getSecurityChainMapping() {
		return new String[]{"/*"};
	}

	@Override
	protected void customizeBuilder(T contextBuilder) {
		super.customizeBuilder(contextBuilder);
		contextBuilder.addFilter(getSecurityFilterChain());
	}

	private SecurityFilterStatement getSecurityFilterChain() {
		String[] sfcm = getSecurityChainMapping();
		ObjectUtil.assertNotEmpty(sfcm, "[Assertion failed] - getSecurityChainMapping() is required; it must not be null");
		SecurityFilterStatement sfs = new SecurityFilterStatement(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
		sfs.setUrlPattern(sfcm);
		return sfs;
	}
}
