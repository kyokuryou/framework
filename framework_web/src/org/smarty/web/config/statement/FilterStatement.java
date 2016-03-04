package org.smarty.web.config.statement;

import javax.servlet.Filter;
import org.smarty.core.utils.ObjectUtil;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * FilterStatement
 */
public class FilterStatement extends WebStatement<Filter> {
	private String[] servletName;
	private String[] urlPattern;
	private boolean beforeFilter = false;

	public FilterStatement(Filter filter) {
		super(filter);
	}

	public FilterStatement(String targetName) {
		super(targetName);
	}

	public FilterStatement(String targetName, Filter filter) {
		super(targetName, filter);
		ObjectUtil.assertNotEmpty(urlPattern, "urlPattern must not be null or empty");
	}

	public void setBeforeFilter(boolean beforeFilter) {
		this.beforeFilter = beforeFilter;
	}

	public void setServletName(String... servletName) {
		this.servletName = servletName;
	}

	public void setUrlPattern(String... urlPattern) {
		this.urlPattern = urlPattern;
	}

	public String[] getUrlPattern() {
		return urlPattern;
	}

	public String[] getServletName() {
		return servletName;
	}

	public boolean isBeforeFilter() {
		return beforeFilter;
	}

	@Override
	public Filter getTarget(String targetName) {
		DelegatingFilterProxy jsldfp = new DelegatingFilterProxy(targetName);
		jsldfp.setTargetFilterLifecycle(true);
		return jsldfp;
	}
}