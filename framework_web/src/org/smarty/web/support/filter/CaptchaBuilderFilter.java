package org.smarty.web.support.filter;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.ObjectUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created Date 2015/04/09
 *
 * @author kyokuryou
 * @version 1.0
 */
public final class CaptchaBuilderFilter extends SingleRequestFilter implements InitializingBean {
	private static Log logger = LogFactory.getLog(CaptchaBuilderFilter.class);
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Value("${captcha.url:/captcha.jpg}")
	private String captchaMapping;

	@Override
	protected String getFilterProcessesUrl() {
		return captchaMapping;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ObjectUtil.assertNotEmpty(imageCaptchaService, "imageCaptchaService must not be null or empty");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletOutputStream out = getHttpHeader(response);
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) imageCaptchaService.getChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {
			logger.warn(e);
		} finally {
			out.close();
		}
	}

	private ServletOutputStream getHttpHeader(HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		return response.getOutputStream();
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}
}
