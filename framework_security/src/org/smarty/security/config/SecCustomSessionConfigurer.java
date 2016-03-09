package org.smarty.security.config;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.smarty.core.common.DynamicProxyHandler;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.GenericFilterBean;

/**
 * SecCustomLoginConfigurer
 */
public class SecCustomSessionConfigurer<H extends HttpSecurityBuilder<H>> extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, H> {
	private final SessionManagementConfigurer<H> sessionManagementConfigurer = new SessionManagementConfigurer<H>();
	private final SessionRegistry sessionRegistry = new SessionRegistryImpl();
	private InvalidSessionStrategy invalidSessionStrategy;

	public SessionManagementConfigurer<H> sessionManagement() {
		return this.sessionManagementConfigurer;
	}

	public SessionManagementConfigurer<H>.ConcurrencyControlConfigurer maximumSessions(int maximumSessions) {
		return sessionManagementConfigurer.maximumSessions(maximumSessions).sessionRegistry(sessionRegistry);
	}

	public void invalidSessionStrategy(InvalidSessionStrategy invalidSessionStrategy) {
		this.invalidSessionStrategy = invalidSessionStrategy;
	}

	@Override
	public void init(H http) throws Exception {
		sessionManagementConfigurer.init(http);
	}

	@Override
	public void configure(H http) throws Exception {
		HttpSecurityHandler<H> hsh = new HttpSecurityHandler<H>();
		sessionManagementConfigurer.configure(hsh.bind(http));
	}

	private final class HttpSecurityHandler<H extends HttpSecurityBuilder<H>> extends DynamicProxyHandler<H> {

		@Override
		public Object invokeBean(H bean, Method method, Object[] args) throws Throwable {
			String mn = method.getName();
			if (!"addFilter".equals(mn)) {
				return super.invokeBean(bean, method, args);
			}
			if (isSessionManagementFilter(args[0])) {
				SessionManagementFilter smf = (SessionManagementFilter) args[0];
				smf.setInvalidSessionStrategy(invalidSessionStrategy);
				return bean.addFilter(smf);
			} else if (isConcurrentSessionFilter(args[0])) {
				SingleSessionFilter ssf = postProcess(new SingleSessionFilter());
				return bean.addFilterBefore(ssf, ConcurrentSessionFilter.class);
			}
			return super.invokeBean(bean, method, args);
		}

		private boolean isSessionManagementFilter(Object filter) {
			return filter != null && filter instanceof SessionManagementFilter;
		}

		private boolean isConcurrentSessionFilter(Object filter) {
			return filter != null && filter instanceof ConcurrentSessionFilter;
		}

	}

	protected final class SingleSessionFilter extends GenericFilterBean {
		private LogoutHandler[] handlers = new LogoutHandler[]{new SecurityContextLogoutHandler()};

		@Override
		public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;

			HttpSession session = request.getSession(false);

			if (session != null) {
				SessionInformation info = sessionRegistry.getSessionInformation(session.getId());

				if (info != null) {
					if (info.isExpired()) {
						// Expired - abort processing
						doLogout(request, response);
						// session invalid
						invalidSessionStrategy.onInvalidSessionDetected(request, response);
						return;
					} else {
						// Non-expired - update last request date/time
						sessionRegistry.refreshLastRequest(info.getSessionId());
					}
				}
			}
			filterChain.doFilter(request, response);
		}

		private void doLogout(HttpServletRequest request, HttpServletResponse response) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			for (LogoutHandler handler : handlers) {
				handler.logout(request, response, auth);
			}
		}
	}
}
