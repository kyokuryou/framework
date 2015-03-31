package org.smarty.web.filter;

import org.smarty.core.logger.RuntimeLogger;
import org.smarty.web.commons.FreemarkerManager;
import org.smarty.core.utils.DateUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * js国际化
 */
public class JsLocaleFilter implements Filter {
    private static RuntimeLogger logger = new RuntimeLogger(JsLocaleFilter.class);

    private FreemarkerManager freemarkerManager;
    private String jsFtl;

    public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
        this.freemarkerManager = freemarkerManager;
    }

    public void setJsFtl(String jsFtl) {
        this.jsFtl = jsFtl;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        OutputStream osw = getCacheOutput(servletResponse);
        try {
            freemarkerManager.outputTemplate(jsFtl, null, osw);
        } catch (Exception e) {
            logger.out(e);
        } finally {
            osw.close();
        }
    }

    private OutputStream getCacheOutput(ServletResponse servletResponse) throws IOException {
        Date now = DateUtil.getToday();
        Date ex = DateUtil.addMinute(now, 10);

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Cache-Control", "public");
        response.setHeader("Pragma", "Pragma");
        response.setHeader("Last-Modified", now.toString());
        response.setHeader("Expires", ex.toString());
        response.setContentType("text/javascript");
        response.setCharacterEncoding("UTF-8");
        return response.getOutputStream();
    }

    @Override
    public void destroy() {
    }
}
