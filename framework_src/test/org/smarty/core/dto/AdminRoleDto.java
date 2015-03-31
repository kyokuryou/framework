package org.smarty.core.dto;

import org.smarty.core.Model;

import java.util.Date;

/**
 * 管理员-角色映射
 */
public class AdminRoleDto extends Model {

    private String adminPkId;
    // 部门
    private String adminDepartment;
    // E-mail
    private String adminEmail;
    // 账号是否启用
    private Boolean adminIsEnabled;
    // 账号是否过期
    private Boolean adminIsExpired;
    // 账号是否锁定
    private Boolean adminIsLocked;
    // 凭证是否过期
    private Boolean adminIsCredentialsExpired;
    // 帐号锁定时间
    private Date adminLockedDate;
    // 最后登录日期
    private Date adminLoginDate;
    // 连续登录失败的次数
    private Integer adminLoginFailureCount;
    // 最后登录IP
    private String adminLoginIp;
    // 姓名
    private String adminName;
    // 密码
    private String adminPassword;
    // 用户名
    private String adminUsername;

    private String rolePkId;
    // 角色名称
    private String roleName;
    // 角色标识
    private String roleValue;
    // 是否为系统内置角色
    private Boolean roleIsSystem;
    // 描述
    private String roleDescription;

    public String getAdminPkId() {
        return adminPkId;
    }

    public void setAdminPkId(String adminPkId) {
        this.adminPkId = adminPkId;
    }

    public String getAdminDepartment() {
        return adminDepartment;
    }

    public void setAdminDepartment(String adminDepartment) {
        this.adminDepartment = adminDepartment;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public Date getAdminLockedDate() {
        return adminLockedDate;
    }

    public void setAdminLockedDate(Date adminLockedDate) {
        this.adminLockedDate = adminLockedDate;
    }

    public Date getAdminLoginDate() {
        return adminLoginDate;
    }

    public void setAdminLoginDate(Date adminLoginDate) {
        this.adminLoginDate = adminLoginDate;
    }

    public Integer getAdminLoginFailureCount() {
        return adminLoginFailureCount;
    }

    public void setAdminLoginFailureCount(Integer adminLoginFailureCount) {
        this.adminLoginFailureCount = adminLoginFailureCount;
    }

    public String getAdminLoginIp() {
        return adminLoginIp;
    }

    public void setAdminLoginIp(String adminLoginIp) {
        this.adminLoginIp = adminLoginIp;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }



    public String getPassword() {
        return adminPassword;
    }

    public String getUsername() {
        return adminUsername;
    }

    public boolean isAccountNonExpired() {
        return !adminIsExpired;
    }

    public boolean isAccountNonLocked() {
        return !adminIsLocked;
    }

    public boolean isCredentialsNonExpired() {
        return !adminIsCredentialsExpired;
    }

    public boolean isEnabled() {
        return adminIsEnabled;
    }

    public void setAdminIsEnabled(Boolean adminIsEnabled) {
        this.adminIsEnabled = adminIsEnabled;
    }

    public void setAdminIsExpired(Boolean adminIsExpired) {
        this.adminIsExpired = adminIsExpired;
    }

    public void setAdminIsLocked(Boolean adminIsLocked) {
        this.adminIsLocked = adminIsLocked;
    }

    public void setAdminIsCredentialsExpired(Boolean adminIsCredentialsExpired) {
        this.adminIsCredentialsExpired = adminIsCredentialsExpired;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

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
}
