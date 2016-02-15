package org.smarty.security.common;

import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.security.bean.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 后台权限、资源对应关系
 */
public class SecurityMetadata implements FilterInvocationSecurityMetadataSource, BeanFactoryAware {
	private final Log logger = LogFactory.getLog(SecurityMetadata.class);

	private ISecurityService securityService;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
		FilterInvocation fi = ((FilterInvocation) o);
		HttpServletRequest request = fi.getHttpRequest();
		String url = getRequestPath(request);
		logger.debug(url);

		List<Resource> resources = securityService.getResourceList(url);
		for (Resource resource : resources) {
			RequestMatcher reUrl = new AntPathRequestMatcher(resource.getValue());
			logger.debug(reUrl.toString());
			if (reUrl.matches(request)) {
				return resource.getConfigAttributes();
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return true;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.securityService = beanFactory.getBean(ISecurityService.class);
	}

	private String getRequestPath(HttpServletRequest request) {
		String url = request.getServletPath();

		if (request.getPathInfo() != null) {
			url += request.getPathInfo();
		}
		return url;
	}
}