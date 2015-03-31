package org.core.bank;

import java.util.Map;

/**
 * 通知处理类
 */
public abstract class BankNotify {
    protected String signType;
    protected String key;
    protected String inputCharset;
    protected String partner;



    /**
     * 验证消息是否是发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public abstract boolean verify(Map<String, String> params);

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
