package org.test.commons;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @author qul
 * @since LVGG1.1
 */
@Component
public class LvggLocaleInterceptor extends HandlerInterceptorAdapter {
    public static final String DEFAULT_PARAM_NAME = "locale";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {

        String newLocale = request.getHeader(DEFAULT_PARAM_NAME);
        if (newLocale != null) {

            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
            localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
        }
        return true;
    }
}
