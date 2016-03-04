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

	protected void configRequestFilter(MultiRequestFilter multiRequestFilter) {
		multiRequestFilter.addRequestFilter(CaptchaBuilderFilter.class);
		multiRequestFilter.addRequestFilter(JSLocaleFilter.class);
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
		contextBuilder.addFilter(createRequestFilter());
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

	private FilterStatement createRequestFilter() {
		MultiRequestFilter mrf = new MultiRequestFilter();
		CharacterEncodingFilter cef = new CharacterEncodingFilter();
		cef.setEncoding(WebBaseConstant.STR_CHARSET);
		cef.setForceEncoding(true);
		mrf.addFilter(cef);
		configRequestFilter(mrf);

		FilterStatement mrfs = new FilterStatement(mrf);
		mrfs.setServletName(DISPATCHER_SERVLET_NAME);
		return mrfs;
	}
}
