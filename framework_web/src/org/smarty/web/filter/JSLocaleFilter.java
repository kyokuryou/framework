package org.smarty.web.filter;

import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.DateUtil;
import org.smarty.web.commons.FreemarkerManager;

/**
 * Created Date 2015/04/09
 *
 * @author kyokuryou
 * @version 1.0
 */
public class JSLocaleFilter implements Filter {
	private static Log logger = LogFactory.getLog(JSLocaleFilter.class);
	private FreemarkerManager freemarkerManager;
	private String incPath;

	public void setIncPath(String incPath) {
		this.incPath = incPath;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		ServletOutputStream osw = getHttpHeader(response);
		try {
			freemarkerManager.outputTemplate(incPath, null, osw);
		} catch (IOException e) {
			logger.warn(e);
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

	public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
		this.freemarkerManager = freemarkerManager;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
