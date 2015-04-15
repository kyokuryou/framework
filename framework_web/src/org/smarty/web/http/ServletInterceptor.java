package org.smarty.web.http;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created Date 2015/04/15
 *
 * @author kyokuryou
 * @version 1.0
 */
public class ServletInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
        if (mv == null || !mv.isReference()) {
            return;
        }
        String vn = mv.getViewName();
        if (!vn.endsWith("jsp") && !vn.endsWith("ftl")) {
            return;
        }
        if (!vn.startsWith("redirect:") && !vn.startsWith("forward:")) {
            mv.setViewName(getFullName((HandlerMethod) handler, vn));
            return;
        }
        String action = vn.startsWith("redirect:") ? vn.substring(0, "redirect:".length()) : vn.substring(0, "forward:".length());
        String url = vn.startsWith("redirect:") ? vn.substring("redirect:".length()) : vn.substring("forward:".length());
        mv.setViewName(action + getFullName((HandlerMethod) handler, url));
    }

    protected String getFullName(HandlerMethod handler, String name) {
        String clsn = handler.getBeanType().getSimpleName();
        char[] nms = clsn.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char nm : nms) {
            if (sb.length() > 0 && Character.isUpperCase(nm)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(nm));
        }
        sb.delete(sb.lastIndexOf("_"), sb.length());
        sb.append("_").append(name);
        return sb.toString();
    }

}
