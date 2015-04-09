package org.smarty.web.http;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 去除页面参数字符串两端的空格
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class TrimInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContextUtils.getLocaleResolver(request).setLocale(request, response, Locale.CHINESE);
        Map<String, String[]> parameters = request.getParameterMap();
        Set<Map.Entry<String, String[]>> params = parameters.entrySet();
        for (Map.Entry<String, String[]> param : params) {
            String[] value = param.getValue();
            for (int i = 0; i < value.length; i++) {
                value[i] = value[i].trim();
                parameters.put(param.getKey(), value);
            }
        }
        return true;
    }
}
