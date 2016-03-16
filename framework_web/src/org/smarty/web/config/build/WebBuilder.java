package org.smarty.web.config.build;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.smarty.core.config.build.SystemBuilder;
import org.smarty.core.config.statement.ConfigStatement;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.web.config.statement.FilterStatement;
import org.smarty.web.config.statement.ListenerStatement;
import org.smarty.web.config.statement.ServletStatement;
import org.springframework.util.Assert;

/**
 * FilterStatement
 */
public class WebBuilder extends SystemBuilder {
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

	protected void build(ConfigStatement<?> contextStatement) {
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
			String[] url = statement.getUrlPattern();
			String[] sn = statement.getServletName();
			if (!ObjectUtil.isEmpty(url)) {
				fd.addMappingForUrlPatterns(statement.getDispatcherTypes(), statement.isBeforeFilter(), url);
			} else if (!ObjectUtil.isEmpty(sn)) {
				fd.addMappingForServletNames(statement.getDispatcherTypes(), statement.isBeforeFilter(), sn);
			} else {
				ObjectUtil.assertExpression(false, "UrlPattern or ServletName between must null or empty");
			}
		}
	}
}