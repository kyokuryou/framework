package org.smarty.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 验证失败
 */
public final class LoginFailureHandlerImpl extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationFailureHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public LoginFailureHandlerImpl() {
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		if (exception == null) {
			return;
		}
		String targetUrl = determineTargetUrl(request, response);
		if (exception instanceof BadCredentialsException) {
			targetUrl += "?err=credentials";
		} else if (exception instanceof UsernameNotFoundException) {
			targetUrl += "?err=username";
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
}
