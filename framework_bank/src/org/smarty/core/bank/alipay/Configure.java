package org.smarty.core.bank.alipay;

/**
 * 设置帐户有关信息及返回路径
 */
public class Configure {
    /**
     * 接口名称
     */
    private String service;
    /**
     * 合作身份者ID，以2088开头由16位纯数字组成的字符串
     */
    private String partner;

    /**
     *  签名方式 不需修改
     */
    private String sign_type = "MD5";

    /**
     * 密钥
     */
    private String sign;

    /**
     * 服务器异步通知页面路径
     */
    private String notify_url;

    /**
     * 页面跳转同步通知页面路径
     */
    private String return_url;

    /**
     * 调试用，创建TXT日志文件夹路径
     */
    public String log_path;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getLog_path() {
        return log_path;
    }

    public void setLog_path(String log_path) {
        this.log_path = log_path;
    }
}
