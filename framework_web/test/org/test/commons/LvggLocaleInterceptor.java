package org.test.commons;

import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.smarty.web.http.AbsInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.ui.context.Theme;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @author qul
 * @since LVGG1.1
 */
@Component
public class LvggLocaleInterceptor extends AbsInterceptor {
    public static final String DEFAULT_PARAM_NAME = "locale";

    @Override
    public boolean preInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preInterceptor(request, response, handler);
    }
}
