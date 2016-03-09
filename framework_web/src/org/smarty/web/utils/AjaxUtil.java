package org.smarty.web.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.JsonUtil;
import org.smarty.web.commons.AjaxType;
import org.smarty.web.commons.WebBaseConstant;

/**
 *
 */
public final class AjaxUtil {
	private static Log logger = LogFactory.getLog(AjaxUtil.class);

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
	public static String ajaxJson(HttpServletResponse response, Map<String, ?> jsonMap) {
		return ajax(response, AjaxType.APPLICATION_JSON, JsonUtil.encode(jsonMap));
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
	public static String ajaxJsonWarn(HttpServletResponse response, String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(WebBaseConstant.VIEW_STATUS, WebBaseConstant.VIEW_WARN);
		jsonMap.put(WebBaseConstant.VIEW_MESSAGE, message);
		return ajaxJson(response, jsonMap);
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
	public static String ajaxJsonSuccess(HttpServletResponse response, String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(WebBaseConstant.VIEW_STATUS, WebBaseConstant.VIEW_SUCCESS);
		jsonMap.put(WebBaseConstant.VIEW_MESSAGE, message);
		return ajaxJson(response, jsonMap);
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
	public static String ajaxJsonError(HttpServletResponse response, String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(WebBaseConstant.VIEW_STATUS, WebBaseConstant.VIEW_ERROR);
		jsonMap.put(WebBaseConstant.VIEW_MESSAGE, message);
		return ajaxJson(response, jsonMap);
	}

	/**
	 * AJAX输出文本
	 * 等价于:
	 *
	 * @param content 内容
	 * @return null
	 */
	public static String ajax(HttpServletResponse response, AjaxType type, String content) {
		PrintWriter printWriter = getPrintWrite(response, type);
		if (printWriter != null) {
			printWriter.write(content);
			printWriter.flush();
		}
		return null;
	}

	public static PrintWriter getPrintWrite(HttpServletResponse response, AjaxType type) {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType(type.toString() + ";charset=UTF-8");
		try {
			return response.getWriter();
		} catch (IOException e) {
			logger.warn(e);
		}
		return null;
	}
}
