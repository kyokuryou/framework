package org.smarty.web.support.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.core.utils.RegexUtil;

/**
 * RequestMatcherFilter
 */
public abstract class SingleRequestFilter implements Filter, Comparable<SingleRequestFilter> {
	private FilterProcessUrlRequestMatcher requestMatcher;

	protected abstract String getFilterProcessesUrl();

	protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

	public int getOrder() {
		return Integer.MAX_VALUE;
	}

	@Override
	public final void init(FilterConfig filterConfig) throws ServletException {
		instantiate();
	}

	@Override
	public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (requestMatcher == null || requestMatcher.matches(request)) {
			doFilterInternal(request, response, filterChain);
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	protected void instantiate(){
		this.requestMatcher = new FilterProcessUrlRequestMatcher(getFilterProcessesUrl());
	}

	@Override
	public void destroy() {

	}

	@Override
	public final int compareTo(SingleRequestFilter filter) {
		if (filter == null) {
			return -1;
		}
		return Integer.valueOf(getOrder()).compareTo(filter.getOrder());
	}

	private static final class FilterProcessUrlRequestMatcher {
		private final String filterProcessesUrl;

		private FilterProcessUrlRequestMatcher(String filterProcessesUrl) {
			ObjectUtil.assertNotEmpty(filterProcessesUrl, "filterProcessesUrl must be specified");
			ObjectUtil.assertExpression(isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl + " isn't a valid redirect URL");
			this.filterProcessesUrl = filterProcessesUrl;
		}

		public boolean matches(HttpServletRequest request) {
			String uri = request.getRequestURI();
			int pathParamIndex = uri.indexOf(';');

			if (pathParamIndex > 0) {
				// strip everything from the first semi-colon
				uri = uri.substring(0, pathParamIndex);
			}

			int queryParamIndex = uri.indexOf('?');

			if (queryParamIndex > 0) {
				// strip everything from the first question mark
				uri = uri.substring(0, queryParamIndex);
			}

			if ("".equals(request.getContextPath())) {
				return uri.endsWith(filterProcessesUrl);
			}

			return uri.endsWith(request.getContextPath() + filterProcessesUrl);
		}

		public static boolean isValidRedirectUrl(String url) {
			return url != null && url.startsWith("/") || RegexUtil.isAbsoluteUrl(url);
		}
	}
}
