package org.smarty.security.common;

import org.smarty.security.config.SecurityInitializer;
import org.smarty.security.config.SecurityBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Test Initializer
 */
public class TestInitializer extends SecurityInitializer<SecurityBuilder> {

	@Override
	protected WebApplicationContext createApplicationContext() {
		XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
		applicationContext.setConfigLocation("classpath:spring.xml");
		return applicationContext;
	}

	protected WebApplicationContext createServletApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		return applicationContext;
	}

	@Override
	protected String[] getDispatcherServletMapping() {
		return new String[]{"*.do"};
	}

	@Override
	protected String getEncodingMapping() {
		return "/*";
	}

	@Override
	protected String getCaptchaMapping() {
		return "/captcha.jpg";
	}

	@Override
	protected String getJsLocaleMapping() {
		return "/";
	}

	@Override
	protected String getSecurityChainMapping() {
		return "/*";
	}

	@Override
	protected String getSecurityCaptchaMapping() {
		return "/admin/loginVerify";
	}

	@Override
	protected SecurityBuilder createContextBuilder() {
		return new SecurityBuilder();
	}
}
