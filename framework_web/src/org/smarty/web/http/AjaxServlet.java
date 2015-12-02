package org.smarty.web.http;

import org.smarty.core.utils.JsonUtil;
import org.smarty.web.commons.WebBaseConstant;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * ajax servlet高级类
 */
public abstract class AjaxServlet extends BaseServlet {

    /**
     * AJAX输出
     *
     * @param content 内容
     * @param type    类型(如:"text/plain","text/html","text/xml"等)
     * @return null
     */
    public String ajax(String content, String type) {
        PrintWriter pw = getPrintWrite(type);
        if (pw != null) {
            pw.write(content);
            pw.flush();
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
        return ajax(jsonString, "application/json");
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
    public String ajaxJson(Map<String, ?> jsonMap) {
        return ajax(JsonUtil.encode(jsonMap), "application/json");
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
        jsonMap.put(WebBaseConstant.VIEW_STATUS, WebBaseConstant.VIEW_WARN);
        jsonMap.put(WebBaseConstant.VIEW_MESSAGE, message);
        return ajaxJson(jsonMap);
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
        jsonMap.put(WebBaseConstant.VIEW_STATUS, WebBaseConstant.VIEW_SUCCESS);
        jsonMap.put(WebBaseConstant.VIEW_MESSAGE, message);
        return ajaxJson(jsonMap);
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
        jsonMap.put(WebBaseConstant.VIEW_STATUS, WebBaseConstant.VIEW_ERROR);
        jsonMap.put(WebBaseConstant.VIEW_MESSAGE, message);
        return ajaxJson(jsonMap);
    }
}
