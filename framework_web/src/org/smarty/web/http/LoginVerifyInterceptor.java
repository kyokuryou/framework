package org.smarty.web.http;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断会员是否登录
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class LoginVerifyInterceptor extends HandlerInterceptorAdapter {
    private String sessionId = "jsession";
    private String cookieName = "jcookie";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginMemberId = (String) request.getSession().getAttribute(sessionId);
        if (loginMemberId == null) {
            Cookie cookie = new Cookie(cookieName, null);
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.sendRedirect("/login.do");
            return false;
        }
        return true;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
}