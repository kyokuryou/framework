package org.smarty.security.config;

import org.smarty.web.config.WebConfigurerAdapter;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * SecConfigurerAdapter
 */
public abstract class SecConfigurerAdapter extends WebConfigurerAdapter {

	public SecConfigurerAdapter() {

	}

	public final void afterPropertiesSet() {
	}

	protected void configure(WebSecurity web) throws Exception {

	}

	protected void configure(HttpSecurity http) throws Exception {

	}

	protected void configure(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());
	}

	protected AbstractAuthenticationProcessingFilter loginProcessingFilter() {
		return new UsernamePasswordAuthenticationFilter();
	}

	protected AccessDeniedHandler accessDeniedHandler() {
		return null;
	}

	protected LogoutSuccessHandler logoutSuccessHandler() {
		return null;
	}

	protected AuthenticationSuccessHandler loginSuccessHandler() {
		return null;
	}

	protected AuthenticationFailureHandler loginFailureHandler() {
		return null;
	}

	protected InvalidSessionStrategy invalidSessionStrategy() {
		return null;
	}
}
