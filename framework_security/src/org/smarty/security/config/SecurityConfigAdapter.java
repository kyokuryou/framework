package org.smarty.security.config;

import org.smarty.security.config.configurers.CustomLoginConfigurer;
import org.smarty.security.config.configurers.CustomSessionConfigurer;
import org.smarty.security.support.service.FilterMetadataSource;
import org.smarty.web.config.WebConfigAdapter;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.config.annotation.web.configurers.ServletApiConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * SecConfigurerAdapter
 */
public abstract class SecurityConfigAdapter extends WebConfigAdapter {

	public SecurityConfigAdapter() {

	}

	protected void configure(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {

	}

	protected void configure(HeadersConfigurer<HttpSecurity> headers) {

	}

	protected void configure(SecurityContextConfigurer<HttpSecurity> securityContext) {

	}

	protected void configure(RequestCacheConfigurer<HttpSecurity> requestCache) {

	}

	protected void configure(AnonymousConfigurer<HttpSecurity> anonymous) {

	}

	protected void configure(ServletApiConfigurer<HttpSecurity> servletApi) {

	}

	protected void configure(RememberMeConfigurer<HttpSecurity> rememberMe) {

	}

	protected void configure(LogoutConfigurer<HttpSecurity> logout) {

	}

	protected void configure(CustomLoginConfigurer<HttpSecurity> customLogin) {

	}


	protected void configure(CustomSessionConfigurer<HttpSecurity> customSession) {

	}

	protected void configure(IgnoredRequestConfigurer ignoredRequest) {

	}


	protected void configure(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());
	}

	protected AbstractAuthenticationProcessingFilter loginProcessingFilter() {
		return null;
	}

	protected AccessDecisionManager accessDecisionManager() {
		return null;
	}

	protected UserDetailsService userDetailsService() {
		return null;
	}

	protected FilterMetadataSource filterMetadataSource() {
		return null;
	}
}
