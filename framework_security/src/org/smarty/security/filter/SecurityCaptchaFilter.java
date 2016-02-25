package org.smarty.security.filter;

import com.octo.captcha.service.image.ImageCaptchaService;
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
	private String captchaParameter;
	private String defaultTargetUrl;
	private ImageCaptchaService imageCaptchaService;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void setCaptchaParameter(String captchaParameter) {
		this.captchaParameter = captchaParameter;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
		this.imageCaptchaService = imageCaptchaService;
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
		String cval = request.getParameter(captchaParameter);
		return imageCaptchaService.validateResponseForID(captchaID, cval.toUpperCase());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
