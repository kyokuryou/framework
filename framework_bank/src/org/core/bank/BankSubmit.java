package org.core.bank;

import org.apache.struts2.views.freemarker.FreemarkerManager;

import java.util.Map;

/**
 * 接口请求提交类
 */
public abstract class BankSubmit{
    protected FreemarkerManager freemarkerManager;

    protected String ftlPath;
    protected String service;
    protected String partner;
    protected String inputCharset;

    protected String signType;
    protected String key;

    protected String sellerEmail;
    protected String notifyUrl;
    protected String returnUrl;

    public abstract String buildRequest(Map<String,String> params);

    public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
        this.freemarkerManager = freemarkerManager;
    }

    public void setFtlPath(String ftlPath) {
        this.ftlPath = ftlPath;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
