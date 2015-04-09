package org.smarty.web.servlet;

import org.smarty.core.bean.Pager;
import org.smarty.core.bean.SystemConfig;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.JsonUtil;
import org.smarty.core.utils.SystemConfigUtil;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Action基类
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class BaseServlet {
    private static RuntimeLogger logger = new RuntimeLogger(BaseServlet.class);
    private static final long serialVersionUID = 6718838822334455667L;

    public static final String VIEW = "view";
    public static final String LIST = "list";
    public static final String STATUS = "status";
    public static final String WARN = "warn";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String CONTENT = "content";

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected String id;
    protected String[] ids;
    protected Pager pager;
    public Theme useTheme;

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
     * 获取系统配置信息
     *
     * @return 系统配置信息
     */
    public SystemConfig getSystemConfig() {
        return SystemConfigUtil.getSystemConfig();
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

    /**
     * AJAX输出
     *
     * @param content 内容
     * @param type    类型(如:"text/plain","text/html","text/xml"等)
     * @return null
     */
    public String ajax(String content, String type) {
        try {
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * AJAX输出文本
     * 等价于:
     * <code>
     * ajax(text,"text/plain");
     * </code>
     *
     * @param text 内容
     * @return null
     */
    public String ajaxText(String text) {
        return ajax(text, "text/plain");
    }

    /**
     * AJAX输出HTML
     * 等价于:
     * <code>
     * ajax(html,"text/html");
     * </code>
     *
     * @param html 内容
     * @return null
     */
    public String ajaxHtml(String html) {
        return ajax(html, "text/html");
    }

    /**
     * AJAX输出XML
     * 等价于:
     * <code>
     * ajax(xml,"text/xml");
     * </code>
     *
     * @param xml 内容
     * @return null
     */
    public String ajaxXml(String xml) {
        return ajax(xml, "text/xml");
    }

    /**
     * 根据字符串输出JSON
     * 等价于:
     * <code>
     * ajax(jsonString,"text/html");
     * </code>
     *
     * @param jsonString 内容
     * @return null
     */
    public String ajaxJson(String jsonString) {
        return ajax(jsonString, "text/html");
    }

    /**
     * 根据Map输出JSON
     * 等价于:
     * <code>
     * String json = JsonUtil.JsonEncode(jsonMap)
     * ajax(json,"text/html");
     * </code>
     *
     * @param jsonMap 数据
     * @return null
     */
    public String ajaxJson(Map<String, String> jsonMap) {
        return ajax(JsonUtil.JsonEncode(jsonMap), "text/html");
    }

    /**
     * 输出JSON警告消息
     * 等价于:
     * <code>
     * Map<String, String> jsonMap = new HashMap<String, String>();
     * jsonMap.put(STATUS, WARN);
     * jsonMap.put(MESSAGE, message);
     * String json = JsonUtil.JsonEncode(jsonMap)
     * ajax(json,"text/html");
     * </code>
     *
     * @param message 内容
     * @return null
     */
    public String ajaxJsonWarnMessage(String message) {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put(STATUS, WARN);
        jsonMap.put(MESSAGE, message);
        return ajax(JsonUtil.JsonEncode(jsonMap), "text/html");
    }

    /**
     * 输出JSON成功消息
     * 等价于:
     * <code>
     * Map<String, String> jsonMap = new HashMap<String, String>();
     * jsonMap.put(STATUS, SUCCESS);
     * jsonMap.put(MESSAGE, message);
     * String json = JsonUtil.JsonEncode(jsonMap)
     * ajax(json,"text/html");
     * </code>
     *
     * @param message 内容
     * @return null
     */
    public String ajaxJsonSuccessMessage(String message) {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put(STATUS, SUCCESS);
        jsonMap.put(MESSAGE, message);
        return ajax(JsonUtil.JsonEncode(jsonMap), "text/html");
    }

    /**
     * 输出JSON错误消息
     * 等价于:
     * <code>
     * Map<String, String> jsonMap = new HashMap<String, String>();
     * jsonMap.put(STATUS, ERROR);
     * jsonMap.put(MESSAGE, message);
     * String json = JsonUtil.JsonEncode(jsonMap)
     * ajax(json,"text/html");
     * </code>
     *
     * @param message 内容
     * @return null
     */
    public String ajaxJsonErrorMessage(String message) {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put(STATUS, ERROR);
        jsonMap.put(MESSAGE, message);
        return ajax(JsonUtil.JsonEncode(jsonMap), "text/html");
    }

    /**
     * 设置页面不缓存
     */
    public void setResponseNoCache() {
        response.setHeader("progma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
    }

    public OutputStream getOutputStream(String fileName) throws IOException {
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + convertEncode(fileName));
        return response.getOutputStream();
    }

    public String convertEncode(String str) throws UnsupportedEncodingException {
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            return URLEncoder.encode(str, "UTF-8");
        }
        return new String(str.getBytes("UTF-8"), "ISO8859-1");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}