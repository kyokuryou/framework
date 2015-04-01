package org.smarty.core.dto;

import org.smarty.core.Model;

/**
 * 角色-资源映射
 */
public class RoleResourceDto extends Model {
    private String rolePkId;
    // 角色名称
    private String roleName;
    // 角色标识
    private String roleValue;
    // 是否为系统内置角色
    private Boolean roleIsSystem;
    // 描述
    private String roleDescription;

    private String resourcePkId;
    // 资源名称
    private String resourceName;
    // 资源标识
    private String resourceValue;
    // 是否为系统内置资源
    private Boolean resourceIsSystem;
    // 描述
    private String resourceDescription;
    // 排序
    private Integer resourceOrderList;

    public String getRolePkId() {
        return rolePkId;
    }

    public void setRolePkId(String rolePkId) {
        this.rolePkId = rolePkId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    public Boolean getRoleIsSystem() {
        return roleIsSystem;
    }

    public void setRoleIsSystem(Boolean roleIsSystem) {
        this.roleIsSystem = roleIsSystem;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getResourcePkId() {
        return resourcePkId;
    }

    public void setResourcePkId(String resourcePkId) {
        this.resourcePkId = resourcePkId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    public Boolean getResourceIsSystem() {
        return resourceIsSystem;
    }

    public void setResourceIsSystem(Boolean resourceIsSystem) {
        this.resourceIsSystem = resourceIsSystem;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public Integer getResourceOrderList() {
        return resourceOrderList;
    }

    public void setResourceOrderList(Integer resourceOrderList) {
        this.resourceOrderList = resourceOrderList;
    }
}
