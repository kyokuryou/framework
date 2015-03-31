package org.core.support.web;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.core.bean.Pager;
import org.core.bean.SystemConfig;
import org.core.logger.RuntimeLogger;
import org.core.utils.JsonUtil;
import org.core.utils.SystemConfigUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Action基类
 */
public class BaseAction extends ActionSupport {
    private static RuntimeLogger logger = new RuntimeLogger(BaseAction.class);
    private static final long serialVersionUID = 6718838822334455667L;

    public static final String VIEW = "view";
    public static final String LIST = "list";
    public static final String STATUS = "status";
    public static final String WARN = "warn";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String CONTENT = "content";

    protected String id;
    protected String[] ids;
    protected Pager pager;
    protected String redirectionUrl;// 操作提示后的跳转URL,为null则返回前一页
    public Theme useTheme;

    public enum Theme {
        defPlan, planA, planB, planC, planD, planE, planF, planG
    }

    public String getBase() {
        return getRequest().getContextPath();
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
        ActionContext ac = ActionContext.getContext();
        ac.setLocale(new Locale(language, country));
    }

    /**
     * 获取Attribute
     *
     * @return object
     */
    public Object getAttribute(String name) {
        return ServletActionContext.getRequest().getAttribute(name);
    }

    /**
     * 设置Attribute
     *
     * @param name  key
     * @param value value
     */
    public void setAttribute(String name, Object value) {
        ServletActionContext.getRequest().setAttribute(name, value);
    }

    /**
     * 获取Parameter
     *
     * @param name key
     * @return value
     */
    public String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取Parameter数组
     *
     * @param name key
     * @return String数组
     */
    public String[] getParameterValues(String name) {
        return getRequest().getParameterValues(name);
    }

    /**
     * 获取Session
     *
     * @param name key
     * @return value
     */
    public Object getSession(String name) {
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        return session.get(name);
    }

    /**
     * 获取Session
     *
     * @return Map
     */
    public Map<String, Object> getSession() {
        ActionContext actionContext = ActionContext.getContext();
        return actionContext.getSession();
    }

    /**
     * 获取Session
     *
     * @param name  key
     * @param value value
     */
    public void setSession(String name, Object value) {
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        session.put(name, value);
    }

    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 获取Response
     *
     * @return HttpServletResponse
     */

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * 获取ServletContext
     *
     * @return ServletContext
     */

    public ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
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
            HttpServletResponse response = getResponse();
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
        getResponse().setHeader("progma", "no-cache");
        getResponse().setHeader("Cache-Control", "no-cache");
        getResponse().setHeader("Cache-Control", "no-store");
        getResponse().setDateHeader("Expires", 0);
    }

    public OutputStream getOutputStream(String fileName) throws IOException {
        HttpServletResponse response = getResponse();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + convertEncode(fileName));
        return response.getOutputStream();
    }

    public String convertEncode(String str) throws UnsupportedEncodingException {
        if (getRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
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

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }
}