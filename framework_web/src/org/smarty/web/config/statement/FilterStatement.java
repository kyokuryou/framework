package org.smarty.web.config.statement;

import javax.servlet.Filter;
import org.springframework.util.Assert;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * FilterStatement
 */
public class FilterStatement extends WebStatement<Filter> {
	private String urlPattern;
	private boolean beforeFilter = false;

	public FilterStatement(Filter object, String urlPattern) {
		super(object);
		this.urlPattern = urlPattern;
	}

	public FilterStatement(String targetName, String urlPattern) {
		super(targetName);
		this.urlPattern = urlPattern;
	}

	public FilterStatement(String targetName, Filter filter, String urlPattern) {
		super(targetName, filter);
		Assert.hasText(urlPattern);
		this.urlPattern = urlPattern;
	}

	public void setBeforeFilter(boolean beforeFilter) {
		this.beforeFilter = beforeFilter;
	}


	public String getUrlPattern() {
		return urlPattern;
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