package org.smarty.web.http;

import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.context.Theme;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @author qul
 * @since LVGG1.1
 */
public abstract class AbsInterceptor extends HandlerInterceptorAdapter {

    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        Locale locale = getLocale(request);
        Theme theme = getTheme(request);
        if (locale != null) {
            LocaleResolver lr = RequestContextUtils.getLocaleResolver(request);
            if (lr == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
            lr.setLocale(request, response, locale);
        }
        if (theme != null) {
            ThemeResolver tr = RequestContextUtils.getThemeResolver(request);
            if (tr == null) {
                throw new IllegalStateException("No ThemeResolver found: not in a DispatcherServlet request?");
            }
            tr.setThemeName(request, response, theme.getName());
        }
        return preInterceptor(request, response, handler);
    }

    @Override
    public final void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelView) throws ServletException {
        postInterceptor(request, response, handler, modelView);
    }

    protected Locale getLocale(HttpServletRequest request) {
        return null;
    }

    protected Theme getTheme(HttpServletRequest request) {
        return null;
    }

    public boolean preInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        return true;
    }

    public void postInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelView) throws ServletException {
    }

}
