package org.smarty.security.config;

import java.lang.reflect.Method;
import javax.servlet.Filter;
import org.smarty.core.common.DynamicProxyHandler;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * SecCustomLoginConfigurer
 */
public class SecCustomLoginConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractAuthenticationFilterConfigurer<H, SecCustomLoginConfigurer<H>, AbstractAuthenticationProcessingFilter> {

	public SecCustomLoginConfigurer(AbstractAuthenticationProcessingFilter authenticationFilter, String defaultLoginProcessingUrl) {
		super(authenticationFilter, defaultLoginProcessingUrl);
	}


	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		return new AntPathRequestMatcher(loginProcessingUrl, "POST");
	}

	@Override
	public SecCustomLoginConfigurer<H> loginPage(String loginPage) {
		return super.loginPage(loginPage);
	}

	@Override
	public void configure(H http) throws Exception {
		HttpSecurityHandler<H> hsh = new HttpSecurityHandler<H>();
		super.configure(hsh.bind(http));
	}

	private final class HttpSecurityHandler<H extends HttpSecurityBuilder<H>> extends DynamicProxyHandler<H> {

		@Override
		public Object invokeBean(H bean, Method method, Object[] args) throws Throwable {
			String mn = method.getName();
			if (!"addFilter".equals(mn)) {
				return super.invokeBean(bean, method, args);
			}
			return bean.addFilterBefore((Filter) args[0], UsernamePasswordAuthenticationFilter.class);
		}
	}
}
