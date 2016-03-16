package org.smarty.security.support.filter;

import com.octo.captcha.service.image.ImageCaptchaService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.exception.CaptchaException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 过滤器 - 后台登录验证码
 */
public abstract class CaptchaAuthFilter extends AbsAuthFilter {
	private String captchaParameter = "j_captcha";
	private ImageCaptchaService imageCaptchaService;

	public abstract Authentication tryAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException;

	public final void setCaptchaParameter(String captchaParameter) {
		this.captchaParameter = captchaParameter;
	}

	public final void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
		this.imageCaptchaService = imageCaptchaService;
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		ObjectUtil.assertNotEmpty(imageCaptchaService, "imageCaptchaService must be not null");
	}

	@Override
	public final Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		if (!validateCaptcha(request)) {
			throw new CaptchaException("captcha code error");
		}
		return tryAuthentication(request, response);
	}

	/**
	 * 校验验证码.
	 *
	 * @param request HttpServletRequest对象
	 */
	protected boolean validateCaptcha(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		String cval = request.getParameter(captchaParameter);
		if (ObjectUtil.isEmpty(cval)) {
			return false;
		}
		return imageCaptchaService.validateResponseForID(sessionId, cval.toUpperCase());
	}
}
