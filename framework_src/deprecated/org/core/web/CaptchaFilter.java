package org.smarty.web.commons;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import org.smarty.core.logger.RuntimeLogger;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 生成验证码图片
 */
public class CaptchaFilter implements Filter {
    private static RuntimeLogger logger = new RuntimeLogger(CaptchaFilter.class);

    private CaptchaService captchaService;

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        try {
            String captchaId = request.getSession(true).getId();
            BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId, request.getLocale());
            ImageIO.write(challenge, "jpg", out);
            out.flush();
        } catch (CaptchaServiceException e) {
            logger.out(e);
        } finally {
            out.close();
        }
    }

}
