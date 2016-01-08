package org.smarty.web.http;

import org.smarty.core.io.RuntimeLogger;
import org.smarty.core.utils.DateUtil;
import org.smarty.web.commons.FreemarkerManager;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created Date 2015/04/09
 *
 * @author kyokuryou
 * @version 1.0
 */
public class JSLocaleFilter  implements Filter, InitializingBean {
    private static RuntimeLogger logger = new RuntimeLogger(JSLocaleFilter.class);
    private FreemarkerManager freemarkerManager;
    private String jsFtl;

    public void afterPropertiesSet() throws Exception {

    }

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
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ServletOutputStream osw = getHttpHeader(response);
        try {
            freemarkerManager.outputTemplate(jsFtl, null, osw);
        } catch (IOException e) {
            logger.out(e);
        } finally {
            osw.close();
        }
    }

    @Override
    public void destroy() {

    }

    private ServletOutputStream getHttpHeader(HttpServletResponse response) throws IOException {
        Date now = DateUtil.getToday();
        Date ex = DateUtil.addMinute(now, 10);
        response.setHeader("Cache-Control", "public");
        response.setHeader("Pragma", "Pragma");
        response.setHeader("Last-Modified", now.toString());
        response.setHeader("Expires", ex.toString());
        response.setContentType("text/javascript");
        response.setCharacterEncoding("UTF-8");
        return response.getOutputStream();
    }
}
