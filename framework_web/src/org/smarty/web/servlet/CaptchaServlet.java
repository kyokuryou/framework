package org.smarty.web.servlet;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import org.smarty.core.logger.RuntimeLogger;
import org.springframework.beans.factory.InitializingBean;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created Date 2015/04/09
 *
 * @author kyokuryou
 * @version 1.0
 */
public class CaptchaServlet extends HttpServlet implements InitializingBean {
    private static RuntimeLogger logger = new RuntimeLogger(CaptchaServlet.class);
    private CaptchaService captchaService;

    public void afterPropertiesSet() throws Exception {

    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream out = getHttpHeader(response);
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

    private ServletOutputStream getHttpHeader(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        return response.getOutputStream();
    }


}
