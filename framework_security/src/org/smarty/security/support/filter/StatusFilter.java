package org.smarty.security.support.filter;

import javax.servlet.http.HttpServletRequest;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.common.SecurityStatus;
import org.smarty.web.support.filter.SingleRequestFilter;
import org.springframework.beans.factory.annotation.Value;

public abstract class StatusFilter extends SingleRequestFilter {
	@Value("${status.parameter:j_status}")
	private String statusParameter;
	@Value("${login.status.url:/status.do}")
	private String statusUrl;

	@Override
	protected final String getFilterProcessesUrl() {
		return statusUrl;
	}

	protected final SecurityStatus getCurrentStatus(HttpServletRequest request) {
		String val = request.getParameter(statusParameter);
		if (ObjectUtil.isEmpty(val)) {
			return SecurityStatus.NONE;
		}
		return SecurityStatus.getSecurityStatus(Integer.valueOf(val));
	}


	@Override
	public final int getOrder() {
		return Integer.MIN_VALUE;
	}
}