package org.smarty.security.config;

import com.octo.captcha.service.image.ImageCaptchaService;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.DateUtil;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.core.utils.SpringUtil;
import org.smarty.security.bean.UserSecurity;
import org.smarty.security.common.ISecurityService;
import org.smarty.security.common.SecurityResourceProxy;
import org.smarty.security.filter.SecurityCaptchaFilter;
import org.smarty.web.config.WebConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	private final Log logger = LogFactory.getLog(SecurityConfigurer.class);
	public static final String SECURITY_LISTENER_NAME = "securityListener";
	public static final String SECURITY_SERVICE_NAME = "securityService";
	public static final String SECURITY_CAPTCHA_FILTER_NAME = "securityCaptchaFilter";
	public static final String SECURITY_RESOURCE_PROXY_NAME = "securityResourceProxy";
	@Resource(name = SECURITY_SERVICE_NAME)
	private ISecurityService securityService;
	@Value("${login.failure.max.count}")
	private int failureMaxCount;
	@Value("${login.failure.lock.time}")
	private int failureLockTime;
	@Value("${login.url}")
	private String loginUrl;
	@Value("${logout.url}")
	private String logoutUrl;
	@Value("${timeout.url}")
	private String timeoutUrl;
	@Value("${access.denied.url}")
	private String accessDeniedUrl;
	@Value("${login.processing.url}")
	private String loginProcessingUrl;
	@Value("${logout.processing.url}")
	private String logoutProcessingUrl;
	@Value("${login.success.url}")
	private String successUrl;
	@Value("${username.parameter:j_username}")
	private String usernameParameter;
	@Value("${password.parameter:j_password}")
	private String passwordParameter;
	@Value("${captcha.parameter:j_captcha}")
	private String captchaParameter;
	@Value("${remember.me.key}")
	private String rememberMeKey;

	@Bean(name = SECURITY_LISTENER_NAME)
	public ApplicationListener getApplicationListener() {
		SecurityListener sl = new SecurityListener();
		sl.setLoginFailureMaxCount(failureMaxCount);
		return sl;
	}

	@Bean(name = SECURITY_RESOURCE_PROXY_NAME)
	public SecurityResourceProxy getSecurityResourceProxy() {
		SecurityResourceProxy sm = new SecurityResourceProxy();
		sm.setSecurityService(securityService);
		return sm;
	}

	@Bean(name = SECURITY_CAPTCHA_FILTER_NAME)
	@Lazy
	public Filter getSecurityCaptchaFilter(ImageCaptchaService imageCaptchaService) {
		SecurityCaptchaFilter scf = new SecurityCaptchaFilter();
		scf.setImageCaptchaService(imageCaptchaService);
		scf.setCaptchaParameter(captchaParameter);
		scf.setDefaultTargetUrl(loginUrl);
		return scf;
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		final HttpSecurity http = getHttp();
		// 设置不拦截规则
		String[] ignoring = {
				loginUrl,
				logoutUrl,
				timeoutUrl,
				accessDeniedUrl
		};
		web.postBuildAction(new Runnable() {
			public void run() {
				SecurityResourceProxy resourceProxy = SpringUtil.getBean(SECURITY_RESOURCE_PROXY_NAME, SecurityResourceProxy.class);
				ObjectUtil.assertNotEmpty(resourceProxy, SECURITY_RESOURCE_PROXY_NAME + " bean not define");
				FilterSecurityInterceptor securityInterceptor = http.getSharedObject(FilterSecurityInterceptor.class);
				resourceProxy.securityInterceptor(securityInterceptor);
				web.securityInterceptor(securityInterceptor);
			}
		}).ignoring().antMatchers(ignoring);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new UserDetailsServiceImpl())
				.passwordEncoder(new Md5PasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		LoginSuccessHandlerImpl ash = new LoginSuccessHandlerImpl();
		ash.setDefaultTargetUrl(successUrl);
		ash.setAlwaysUseDefaultTargetUrl(true);

		LoginFailureHandlerImpl afh = new LoginFailureHandlerImpl();
		afh.setDefaultTargetUrl(loginUrl);
		afh.setAlwaysUseDefaultTargetUrl(true);

		LogoutSuccessHandlerImpl lsh = new LogoutSuccessHandlerImpl();
		lsh.setDefaultTargetUrl(logoutUrl);
		lsh.setAlwaysUseDefaultTargetUrl(true);

		http.authorizeRequests()
				.filterSecurityInterceptorOncePerRequest(true)
				.accessDecisionManager(new AccessDecisionManagerImpl())
				//	.and().anonymous()
				.and().exceptionHandling()
				.accessDeniedPage(accessDeniedUrl)
				.and().csrf().disable().formLogin()
				.loginPage(loginUrl)
				.usernameParameter(usernameParameter)
				.passwordParameter(passwordParameter)
				.loginProcessingUrl(loginProcessingUrl)
				.successHandler(ash)
				.failureHandler(afh).permitAll()
				.and().rememberMe()
				.key(rememberMeKey)
				.and().logout()
				.invalidateHttpSession(true)
				.logoutUrl(logoutProcessingUrl)
				.logoutSuccessHandler(lsh)
				.and().sessionManagement()
				.sessionFixation().changeSessionId()
				.invalidSessionUrl(timeoutUrl)
				.maximumSessions(1).maxSessionsPreventsLogin(true)
				.expiredUrl(loginUrl)
				.maxSessionsPreventsLogin(false);
	}

	private class LoginFailureHandlerImpl extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationFailureHandler {
		private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
			if (exception == null) {
				return;
			}
			String targetUrl = determineTargetUrl(request, response);
			if (exception instanceof UsernameNotFoundException) {
				// 用户名不存在
				targetUrl += "?err=1";
			} else if (exception instanceof BadCredentialsException) {
				// 密码错误
				targetUrl += "?err=2";
			} else if (exception instanceof LockedException) {
				// 帐号锁定
				targetUrl += "?err=3";
			} else if (exception instanceof AccountExpiredException) {
				// 帐号过期
				targetUrl += "?err=4";
			} else if (exception instanceof CredentialsExpiredException) {
				// 密码过期
				targetUrl += "?err=5";
			} else if (exception instanceof DisabledException) {
				// 帐号禁用
				targetUrl += "?err=6";
			}
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}
	}

	private class LoginSuccessHandlerImpl extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {
		private RequestCache requestCache = new HttpSessionRequestCache();

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
			SavedRequest savedRequest = requestCache.getRequest(request, response);
			if (savedRequest == null) {
				handle(request, response, authentication);
				return;
			}
			String targetUrlParameter = getTargetUrlParameter();
			if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
				requestCache.removeRequest(request, response);
				handle(request, response, authentication);
				return;
			}
			String targetUrl = savedRequest.getRedirectUrl();
			logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
		}
	}

	private class LogoutSuccessHandlerImpl extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
			handle(request, response, authentication);
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

	private class UserDetailsServiceImpl implements UserDetailsService {

		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
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

}
