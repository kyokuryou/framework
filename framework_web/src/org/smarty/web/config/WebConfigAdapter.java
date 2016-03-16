package org.smarty.web.config;

import java.util.List;
import org.smarty.core.config.SystemConfigAdapter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

/**
 * WebConfigAdapter
 */
public abstract class WebConfigAdapter extends SystemConfigAdapter {
	protected void configure(ViewResolverRegistry registry) {

	}

	protected void configure(List<HttpMessageConverter<?>> converters) {

	}

	protected void addInterceptors(InterceptorRegistry registry) {

	}
}
