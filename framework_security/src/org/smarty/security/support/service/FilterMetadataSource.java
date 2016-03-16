package org.smarty.security.support.service;

import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * FilterMetadataSource
 */
public interface FilterMetadataSource extends FilterInvocationSecurityMetadataSource {
	void refresh();
}
