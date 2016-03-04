package org.smarty.web.support.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.core.utils.SpringUtil;

/**
 * LinkedFilter
 */
public final class MultiRequestFilter implements Filter {
	private final List<Filter> filters = new ArrayList<Filter>();
	private final List<Class<? extends SingleRequestFilter>> filterClass = new ArrayList<Class<? extends SingleRequestFilter>>();

	public void addFilter(Filter filter) {
		this.filters.add(filter);
	}

	public void addRequestFilter(Class<? extends SingleRequestFilter> filterClass) {
		this.filterClass.add(filterClass);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (ObjectUtil.isEmpty(filters)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		VirtualFilterChain vfc = new VirtualFilterChain(filterChain);
		vfc.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initFilterBean();
		for (Filter filter : filters) {
			filter.init(filterConfig);
		}
	}

	@Override
	public void destroy() {
		if (ObjectUtil.isEmpty(filters)) {
			return;
		}
		for (Filter filter : filters) {
			filter.destroy();
		}
	}

	private void initFilterBean() {
		if (ObjectUtil.isEmpty(filterClass)) {
			return;
		}
		List<SingleRequestFilter> srl = new ArrayList<SingleRequestFilter>();
		for (Class<? extends SingleRequestFilter> klass : filterClass) {
			srl.add(SpringUtil.createBean(klass));
		}
		if (!ObjectUtil.isEmpty(srl)) {
			Collections.sort(srl);
		}
		filters.addAll(srl);
	}

	private class VirtualFilterChain implements FilterChain {
		private final FilterChain originalChain;
		private final int size = filters.size();
		private int currentPosition = 0;

		private VirtualFilterChain(FilterChain chain) {
			this.originalChain = chain;
		}

		public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
			if (currentPosition == size) {
				originalChain.doFilter(request, response);
			} else {
				currentPosition++;
				Filter nextFilter = filters.get(currentPosition - 1);
				nextFilter.doFilter(request, response, this);
			}
		}
	}
}
