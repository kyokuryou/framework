package org.smarty.security.bean;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.smarty.core.utils.LogicUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

/**
 * 资源
 */
public class Resource {
	private int id;
	private String value;
	private Collection<ConfigAttribute> configAttributes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Collection<ConfigAttribute> getConfigAttributes() {
		return configAttributes;
	}

	public void setAuthorities(List<String> authorities) {
		if (LogicUtil.isEmptyCollection(authorities)) {
			return;
		}
		List<ConfigAttribute> calist = new LinkedList<ConfigAttribute>();
		for (String auth : authorities) {
			calist.add(new SecurityConfig(auth));
		}
		this.configAttributes = calist;
	}
}
