package org.smarty.security.action.admin;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台Action类 - 后台管理、管理员
 */

@Controller
@RequestMapping("/admin")
public class AdminAction extends BaseAdminAction {

	@RequestMapping("/test")
	public String test() {
		return "admin/test.ftl";
	}


	@RequestMapping("/index")
	public String index() {
		return null;
	}

	@RequestMapping("/login")
	public final String login() {
		AccountStatus err = getAccountStatus();
		switch (err) {
			case USERNAME_NOT_FOUND:
				addMessageAttribute("login.failure", "login.error.username");
				break;
			case BAD_CREDENTIALS:
				addMessageAttribute("login.failure", "login.error.credentials");
				break;
			case LOCKED:
				break;
			case ACCOUNT_EXPIRED:
				break;
			case CREDENTIALS_EXPIRED:
				break;
			case DISABLED:
				break;
			case CAPTCHA:
				addMessageAttribute("login.failure", "login.error.captcha");
				break;
		}
		return "admin/login.ftl";
	}

	@RequestMapping("/main")
	public final String main() {
		return "admin/main.ftl";
	}

	@RequestMapping("/denied")
	public String denied() {
		return "admin/denied.ftl";
	}

	@RequestMapping("/timeout")
	public void timeout() throws IOException {
		sendRedirect("/admin/login.do");
	}

	@RequestMapping("/logout")
	public void logout() throws IOException {
		sendRedirect("/admin/login.do");
	}
}