package org.smarty.security.filter;

import com.octo.captcha.service.CaptchaService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

/**
 * 过滤器 - 后台登录验证码
 */
public class SecurityCaptchaFilter implements Filter {
	private final String CAPTCHA_INPUT_NAME = "j_captcha";// 验证码输入表单名称
	private CaptchaService captchaService;
	private String defaultTargetUrl = "/";
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		boolean isCaptcha = validateCaptcha(request);
		if (isCaptcha) {
			chain.doFilter(request, response);
		} else {
			redirectStrategy.sendRedirect(request, response, defaultTargetUrl + "?err=captcha");
		}
	}

	/**
	 * 校验验证码.
	 *
	 * @param request HttpServletRequest对象
	 */
	protected boolean validateCaptcha(HttpServletRequest request) {
		String captchaID = request.getSession().getId();
		String cval = request.getParameter(CAPTCHA_INPUT_NAME);
		return captchaService.validateResponseForID(captchaID, cval.toUpperCase());
	}

}
