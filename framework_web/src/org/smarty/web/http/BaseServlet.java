package org.smarty.web.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.smarty.core.io.RuntimeLogger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Action基类
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class BaseServlet {
    private static RuntimeLogger logger = new RuntimeLogger(BaseServlet.class);

    public Theme useTheme;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    public enum Theme {
        defPlan, planA, planB, planC, planD, planE, planF, planG
    }

    public String getBase() {
        return request.getContextPath();
    }

    @ModelAttribute
    public void setHttpServlet(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        setInstanceState(this.request);
    }

    public void setInstanceState(HttpServletRequest request) {

    }

    public String getParameter(String key) {
        return request.getParameter(key);
    }

    public String redirectAction(String action, String name) {
        StringBuilder url = new StringBuilder("redirect:/");
        url.append(action).append("/");
        url.append(name).append(".do");
        return url.toString();
    }

    public String forwardView(String prefix, String name) {
        StringBuilder url = new StringBuilder();
        url.append(prefix).append("_");
        url.append(name).append(".jsp");
        return url.toString();
    }

    /**
     * 返回主题,未设置时返回默认主题
     *
     * @return 主题
     */
    public String getTheme() {
        if (useTheme == null) {
            useTheme = Theme.defPlan;
        }
        return useTheme.toString();
    }

    /**
     * 设置主题
     *
     * @param useTheme 主题
     */
    public void setTheme(Theme useTheme) {
        this.useTheme = useTheme;
    }

    /**
     * 选择语言
     *
     * @param language 小写的两字母 ISO-639 代码
     * @param country  大写的两字母 ISO-3166 代码
     */
    public void chooseLanguage(String language, String country) {
        Locale locale = new Locale(language, country);
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
    }

    private HttpURLConnection getConnection(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(5000);
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)");
        con.setUseCaches(false);
        con.setDoInput(true);
        con.setDoOutput(true);
        return con;
    }

    protected byte[] requestPost(URL url, byte[] data) throws IOException {
        HttpURLConnection con = getConnection(url);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestMethod("POST");
        return urlStream(con, data);
    }

    protected byte[] requestGet(URL url) throws IOException {
        HttpURLConnection con = getConnection(url);
        con.setRequestMethod("GET");
        return urlStream(con, null);
    }

    protected PrintWriter getPrintWrite(String type) {
        setResponseNoCache();
        response.setContentType(type + ";charset=UTF-8");
        try {
            return response.getWriter();
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 设置页面不缓存
     */
    protected void setResponseNoCache() {
        response.setHeader("progma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
    }

    protected byte[] urlStream(HttpURLConnection con, byte[] data) throws IOException {
        if (con == null) {
            return null;
        }
        if (con.getDoInput() && data != null && data.length > 0) {
            copyStream(new ByteArrayInputStream(data), con.getOutputStream());
        }
        if (con.getDoOutput()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            copyStream(con.getInputStream(), out);
            return out.toByteArray();
        }
        return null;
    }

    protected void downloadResponse(String fileName, int len, String sha1) {
        response.reset();
        //设置文件名
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        //设置下载文件大小
        response.addHeader("Content-Length", len + "");
        response.addHeader("sha1", sha1);
        //设置文件类型
        response.setContentType("application/octet-stream;charset=UTF-8");
    }

    protected void copyStream(InputStream in, OutputStream out) throws IOException {
        try {
            IOUtils.copy(in, out);
            out.flush();
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }
}