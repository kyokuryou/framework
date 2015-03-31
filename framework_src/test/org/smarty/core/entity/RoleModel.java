package org.smarty.core.entity;

import org.smarty.core.Model;

/**
 * 角色
 */
public class RoleModel extends Model {
    // 角色名称
    private String name;
    // 角色标识
    private String value;
    // 是否为系统内置角色
    private Boolean isSystem;
    // 描述
    private String description;

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
}
