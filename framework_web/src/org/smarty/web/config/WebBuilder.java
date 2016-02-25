package org.smarty.web.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.smarty.core.config.ConfigBuilder;
import org.smarty.core.config.statement.ConfigStatement;
import org.smarty.web.config.statement.FilterStatement;
import org.smarty.web.config.statement.ListenerStatement;
import org.smarty.web.config.statement.ServletStatement;
import org.springframework.util.Assert;

/**
 * FilterStatement
 */
public class WebBuilder extends ConfigBuilder {
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void addServletListener(ListenerStatement statement) {
		addStatement(statement);
	}

	public void addServlet(ServletStatement statement) {
		addStatement(statement);
	}

	public void addFilter(FilterStatement statement) {
		addStatement(statement);
	}

	protected void build(ConfigStatement contextStatement) {
		super.build(contextStatement);
		if (contextStatement instanceof ListenerStatement) {
			ListenerStatement statement = (ListenerStatement) contextStatement;
			servletContext.addListener(statement.getTarget());
		}
		if (contextStatement instanceof ServletStatement) {
			ServletStatement statement = (ServletStatement) contextStatement;
			ServletRegistration.Dynamic srd = servletContext.addServlet(statement.getTargetName(), statement.getTarget());
			Assert.notNull(srd);
			srd.setLoadOnStartup(statement.getOnStartup());
			srd.addMapping(statement.getMapping());
			srd.setAsyncSupported(statement.isAsyncSupported());
		}
		if (contextStatement instanceof FilterStatement) {
			FilterStatement statement = (FilterStatement) contextStatement;
			FilterRegistration.Dynamic fd = servletContext.addFilter(statement.getTargetName(), statement.getTarget());
			fd.setAsyncSupported(statement.isAsyncSupported());
			fd.addMappingForUrlPatterns(statement.getDispatcherTypes(), statement.isBeforeFilter(), statement.getUrlPattern());
		}
	}
}