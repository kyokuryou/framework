package org.smarty.core.bean;

import org.smarty.core.Model;

/**
 * 系统配置
 */
public class SystemConfig extends Model {
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 系统版本
     */
    private String systemVersion;

    /**
     * 系统版权
     */
    private String systemCopyright;
    /**
     * 系统描述
     */
    private String systemDescription;
    /**
     * url
     */
    private String systemUrl;

    /**
     * 首页页面关键词
     */
    private String metaKeywords;
    /**
     *  首页页面描述
     */
    private String metaDescription;

    /**
     * 精确位数
     */
    private Integer mathScale;

    /**
     * 文件上传最大值,0表示无限制,单位KB
     */
    private Integer uploadLimit;
    /**
     * 同一账号允许连续登录失败的最大次数，超出次数后将锁定其账号
     */
    private Integer loginFailureLockCount;
    /**
     * 账号锁定时间(单位：分钟,0表示永久锁定)
     */
    private Integer loginFailureLockTime;
    /**
     * 登录SessionId
     */
    private String loginSessionId;
    /**
     * 登录用户名的Cookie名称
     */
    private String loginCookieName;
    /**
     * 密码找回Key分隔符
     */
    private String passwordRecoverKey;
    /**
     * 密码找回Key有效时间（单位：分钟）
     */
    private Integer passwordRecoverPeriod;

    /**
     * 发件人邮箱
     */
    private String smtpFromMail;
    /**
     *  SMTP服务器地址
     */
    private String smtpHost;
    /**
     * SMTP服务器端口
     */
    private Integer smtpPort;
    /**
     * SMTP用户名
     */
    private String smtpUsername;
    /**
     *  SMTP密码
     */
    private String smtpPassword;

    /**
     * 默认构造
     */
    public SystemConfig (){

    }

    /**
     * 创建与参数值一致的对象
     * @param systemConfig SystemConfig对象
     */
    public SystemConfig(SystemConfig systemConfig) {
        this.systemName = systemConfig.getSystemName();
        this.systemVersion = systemConfig.getSystemVersion();
        this.systemCopyright = systemConfig.getSystemCopyright();
        this.systemDescription = systemConfig.getSystemDescription();
        this.systemUrl = systemConfig.getSystemUrl();
        this.metaKeywords = systemConfig.getMetaKeywords();
        this.metaDescription = systemConfig.getMetaDescription();
        this.mathScale = systemConfig.getMathScale();
        this.uploadLimit = systemConfig.getUploadLimit();
        this.loginFailureLockCount = systemConfig.getLoginFailureLockCount();
        this.loginFailureLockTime = systemConfig.getLoginFailureLockTime();
        this.loginSessionId = systemConfig.getLoginSessionId();
        this.loginCookieName = systemConfig.getLoginCookieName();
        this.passwordRecoverKey = systemConfig.getPasswordRecoverKey();
        this.passwordRecoverPeriod = systemConfig.getPasswordRecoverPeriod();
        this.smtpFromMail = systemConfig.getSmtpFromMail();
        this.smtpHost = systemConfig.getSmtpHost();
        this.smtpPort = systemConfig.getSmtpPort();
        this.smtpUsername = systemConfig.getSmtpUsername();
        this.smtpPassword = systemConfig.getSmtpPassword();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getSystemCopyright() {
        return systemCopyright;
    }

    public void setSystemCopyright(String systemCopyright) {
        this.systemCopyright = systemCopyright;
    }

    public String getSystemDescription() {
        return systemDescription;
    }

    public void setSystemDescription(String systemDescription) {
        this.systemDescription = systemDescription;
    }

    public String getSystemUrl() {
        return systemUrl;
    }

    public void setSystemUrl(String systemUrl) {
        this.systemUrl = systemUrl;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Integer getMathScale() {
        return mathScale;
    }

    public void setMathScale(Integer mathScale) {
        this.mathScale = mathScale;
    }

    public Integer getUploadLimit() {
        return uploadLimit;
    }

    public void setUploadLimit(Integer uploadLimit) {
        this.uploadLimit = uploadLimit;
    }

    public Integer getLoginFailureLockCount() {
        return loginFailureLockCount;
    }

    public void setLoginFailureLockCount(Integer loginFailureLockCount) {
        this.loginFailureLockCount = loginFailureLockCount;
    }

    public Integer getLoginFailureLockTime() {
        return loginFailureLockTime;
    }

    public void setLoginFailureLockTime(Integer loginFailureLockTime) {
        this.loginFailureLockTime = loginFailureLockTime;
    }

    public String getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(String loginSessionId) {
        this.loginSessionId = loginSessionId;
    }

    public String getLoginCookieName() {
        return loginCookieName;
    }

    public void setLoginCookieName(String loginCookieName) {
        this.loginCookieName = loginCookieName;
    }

    public String getPasswordRecoverKey() {
        return passwordRecoverKey;
    }

    public void setPasswordRecoverKey(String passwordRecoverKey) {
        this.passwordRecoverKey = passwordRecoverKey;
    }

    public Integer getPasswordRecoverPeriod() {
        return passwordRecoverPeriod;
    }

    public void setPasswordRecoverPeriod(Integer passwordRecoverPeriod) {
        this.passwordRecoverPeriod = passwordRecoverPeriod;
    }

    public String getSmtpFromMail() {
        return smtpFromMail;
    }

    public void setSmtpFromMail(String smtpFromMail) {
        this.smtpFromMail = smtpFromMail;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }
}