package org.smarty.security.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.DateUtil;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.core.utils.SpringUtil;
import org.smarty.security.bean.UserSecurity;
import org.smarty.security.common.ISecurityService;
import org.smarty.security.common.SecurityResourceProxy;
import org.smarty.security.common.SecurityStatus;
import org.smarty.web.config.WebConfigurer;
import org.smarty.web.utils.WebPathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;
import org.springframework.stereotype.Component;

/**
 * SecurityConfigurer
 */
@Configuration(value = "security_system")
@EnableWebSecurity
@Import(value = {WebConfigurer.class})
@ComponentScan(useDefaultFilters = false, basePackages = "org.smarty.security", includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class)
})
@Order(0)
public class SecConfigurer extends WebSecurityConfigurerAdapter {
	private final Log logger = LogFactory.getLog(SecConfigurer.class);
	public static final String SECURITY_LISTENER_NAME = "securityListener";
	public static final String SECURITY_RESOURCE_PROXY_NAME = "securityResourceProxy";

	private SecConfigurerAdapter configurerAdapter = new DefaultConfigurerAdapter();
	private ISecurityService securityService;

	@Value("${login.failure.max.count}")
	private int failureMaxCount;
	@Value("${login.failure.lock.time}")
	private int failureLockTime;
	@Value("${login.url:/login.do}")
	private String loginUrl;
	@Value("${login.processing.url:/loginProcessing}")
	private String loginProcessingUrl;
	@Value("${logout.processing.url:/logoutProcessing}")
	private String logoutProcessingUrl;
	@Value("${login.status.url:/status.do}")
	private String statusUrl;
	@Value("${status.parameter:j_status}")
	private String statusParameter;
	@Value("${remember.me.key}")
	private String rememberMeKey;
	@Value("${ignoring.urls}")
	private String[] ignoringUrls;

	@Autowired(required = true)
	public void setConfigurerAdapter(SecConfigurerAdapter configurerAdapter, ISecurityService securityService) {
		ObjectUtil.assertNotEmpty(securityService, "securityService must not null");
		this.securityService = securityService;
		this.configurerAdapter = configurerAdapter;
		this.configurerAdapter.afterPropertiesSet();
	}

	@Bean(name = SECURITY_LISTENER_NAME)
	public ApplicationListener<ApplicationEvent> getApplicationListener() {
		SecListener sl = new SecListener();
		sl.setLoginFailureMaxCount(failureMaxCount);
		return sl;
	}

	@Bean(name = SECURITY_RESOURCE_PROXY_NAME)
	public SecurityResourceProxy getSecurityResourceProxy() {
		SecurityResourceProxy sm = new SecurityResourceProxy();
		sm.setSecurityService(securityService);
		return sm;
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		final HttpSecurity http = getHttp();

		web.postBuildAction(new Runnable() {
			public void run() {
				SecurityResourceProxy resourceProxy = SpringUtil.getBean(SECURITY_RESOURCE_PROXY_NAME, SecurityResourceProxy.class);
				ObjectUtil.assertNotEmpty(resourceProxy, SECURITY_RESOURCE_PROXY_NAME + " bean not define");
				FilterSecurityInterceptor securityInterceptor = http.getSharedObject(FilterSecurityInterceptor.class);
				resourceProxy.securityInterceptor(securityInterceptor);
				web.securityInterceptor(securityInterceptor);
			}
		})
				.ignoring().regexMatchers("^\\S+\\.(png|jpg|css|js|gif)$")
				.antMatchers(getIgnoringUrls());
		configurerAdapter.configure(web);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userDetailsService = getUserDetailsService();
		configurerAdapter.configure(auth, userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//resource authorize
		http.authorizeRequests()
				.filterSecurityInterceptorOncePerRequest(true)
				.accessDecisionManager(getAccessDecisionManager());
		// resource access denied
		http.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler());
		// csrf
		http.csrf().disable();
		// anonymous
		http.anonymous();
		// rememberMe
		http.rememberMe().key(rememberMeKey);
		// logout
		http.logout()
				.invalidateHttpSession(true)
				.logoutUrl(logoutProcessingUrl)
				.logoutSuccessHandler(logoutSuccessHandler());
		// sessionManagement
		http.sessionManagement().disable();
		addLoginFilter(http);
		addSessionFilter(http);
		configurerAdapter.configure(http);
	}

	private void addSessionFilter(HttpSecurity http) throws Exception {
		SecCustomSessionConfigurer<HttpSecurity> scsc = http.apply(new SecCustomSessionConfigurer<HttpSecurity>());
		scsc.invalidSessionStrategy(invalidSessionStrategy());
		// session management
		scsc.sessionManagement()
				.sessionFixation().changeSessionId();
		// Single Session
		scsc.maximumSessions(1)
				.maxSessionsPreventsLogin(false);
	}

	private void addLoginFilter(HttpSecurity http) throws Exception {
		AbstractAuthenticationProcessingFilter filter = configurerAdapter.loginProcessingFilter();
		if (filter == null) {
			return;
		}
		AuthenticationSuccessHandler ash = loginSuccessHandler();
		AuthenticationFailureHandler afh = loginFailureHandler();
		SecCustomLoginConfigurer<HttpSecurity> cfb = http.apply(new SecCustomLoginConfigurer<HttpSecurity>(filter, loginProcessingUrl));
		cfb.loginPage(loginUrl);
		cfb.successHandler(ash);
		cfb.failureHandler(afh);
		cfb.permitAll();
	}

	private String[] getIgnoringUrls() {
		// 设置不拦截规则
		List<String> ignoring = new ArrayList<String>();
		ignoring.addAll(Arrays.asList(ignoringUrls));
		ignoring.add(loginUrl);
		ignoring.add(statusUrl);
		return ignoring.toArray(new String[ignoring.size()]);
	}

	private UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	private AccessDecisionManager getAccessDecisionManager() {
		return new AccessDecisionManagerImpl();
	}

	private InvalidSessionStrategy invalidSessionStrategy() {
		InvalidSessionStrategy iss = configurerAdapter.invalidSessionStrategy();
		if (iss != null) {
			return iss;
		}
		return new SimpleRedirectInvalidSessionStrategy(getStatusUrl(SecurityStatus.TIMEOUT));
	}

	private AccessDeniedHandler accessDeniedHandler() {
		AccessDeniedHandler adh = configurerAdapter.accessDeniedHandler();
		if (adh != null) {
			return adh;
		}
		AccessDeniedHandlerImpl impl = new AccessDeniedHandlerImpl();
		impl.setErrorPage(getStatusUrl(SecurityStatus.ACCESS_DENIED));
		return impl;
	}

	private LogoutSuccessHandler logoutSuccessHandler() {
		LogoutSuccessHandler lsh = configurerAdapter.logoutSuccessHandler();
		if (lsh != null) {
			return lsh;
		}
		SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
		handler.setDefaultTargetUrl(getStatusUrl(SecurityStatus.LOGOUT_SUCCESS));
		return handler;
	}

	private AuthenticationSuccessHandler loginSuccessHandler() {
		AuthenticationSuccessHandler sh = configurerAdapter.loginSuccessHandler();
		if (sh != null) {
			return sh;
		}
		SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
		handler.setDefaultTargetUrl(getStatusUrl(SecurityStatus.LOGIN_SUCCESS));
		handler.setAlwaysUseDefaultTargetUrl(false);
		return handler;
	}

	private AuthenticationFailureHandler loginFailureHandler() {
		AuthenticationFailureHandler fh = configurerAdapter.loginFailureHandler();
		if (fh != null) {
			return fh;
		}
		return new SimpleUrlAuthenticationFailureHandler(getStatusUrl(SecurityStatus.LOGIN_FAILURE));
	}

	private String getStatusUrl(SecurityStatus status) {
		Map<String, Object> var = new HashMap<String, Object>();
		var.put(statusParameter, status);
		return WebPathUtil.extendUrl(statusUrl, var);
	}

	private class UserDetailsServiceImpl implements UserDetailsService {

		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
			ObjectUtil.assertNotEmpty(securityService, "securityService must not null");
			String id = securityService.getLoginId(username);
			if (id == null) {
				throw new UsernameNotFoundException(username);
			}
			UserSecurity us = securityService.getUserSecurity(id);
			if (us.isAccountNonLocked()) {
				return us;
			}
			if (canUnLock(us.getLockedDate())) {
				us.setAccountNonLocked(securityService.unlock(id));
			}
			return us;
		}

		/**
		 * 解除管理员账户锁定
		 */
		private boolean canUnLock(Date lockedDate) {
			if (lockedDate == null) {
				return true;
			}
			if (failureLockTime == 0) {
				return true;
			}
			Date nlt = DateUtil.addMinute(lockedDate, failureLockTime);
			return nlt.before(new Date());
		}
	}

	private class AccessDecisionManagerImpl implements AccessDecisionManager {

		public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
			if (configAttributes == null) {
				return;
			}
			//所请求的资源拥有的权限(一个资源对多个权限)
			for (ConfigAttribute configAttribute : configAttributes) {
				//访问所请求资源所需要的权限
				String needPermission = configAttribute.getAttribute();
				//用户所拥有的权限authentication
				for (GrantedAuthority ga : authentication.getAuthorities()) {
					if (needPermission.equals(ga.getAuthority())) {
						return;
					}
				}
			}
			//没有权限
			throw new AccessDeniedException("no right");
		}

		public boolean supports(ConfigAttribute attribute) {
			return true;
		}

		public boolean supports(Class<?> clazz) {
			return true;
		}
	}

	private class DefaultConfigurerAdapter extends SecConfigurerAdapter {
	}
}
