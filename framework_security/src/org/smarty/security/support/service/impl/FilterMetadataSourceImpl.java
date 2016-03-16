package org.smarty.security.support.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.bean.ResourceSecurity;
import org.smarty.security.support.service.FilterMetadataSource;
import org.smarty.security.support.service.SecurityService;
import org.smarty.security.utils.SecurityUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * MetadataSourceImpl
 */
public class FilterMetadataSourceImpl implements FilterMetadataSource, InitializingBean {
	private SecurityService securityService;
	private final Map<String, Collection<ConfigAttribute>> configAttributes = new HashMap<String, Collection<ConfigAttribute>>();

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ObjectUtil.assertNotEmpty(securityService, "securityService must not null");
		loadMetadataSource();
	}

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


	@Override
	public void refresh() {
		if (!configAttributes.isEmpty()) {
			configAttributes.clear();
		}
		loadMetadataSource();
	}

	private void loadMetadataSource() {
		List<ResourceSecurity> resources = securityService.getResourceList();
		for (ResourceSecurity resource : resources) {
			configAttributes.put(resource.getValue(), resource.getConfigAttributes());
		}
		SecurityUtil.setFilterMetadataSource(this);
	}

	private Collection<ConfigAttribute> getDefaultAuthorities() {
		List<ConfigAttribute> calist = new LinkedList<ConfigAttribute>();
		calist.add(new SecurityConfig("ROLE_NULL"));
		return calist;
	}
}