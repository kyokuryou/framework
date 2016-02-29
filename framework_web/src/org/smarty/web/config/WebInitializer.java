package org.smarty.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.smarty.core.common.AbsContextInitializer;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.web.commons.WebBaseConstant;
import org.smarty.web.config.statement.FilterStatement;
import org.smarty.web.config.statement.ListenerStatement;
import org.smarty.web.config.statement.ServletStatement;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

/**
 * Web Initializer
 */
public abstract class WebInitializer<T extends WebBuilder> extends AbsContextInitializer<WebApplicationContext, T> implements WebApplicationInitializer {

	public static final String DISPATCHER_SERVLET_NAME = "dispatcher";
	public static final String ENCODING_FILTER_NAME = "encodingFilter";

	@Override
	public void onInitialize() throws Exception {
		super.onInitialize();
	}

	@Override
	protected WebApplicationContext createApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WebConfigurer.class);
		String cl = getConfigLocation();
		if (!ObjectUtil.isEmpty(cl)) {
			applicationContext.setConfigLocation(cl);
		}
		return applicationContext;
	}

	protected final WebApplicationContext createServletApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		String cl = getServletConfigLocation();
		if (!ObjectUtil.isEmpty(cl)) {
			applicationContext.setConfigLocation(cl);
		}
		return applicationContext;
	}

	protected String getConfigLocation() {
		return null;
	}

	protected String getServletConfigLocation() {
		return null;
	}

	protected String[] getDispatcherServletMapping() {
		return new String[]{"/"};
	}

	protected String getUrlMapping() {
		return "/*";
	}

	protected String getCaptchaMapping() {
		return "/captcha.jpg";
	}

	protected String getJsLocaleMapping() {
		return "/";
	}

	@Override
	public final void onStartup(ServletContext servletContext) throws ServletException {
		ContextLoaderListener listener = new ContextLoaderListener(applicationContext);
		listener.setContextInitializers(getApplicationContextInitializers());

		contextBuilder.setServletContext(servletContext);

		contextBuilder.addServletListener(new ListenerStatement(listener));
		contextBuilder.addServletListener(new ListenerStatement(new IntrospectorCleanupListener()));
		contextBuilder.addServletListener(new ListenerStatement(new RequestContextListener()));
		contextBuilder.addServlet(getDispatcherServlet());
		contextBuilder.addFilter(getCharacterEncodingFilter());

		customizeBuilder(contextBuilder);
		contextBuilder.apply();
	}

	protected void customizeBuilder(T contextBuilder) {
		String cm = getCaptchaMapping();
		if (!ObjectUtil.isEmpty(cm)) {
			contextBuilder.addFilter(new FilterStatement(WebConfigurer.CAPTCHA_BUILDER_FILTER_NAME, cm));
		}
		String jslm = getJsLocaleMapping();
		if (!ObjectUtil.isEmpty(jslm)) {
			contextBuilder.addFilter(new FilterStatement(WebConfigurer.JS_LOCALE_FILTER_NAME, jslm));
		}
	}

	private ServletStatement getDispatcherServlet() {
		WebApplicationContext servletAppContext = createServletApplicationContext();
		Assert.notNull(servletAppContext, "[Assertion failed] - createServletApplicationContext() is required; it must not be null");
		String[] dsm = getDispatcherServletMapping();
		Assert.notEmpty(dsm, "[Assertion failed] - getDispatcherServletMapping() is required; it must not be null");
		FrameworkServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		return new ServletStatement(DISPATCHER_SERVLET_NAME, dispatcherServlet, dsm);
	}

	private FilterStatement getCharacterEncodingFilter() {
		String um = getUrlMapping();
		Assert.hasText(um, "[Assertion failed] - getUrlMapping() is required; it must not be null, empty, or blank");

		CharacterEncodingFilter cef = new CharacterEncodingFilter();
		cef.setEncoding(WebBaseConstant.STR_CHARSET);
		cef.setForceEncoding(true);
		return new FilterStatement(ENCODING_FILTER_NAME, cef, um);
	}
}
