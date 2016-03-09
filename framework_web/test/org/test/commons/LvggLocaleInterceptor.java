package org.test.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.smarty.web.support.interceptor.AbsInterceptor;
import org.springframework.stereotype.Component;

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
