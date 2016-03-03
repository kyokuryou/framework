package org.smarty.security.http;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.bean.UserSecurity;
import org.smarty.web.http.AbsServlet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * spring Security 控制器
 */
public abstract class SecurityServlet extends AbsServlet {
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void setInstanceState(HttpServletRequest request) {

	}

	@Override
	public void sendRedirect(String url) throws IOException {
		redirectStrategy.sendRedirect(request, response, url);
	}

	protected final String getSessionId() {
		return null;
	}

	protected WebAuthenticationDetails getDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}
		return (WebAuthenticationDetails) auth.getDetails();
	}

	protected UserSecurity getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}
		return (UserSecurity) auth.getPrincipal();
	}

	protected AccountStatus getAccountStatus() {
		String err = getParameter("err");
		if (ObjectUtil.isEmpty(err)) {
			return AccountStatus.NONE;
		}
		AccountStatus[] ass = AccountStatus.values();
		for (AccountStatus as : ass) {
			if (as.index.equals(err)) {
				return as;
			}
		}
		return AccountStatus.NONE;
	}

	protected enum AccountStatus {
		NONE("0"),
		USERNAME_NOT_FOUND("1"),
		BAD_CREDENTIALS("2"),
		LOCKED("3"),
		ACCOUNT_EXPIRED("4"),
		CREDENTIALS_EXPIRED("5"),
		DISABLED("6"),
		CAPTCHA("7");

		private String index;

		AccountStatus(String index) {
			this.index = index;
		}
	}
}
