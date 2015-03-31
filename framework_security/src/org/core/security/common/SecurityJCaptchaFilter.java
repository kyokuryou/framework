package org.core.security.common;

import org.core.common.JCaptchaEngine;
import com.octo.captcha.service.CaptchaService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器 - 后台登录验证码
 */
public class SecurityJCaptchaFilter implements Filter {
    private CaptchaService captchaService;
    //后台登录验证失败跳转URL
    private String captchaErrorUrl;

    public void setCaptchaErrorUrl(String captchaErrorUrl) {
        this.captchaErrorUrl = captchaErrorUrl;
    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean isCaptcha = validateCaptcha(request);
        if (isCaptcha) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + captchaErrorUrl);
        }
    }

    /**
     * 校验验证码.
     *
     * @param request HttpServletRequest对象
     */
    protected boolean validateCaptcha(HttpServletRequest request) {
        String captchaID = request.getSession().getId();
        String challengeResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
        return captchaService.validateResponseForID(captchaID, challengeResponse);
    }

}
