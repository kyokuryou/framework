package org.smarty.web.support.filter;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.DateUtil;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.web.commons.FreemarkerManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created Date 2015/04/09
 *
 * @author kyokuryou
 * @version 1.0
 */
public final class JSLocaleFilter extends SingleRequestFilter implements InitializingBean {
	private static Log logger = LogFactory.getLog(JSLocaleFilter.class);
	@Autowired
	private FreemarkerManager freemarkerManager;
	@Value("${js.locale.url:/js/}")
	private String jsLocale;
	@Value("${resources.js.template}")
	private String jsTemplate;

	@Override
	protected String getFilterProcessesUrl() {
		return jsLocale;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ObjectUtil.assertNotEmpty(jsTemplate, "property[resources.js.template] must not be null or empty");
		ObjectUtil.assertNotEmpty(freemarkerManager, "freemarkerManager must not be null or empty");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletOutputStream osw = getHttpHeader(response);
		try {
			freemarkerManager.outputTemplate(jsTemplate, null, osw);
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

	@Override
	public int getOrder() {
		return 1;
	}
}
