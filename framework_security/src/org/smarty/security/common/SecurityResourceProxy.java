package org.smarty.security.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.bean.ResourceSecurity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * SecurityResourceProxy
 */
public final class SecurityResourceProxy implements InitializingBean {
	private ISecurityService securityService;
	private SecurityMetadataSourceImpl securityMetadataSource;

	@Override
	public void afterPropertiesSet() throws Exception {
		ObjectUtil.assertNotEmpty(securityService, "securityService must not be null");
		securityMetadataSource = new SecurityMetadataSourceImpl();
		securityMetadataSource.refreshResource();
	}

	public void securityInterceptor(FilterSecurityInterceptor securityInterceptor) {
		if (securityInterceptor == null) {
			return;
		}
		securityInterceptor.setSecurityMetadataSource(securityMetadataSource);
	}

	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

	public void refreshMetadataSource() {
		securityMetadataSource.refreshResource();
	}


	private class SecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
		private final Map<String, Collection<ConfigAttribute>> configAttributes = new HashMap<String, Collection<ConfigAttribute>>();

		@Override
		public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
			FilterInvocation fi = ((FilterInvocation) o);
			HttpServletRequest request = fi.getHttpRequest();
			Set<Map.Entry<String, Collection<ConfigAttribute>>> mes = configAttributes.entrySet();
			for (Map.Entry<String, Collection<ConfigAttribute>> me : mes) {
				RequestMatcher reUrl = new AntPathRequestMatcher(me.getKey());
				if (reUrl.matches(request)) {
					return me.getValue();
				}
			}
			return getDefaultAuthorities();
		}

		@Override
		public Collection<ConfigAttribute> getAllConfigAttributes() {
			return null;
		}

		@Override
		public boolean supports(Class<?> aClass) {
			return true;
		}

		public final void refreshResource() {
			if (!configAttributes.isEmpty()) {
				configAttributes.clear();
			}
			List<ResourceSecurity> resources = securityService.getResourceList();
			for (ResourceSecurity resource : resources) {
				configAttributes.put(resource.getValue(), resource.getConfigAttributes());
			}
		}

		private Collection<ConfigAttribute> getDefaultAuthorities() {
			List<ConfigAttribute> calist = new LinkedList<ConfigAttribute>();
			calist.add(new SecurityConfig("ROLE_NULL"));
			return calist;
		}
	}
}
