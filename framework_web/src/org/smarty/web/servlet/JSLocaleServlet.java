package org.smarty.web.servlet;

import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.DateUtil;
import org.smarty.web.commons.FreemarkerManager;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created Date 2015/04/09
 *
 * @author kyokuryou
 * @version 1.0
 */
public class JSLocaleServlet extends HttpServlet implements InitializingBean {
    private static RuntimeLogger logger = new RuntimeLogger(JSLocaleServlet.class);
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream osw = getHttpHeader(response);
        try {
            freemarkerManager.outputTemplate(jsFtl, null, osw);
        } catch (IOException e) {
            logger.out(e);
        } finally {
            osw.close();
        }
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
