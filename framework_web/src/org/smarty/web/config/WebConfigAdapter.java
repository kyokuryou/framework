package org.smarty.web.config;

import java.util.List;
import org.smarty.core.config.SystemConfigAdapter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

/**
 * WebConfigAdapter
 */
public abstract class WebConfigAdapter extends SystemConfigAdapter {
	protected void configure(ViewResolverRegistry registry) {

	}

	protected void configure(AsyncSupportConfigurer configurer) {

	}

	protected void configure(List<HttpMessageConverter<?>> converters) {

	}

	protected void configure(DefaultServletHandlerConfigurer configurer) {

	}

	protected void addInterceptors(InterceptorRegistry registry) {

	}
}
