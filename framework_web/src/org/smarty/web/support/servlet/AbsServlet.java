package org.smarty.web.support.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.RequestContext;

/**
 * Action基类
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class AbsServlet {
	private static Log logger = LogFactory.getLog(AbsServlet.class);

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected RequestContext requestContext;

	public String getBase() {
		return request.getContextPath();
	}

	@ModelAttribute
	public void setHttpServlet(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.requestContext = new RequestContext(request, response);
		this.session = request.getSession();
		setInstanceState(request);
	}

	public void setInstanceState(HttpServletRequest request) {

	}

	public void addMessageAttribute(String key, String msgKey, Object... values) {
		request.setAttribute(key, requestContext.getMessage(msgKey, values));
	}

	public void sendRedirect(String url) throws IOException {
		String redirectUrl = request.getContextPath() + url;
		redirectUrl = response.encodeRedirectURL(redirectUrl);

		if (logger.isDebugEnabled()) {
			logger.debug("Redirecting to '" + redirectUrl + "'");
		}
		response.sendRedirect(redirectUrl);
	}

	public String getParameter(String key) {
		return request.getParameter(key);
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

	protected byte[] sendPost(URL url, byte[] data) throws IOException {
		// RestTemplate rest = new RestTemplate();
		HttpURLConnection con = getConnection(url);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestMethod("POST");
		return urlStream(con, data);
	}

	protected byte[] sendGet(URL url) throws IOException {
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
			logger.warn(e);
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