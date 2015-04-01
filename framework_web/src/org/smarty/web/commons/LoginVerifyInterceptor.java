package org.smarty.web.commons;

import org.smarty.core.bean.SystemConfig;
import org.smarty.core.utils.SystemConfigUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断会员是否登录
 */
public class LoginVerifyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
        String loginMemberId = (String)  request.getSession().getAttribute(systemConfig.getLoginSessionId());
        if (loginMemberId == null) {
            Cookie cookie = new Cookie(systemConfig.getLoginCookieName(), null);
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.sendRedirect("account/login");
            return false;
        }
        return true;
    }
}