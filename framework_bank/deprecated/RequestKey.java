package org.smarty.core.bank.alipay;

/**
 * 请求参数key
 */
public enum RequestKey implements Keys {

    /**
     * 接口名称
     */
    MUST_SERVICE("service"),
    /**
     * 合作身份证ID
     */
    MUST_PARTNER("partner"),
    /**
     * 参数编码字符集
     */
    MUST_INPUT_CHARSET("_input_charset"),
    /**
     * 签名方式
     */
    MUST_SIGN_TYPE("sign_type"),
    /**
     * 密钥
     */
    MUST_SIGN("sign"),
    /**
     * 服务器异步通知页面路径
     */
    MUST_NOTIFY_URL("notify_url"),
    /**
     * 页面跳转同步通知页面路径
     */
    MUST_RETURN_URL("return_url"),
    /**
     * 商户网站唯一订单号String(64)
     */
    MUST_OUT_TRADE_NO("out_trade_no"),
    /**
     * 商品名称String(256)
     */
    MUST_SUBJECT("subject"),
    /**
     * 收款类型只支持1:商品购买
     */
    MUST_PAYMENT_TYPE("payment_type"),
    /**
     * 物流类型
     */
    MUST_LOGISTICS_TYPE("logistics_type"),

    /**
     * 物流费用
     */
    MUST_LOGISTICS_FEE("logistics_fee"),

    /**
     * 物流支付类型
     */
    MUST_LOGISTICS_PAY_MENT("logistics_pay_ment"),

    /**
     * 卖家支付宝帐号
     */
    MUST_SELLER_EMAIL("seller_email"),

    /**
     * 商品单价
     */
    MUST_PRICE("price"),

    /**
     * 商品数量
     */
    MUST_QUANTITY("quantity"),

    /**
     * 商品描述String(400)(可选)
     */
    OPT_BODY("body"),

    /**
     * 折扣(可选)
     */
    OPT_DISCOUNT("discount"),

    /**
     * 交易金额(可选)
     */
    OPT_TOTAL_FEE("total_fee"),

    /**
     * 商品展示URL String(400) (可选)
     */
    OPT_SHOW_URL("show_url"),

    /**
     * 卖家支付宝帐号对应的支付宝唯一用户名 String(300) (可选)
     */
    OPT_SELLER_ID("seller_id"),

    /**
     * 买家支付宝帐号(可选)
     */
    OPT_BUYER_EMAIL("buyer_email"),

    /**
     * 买家支付宝帐号对应的支付宝唯一用户名(可选)
     */
    OPT_BUYER_ID("buyer_email"),

    /**
     * 卖家别名支付宝帐号(可选)
     */
    OPT_SELLER_ACCOUNT_NAME("seller_account_name"),

    /**
     * 买家别名支付宝帐号(可选)
     */
    OPT_BUYER_ACCOUNT_NAME("buyer_account_name"),

    /**
     * 收货人姓名(可选)
     */
    OPT_RECEIVE_NAME("receive_name"),

    /**
     * 收货人地址(可选)
     */
    OPT_RECEIVE_ADDRESS("receive_address"),

    /**
     * 收货人邮编(可选)
     */
    OPT_RECEIVE_ZIP("receive_zip"),

    /**
     * 收货人电话(可选)
     */
    OPT_RECEIVE_PHONE("receive_phone"),

    /**
     * 收货人手机(可选)
     */
    OPT_RECEIVE_MOBILE("receive_mobile"),


    /**
     * 物流类型1(可选)
     */
    OPT_LOGISTICS_TYPE_1("logistics_type_1"),

    /**
     * 物流费用1(可选)
     */
    OPT_LOGISTICS_FEE_1("logistics_fee_1"),

    /**
     * 物流支付类型1(可选)
     */
    OPT_LOGISTICS_PAY_MENT_1("logistics_pay_ment_1"),


    /**
     * 物流类型2(可选)
     */
    OPT_LOGISTICS_TYPE_2("logistics_type_2"),

    /**
     * 物流费用2(可选)
     */
    OPT_LOGISTICS_FEE_2("logistics_fee_2"),

    /**
     * 物流支付类型2(可选)
     */
    OPT_LOGISTICS_PAY_MENT_2("logistics_pay_ment_2"),

    /**
     * 买家逾期不付款,自动关闭交易 1m~15d,m-分钟,h-小时,d-天,1c-当天(可选)
     */
    OPT_IT_B_PAY("it_b_pay"),

    /**
     * 卖家逾期不发货,允许买家退款,单位为天(d)(可选)
     */
    OPT_T_S_SEND_1("t_s_send_1"),

    /**
     * 卖家逾期不发货,建议买家退款,单位为天(d)(可选)
     */
    OPT_T_S_SEND_2("t_s_send_2"),

    /**
     * 买家逾期不确认收货,自动完成交易(平邮),单位为天(d) (可选)
     */
    OPT_T_B_REC_POST("t_b_rec_post"),

    /**
     * 防钓鱼时间戳(如果已经开通防钓鱼时间戳验证,此字段必须)(可选)
     */
    OPT_ANTI_PHISHING_KEY("anti_phishing_key"),

    /**
     * 快捷登录授权令牌(可选)
     */
    OPT_TOKEN("token");

    private String value;

    private RequestKey(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
