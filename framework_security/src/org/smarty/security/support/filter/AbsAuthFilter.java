package org.smarty.security.support.filter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * 过滤器 - 后台登录验证码
 */
public abstract class AbsAuthFilter extends AbstractAuthenticationProcessingFilter {
	protected AbsAuthFilter() {
		super("/j_spring_security_check");
	}

	protected final Authentication authenticate(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
		return getAuthenticationManager().authenticate(authRequest);
	}
}
