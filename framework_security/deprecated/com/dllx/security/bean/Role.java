package com.dllx.security.bean;

import com.dllx.Model;

import java.util.List;

/**
 * 角色
 */
public class Role extends Model {

	private static final long serialVersionUID = -6614052029623997372L;

	private String name;// 角色名称
	private Boolean isSystem;// 是否为系统内置角色
	private String description;// 描述
    private String value;// 角色标识
	
	private List<Admin> adminList;// 管理员
	private List<Resource> resourceList;// 资源

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}