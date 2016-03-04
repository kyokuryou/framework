package org.smarty.web.support.rest;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.smarty.core.utils.ObjectUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriTemplate;

/**
 *
 */
public class RestModel {
	private HttpMethod method = HttpMethod.GET;
	private String url;
	private HttpEntity<?> entity;
	private Map<String, ?> uriVariables;
	private RestHandler handler;

	public RestModel(String url, HttpMethod method) {
		this.url = url;
		this.method = method;
	}

	public URI getUrl() {
		if (ObjectUtil.isEmpty(uriVariables)) {
			return new UriTemplate(url).expand();
		} else {
			return new UriTemplate(expendUrl()).expand(uriVariables);
		}
	}

	public HttpMethod getMethod() {
		return method;
	}

	public HttpEntity<?> getEntity() {
		return entity;
	}

	public void setEntity(HttpEntity<?> entity) {
		this.entity = entity;
	}

	public Map<String, ?> getUriVariables() {
		return uriVariables;
	}

	public void setUriVariables(Map<String, ?> uriVariables) {
		this.uriVariables = uriVariables;
	}

	public RestHandler getHandler() {
		return handler;
	}

	public void setHandler(RestHandler handler) {
		this.handler = handler;
	}

	private String expendUrl() {
		Set<String> keys = uriVariables.keySet();
		if (ObjectUtil.isEmpty(keys)) {
			return url;
		}
		StringBuilder var = new StringBuilder(url + "?");
		Iterator<String> kit = keys.iterator();
		for (int i = 0, len = keys.size(); kit.hasNext(); i++) {
			String key = kit.next();
			var.append(key);
			var.append("={");
			var.append(key);
			var.append("}");
			if (i < len - 1) {
				var.append("&");
			}
		}
		return var.toString();
	}
}
