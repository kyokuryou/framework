package org.smarty.security.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.security.config.configurers.CustomLoginConfigurer;
import org.smarty.security.config.configurers.CustomSessionConfigurer;
import org.smarty.security.support.listener.SecurityListener;
import org.smarty.security.support.service.FilterMetadataSource;
import org.smarty.security.support.service.impl.AccessDecisionManagerImpl;
import org.smarty.security.support.service.impl.FilterMetadataSourceImpl;
import org.smarty.security.support.service.impl.UserDetailsServiceImpl;
import org.smarty.web.config.WebConfig;
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
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.config.annotation.web.configurers.ServletApiConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.stereotype.Component;

/**
 * SecurityConfigurer
 */
@Configuration(value = "security_system")
@EnableWebSecurity
@Import(value = {WebConfig.class})
@ComponentScan(useDefaultFilters = false, basePackages = "org.smarty.security", includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class)
})
@Order(0)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final Log logger = LogFactory.getLog(SecurityConfig.class);
	public static final String SECURITY_LISTENER_NAME = "securityListener";
	@Autowired
	private ObjectPostProcessor<Object> objectPostProcessor;
	private SecurityConfigAdapter configAdapter = new DefaultConfigAdapter();

	@Value("${login.url:/login.do}")
	private String loginUrl;
	@Value("${login.processing.url:/loginProcessing}")
	private String loginProcessingUrl;
	@Value("${logout.processing.url:/logoutProcessing}")
	private String logoutProcessingUrl;
	@Value("${remember.me.key}")
	private String rememberMeKey;
	@Value("${session.maximum:1}")
	private int maximumSessions;
	@Value("${session.maximum.exceeded:false}")
	private boolean maximumExceeded;

	public SecurityConfig() {
		// disableDefaults
		super(true);
	}

	@Autowired(required = false)
	public void setConfigAdapter(SecurityConfigAdapter configAdapter) {
		this.configAdapter = configAdapter;
	}

	@Bean(name = SECURITY_LISTENER_NAME)
	public ApplicationListener<ApplicationEvent> getApplicationListener() {
		return new SecurityListener();
	}

	@Override
	public void init(final WebSecurity web) throws Exception {
		final HttpSecurity http = getHttp();
		web.postBuildAction(new Runnable() {
			public void run() {
				FilterSecurityInterceptor securityInterceptor = http.getSharedObject(FilterSecurityInterceptor.class);
				securityInterceptor.setSecurityMetadataSource(filterMetadataSource());
				web.securityInterceptor(securityInterceptor);
			}
		});
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		IgnoredRequestConfigurer irc = web.ignoring();
		irc.antMatchers(loginUrl);
		irc.regexMatchers("^\\S+\\.(png|jpg|css|js|gif)$");
		configAdapter.configure(irc);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userDetailsService = userDetailsService();
		configAdapter.configure(auth, userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//resource authorize
		http.authorizeRequests()
				.filterSecurityInterceptorOncePerRequest(true)
				.accessDecisionManager(accessDecisionManager());
		// resource access denied
		http.exceptionHandling();
		// headers
		http.headers();
		// sessionManagement disable
		// securityContext
		http.securityContext();
		// requestCache
		http.requestCache();
		// anonymous
		http.anonymous();
		// servletApi
		http.servletApi();
		// rememberMe
		http.rememberMe().key(rememberMeKey);
		// logout
		http.logout()
				.invalidateHttpSession(true)
				.logoutUrl(logoutProcessingUrl);
		// apply:customLogin,customSession
		http.apply(customLogin());
		http.apply(customSession());
		// filter:WebAsyncManagerIntegrationFilter
		http.addFilter(new WebAsyncManagerIntegrationFilter());
		// configure
		configAdapter.configure(http.getConfigurer(ExceptionHandlingConfigurer.class));
		configAdapter.configure(http.getConfigurer(HeadersConfigurer.class));
		configAdapter.configure(http.getConfigurer(SecurityContextConfigurer.class));
		configAdapter.configure(http.getConfigurer(RequestCacheConfigurer.class));
		configAdapter.configure(http.getConfigurer(AnonymousConfigurer.class));
		configAdapter.configure(http.getConfigurer(ServletApiConfigurer.class));
		configAdapter.configure(http.getConfigurer(RememberMeConfigurer.class));
		configAdapter.configure(http.getConfigurer(LogoutConfigurer.class));
		configAdapter.configure(http.getConfigurer(CustomLoginConfigurer.class));
		configAdapter.configure(http.getConfigurer(CustomSessionConfigurer.class));
	}

	private CustomSessionConfigurer<HttpSecurity> customSession() throws Exception {
		CustomSessionConfigurer<HttpSecurity> scsc = new CustomSessionConfigurer<HttpSecurity>();
		// session management
		scsc.sessionManagement()
				.sessionFixation().changeSessionId();
		// Single Session
		scsc.maximumSessions(maximumSessions)
				.maxSessionsPreventsLogin(maximumExceeded);
		return scsc;
	}

	private CustomLoginConfigurer<HttpSecurity> customLogin() throws Exception {
		AbstractAuthenticationProcessingFilter filter = configAdapter.loginProcessingFilter();
		if (filter == null) {
			filter = new UsernamePasswordAuthenticationFilter();
		}
		CustomLoginConfigurer<HttpSecurity> cfb = new CustomLoginConfigurer<HttpSecurity>(filter);
		cfb.loginProcessingUrl(loginProcessingUrl);
		cfb.loginPage(loginUrl);
		cfb.permitAll();
		return cfb;
	}

	protected UserDetailsService userDetailsService() {
		UserDetailsService uds = configAdapter.userDetailsService();
		if (uds == null) {
			uds = new UserDetailsServiceImpl();
		}
		return objectPostProcessor.postProcess(uds);
	}

	private AccessDecisionManager accessDecisionManager() {
		AccessDecisionManager adm = configAdapter.accessDecisionManager();
		if (adm == null) {
			adm = new AccessDecisionManagerImpl();
		}
		return objectPostProcessor.postProcess(adm);
	}

	private FilterMetadataSource filterMetadataSource() {
		FilterMetadataSource fms = configAdapter.filterMetadataSource();
		if (fms == null) {
			fms = new FilterMetadataSourceImpl();
		}
		return objectPostProcessor.postProcess(fms);
	}

	private class DefaultConfigAdapter extends SecurityConfigAdapter {
	}
}
