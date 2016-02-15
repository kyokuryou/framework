package org.smarty.security.http;

import javax.servlet.http.HttpServletRequest;
import org.smarty.web.http.AbsServlet;

/**
 * spring Security 控制器
 */
public class SecurityServlet extends AbsServlet {
	protected final String USERNAME_ERROR = "username";
	protected final String CREDENTIALS_ERROR = "credentials";
	protected final String CAPTCHA_ERROR = "captcha";

	@Override
	public void setInstanceState(HttpServletRequest request) {

	}
}
