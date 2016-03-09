package org.smarty.security.config;

import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.config.statement.SecFilterStatement;
import org.smarty.web.config.WebInitializer;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Security Initializer
 */
public abstract class SecInitializer<T extends SecBuilder> extends WebInitializer<T> {

	@Override
	public void onInitialize() throws Exception {
		super.onInitialize();
	}

	@Override
	protected Class<?> getAnnotatedClasses() {
		return SecConfigurer.class;
	}

	protected String[] getSecurityChainMapping() {
		return new String[]{"/*"};
	}

	@Override
	protected void customizeBuilder(T contextBuilder) {
		super.customizeBuilder(contextBuilder);
		contextBuilder.addFilter(getSecurityFilterChain());
	}

	private SecFilterStatement getSecurityFilterChain() {
		String[] sfcm = getSecurityChainMapping();
		ObjectUtil.assertNotEmpty(sfcm, "[Assertion failed] - getSecurityChainMapping() is required; it must not be null");
		SecFilterStatement sfs = new SecFilterStatement(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
		sfs.setUrlPattern(getSecurityChainMapping());
		return sfs;
	}
}
