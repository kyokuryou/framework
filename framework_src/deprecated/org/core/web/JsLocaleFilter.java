package org.smarty.web.commons;

import org.smarty.core.logger.RuntimeLogger;
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

    public void init(FilterConfig filterConfig) throws ServletException {
    }

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



    public void destroy() {
    }
}
