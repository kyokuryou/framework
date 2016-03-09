package org.smarty.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.smarty.core.common.AbsContextInitializer;
import org.smarty.web.commons.WebBaseConstant;
import org.smarty.web.config.statement.FilterStatement;
import org.smarty.web.config.statement.ListenerStatement;
import org.smarty.web.config.statement.ServletStatement;
import org.smarty.web.support.filter.CaptchaBuilderFilter;
import org.smarty.web.support.filter.JSLocaleFilter;
import org.smarty.web.support.filter.MultiRequestFilter;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

/**
 * Web Initializer
 */
public abstract class WebInitializer<T extends WebBuilder> extends AbsContextInitializer<WebApplicationContext, T> implements WebApplicationInitializer {

	public static final String DISPATCHER_SERVLET_NAME = "dispatcher";
	public static final String ENCODING_FILTER_NAME = "encodingFilter";
	public static final String CONTEXT_FILTER_NAME = "contextFilter";

	@Override
	public void onInitialize() throws Exception {
		super.onInitialize();
	}

	@Override
	protected final WebApplicationContext createApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(getAnnotatedClasses());
		return applicationContext;
	}

	protected final WebApplicationContext createServletApplicationContext() {
		return new AnnotationConfigWebApplicationContext();
	}

	protected Class<?> getAnnotatedClasses() {
		return WebConfigurer.class;
	}

	protected String[] getDispatcherServletMapping() {
		return new String[]{"/"};
	}

	protected void configCharacterEncoding(MultiRequestFilter multiRequestFilter) {
		multiRequestFilter.addRequestFilter(CaptchaBuilderFilter.class);
		multiRequestFilter.addRequestFilter(JSLocaleFilter.class);
	}

	protected void configRequestContext(MultiRequestFilter multiRequestFilter) {

	}

	@Override
	public final void onStartup(ServletContext servletContext) throws ServletException {
		ContextLoaderListener listener = new ContextLoaderListener(applicationContext);
		listener.setContextInitializers(getApplicationContextInitializers());

		contextBuilder.setServletContext(servletContext);

		contextBuilder.addServletListener(new ListenerStatement(listener));
		contextBuilder.addServletListener(new ListenerStatement(new IntrospectorCleanupListener()));
		contextBuilder.addServlet(getDispatcherServlet());
		contextBuilder.addFilter(getCharacterEncoding());
		contextBuilder.addFilter(getRequestContext());
		customizeBuilder(contextBuilder);
		contextBuilder.apply();
	}

	protected void customizeBuilder(T contextBuilder) {

	}

	private ServletStatement getDispatcherServlet() {
		WebApplicationContext servletAppContext = createServletApplicationContext();
		Assert.notNull(servletAppContext, "[Assertion failed] - createServletApplicationContext() is required; it must not be null");
		String[] dsm = getDispatcherServletMapping();
		Assert.notEmpty(dsm, "[Assertion failed] - getDispatcherServletMapping() is required; it must not be null");
		FrameworkServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		ServletStatement dsss = new ServletStatement(DISPATCHER_SERVLET_NAME, dispatcherServlet);
		dsss.setMapping(dsm);
		return dsss;
	}

	private FilterStatement getCharacterEncoding() {
		// CharacterEncodingFilter
		CharacterEncodingFilter cef = new CharacterEncodingFilter();
		cef.setEncoding(WebBaseConstant.STR_CHARSET);
		cef.setForceEncoding(true);
		// default filter
		MultiRequestFilter mrf = new MultiRequestFilter();
		mrf.addFilter(cef);
		// config
		configCharacterEncoding(mrf);
		// filter creator
		FilterStatement mrfs = new FilterStatement(ENCODING_FILTER_NAME, mrf);
		mrfs.setServletName(DISPATCHER_SERVLET_NAME);
		return mrfs;
	}

	private FilterStatement getRequestContext() {
		// RequestContextFilter
		RequestContextFilter rcf = new RequestContextFilter();
		// default filter
		MultiRequestFilter mrf = new MultiRequestFilter();
		mrf.addFilter(rcf);
		// config
		configRequestContext(mrf);
		// filter creator
		FilterStatement mrfs = new FilterStatement(CONTEXT_FILTER_NAME, mrf);
		mrfs.setServletName(DISPATCHER_SERVLET_NAME);
		return mrfs;
	}
}
