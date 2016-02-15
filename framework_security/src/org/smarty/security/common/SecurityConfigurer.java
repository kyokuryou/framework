package org.smarty.security.common;

import org.smarty.security.handler.LoginFailureHandlerImpl;
import org.smarty.security.handler.LoginSuccessHandlerImpl;
import org.smarty.security.handler.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * SecurityConfigurer
 */
@Configuration(value = "security_system")
@EnableWebMvcSecurity
@Order(3)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	private ApplicationContext context;
	@Value("#{configurer['login.failure.max.count']}")
	private int failureMaxCount;
	@Value("#{configurer['login.failure.lock.time']}")
	private int failureLockTime;
	@Value("#{configurer['login.url']}")
	private String loginUrl;
	@Value("#{configurer['logout.url']}")
	private String logoutUrl;
	@Value("#{configurer['timeout.url']}")
	private String timeoutUrl;
	@Value("#{configurer['access.denied.url']}")
	private String accessDeniedUrl;
	@Value("#{configurer['login.processing.url']}")
	private String loginProcessingUrl;
	@Value("#{configurer['logout.processing.url']}")
	private String logoutProcessingUrl;
	@Value("#{configurer['login.success.url']}")
	private String successUrl;
	@Value("#{configurer['username.parameter']}")
	private String usernameParameter;
	@Value("#{configurer['password.parameter']}")
	private String passwordParameter;
	@Value("#{configurer['remember.me.key']}")
	private String rememberMeKey;

	@Bean(name = "secSuccessHandler")
	public AuthenticationSuccessHandler getSecuritySuccessHandler() {
		LoginSuccessHandlerImpl ssh = new LoginSuccessHandlerImpl();
		ssh.setDefaultTargetUrl(successUrl);
		return ssh;
	}

	@Bean(name = "secFailureHandler")
	public AuthenticationFailureHandler getSecurityFailureHandler() {
		LoginFailureHandlerImpl sfh = new LoginFailureHandlerImpl();
		sfh.setDefaultTargetUrl(loginUrl);
		sfh.setAlwaysUseDefaultTargetUrl(true);
		return sfh;
	}

	@Bean(name = "secLogoutHandler")
	public LogoutSuccessHandler getSecurityLogoutHandler() {
		LogoutSuccessHandlerImpl slh = new LogoutSuccessHandlerImpl();
		slh.setDefaultTargetUrl(logoutUrl);
		slh.setAlwaysUseDefaultTargetUrl(true);
		return slh;
	}

	@Bean(name = "secAccessDecision")
	public AccessDecisionManager getSecurityAccessDecision() {
		return new SecurityAccessDecision();
	}

	@Bean(name = "secUserDetailService")
	public UserDetailsService getUserDetailsService() {
		SecurityAuthentication sa = new SecurityAuthentication();
		sa.setLoginFailureLockTime(failureLockTime);
		return sa;
	}

	@Bean(name = "secListener")
	public ApplicationListener getApplicationListener() {
		SecurityListener sl = new SecurityListener();
		sl.setLoginFailureMaxCount(failureMaxCount);
		return sl;
	}

	@Bean(name = "secMetadata")
	public SecurityMetadata getSecurityMetadata() {
		return new SecurityMetadata();
	}

	@Bean(name = "secInterceptor")
	public FilterSecurityInterceptor getFilterSecurityInterceptor() throws Exception {
		AuthenticationManager am = authenticationManager();
		AccessDecisionManager adm = getBean("secAccessDecision", AccessDecisionManager.class);
		SecurityMetadata sm = getBean("secMetadata", SecurityMetadata.class);

		FilterSecurityInterceptor fsi = new FilterSecurityInterceptor();
		fsi.setAuthenticationManager(am);
		fsi.setAccessDecisionManager(adm);
		fsi.setSecurityMetadataSource(sm);
		return fsi;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 设置不拦截规则
		String[] ignoring = {
				loginUrl,
				logoutUrl,
				timeoutUrl,
				accessDeniedUrl
		};
		web.ignoring().antMatchers(ignoring);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService uds = getBean("secUserDetailService", UserDetailsService.class);
		auth.userDetailsService(uds).passwordEncoder(new Md5PasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		AuthenticationSuccessHandler ash = getBean("secSuccessHandler", AuthenticationSuccessHandler.class);
		AuthenticationFailureHandler afh = getBean("secFailureHandler", AuthenticationFailureHandler.class);
		LogoutSuccessHandler lsh = getBean("secLogoutHandler", LogoutSuccessHandler.class);
		FilterSecurityInterceptor fsi = getBean("secInterceptor", FilterSecurityInterceptor.class);

		http.authorizeRequests()
				.filterSecurityInterceptorOncePerRequest(true)
				.and().anonymous()
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
		http.addFilterBefore(fsi, FilterSecurityInterceptor.class);
	}

	private <T> T getBean(String name, Class<T> klass) {
		return context.getBean(name, klass);
	}
}
