package org.smarty.core.bank.alipay;

/**
 * 响应参数key
 */
public enum ResponseKey implements Keys{
    /**
     * 成功标识
     */
    MUST_IS_SUCCESS("is_success"),
    /**
     * 合作身份者ID
     */
    MUST_PARTNERID("partnerld"),
    /**
     * 签名方式
     */
    MUST_SIGN_TYPE("sign_type"),
    /**
     * 签名
     */
    MUST_SIGN("sign"),

    /**
     * 参数编码字符集
     */
    MUST_CHARSET("charset"),
    /**
     * 通知ID
     */
    MUST_NOTIFY_ID("notify_id"),
    /**
     * 通知类型
     */
    MUST_NOTIFY_TYPE("notify_type"),
    /**
     * 通知时间
     */
    MUST_NOTIFY_TIME("notify_time"),
    /**
     * 交易号
     */
    MUST_TRADE_NO("trade_no"),
    /**
     * 商品名称
     */
    MUST_SUBJECT("subject"),
    /**
     * 商品单价
     */
    MUST_PRICE("price"),
    /**
     * 商品数量
     */
    MUST_QUANTITY("quantity"),
    /**
     * 商品折扣
     */
    MUST_DISCOUNT("discount"),
    /**
     * 总额
     */
    MUST_TOTAL_FEE("total_fee"),

    /**
     * 卖家支付宝帐号
     */
    MUST_SELLER_EMAIL("seller_email"),
    /**
     * 卖家支付宝帐号对应的支付宝唯一用户号
     */
    MUST_SELLER_ID("seller_id"),
    /**
     * 买家支付宝帐号
     */
    MUST_BUYER_EMAIL("buyer_email"),
    /**
     * 买家ID
     */
    MUST_BUYER_ID("buyer_id"),
    /**
     * 交易状态
     */
    MUST_TRADE_STATUS("trade_status"),
    /**
     * 总价是否调整过
     */
    MUST_IS_TOTAL_FEE_ADJUST("is_total_fee_adjust"),
    /**
     * 是否使用红包
     */
    MUST_USE_COUPON("use_coupon"),
    /**
     * 商户网站唯一订单号
     */
    OPT_OUT_TRADE_NO("out_trade_no"),
    /**
     * 商品描述
     */
    OPT_BODY("body"),
    /**
     * 收款类型
     */
    OPT_PAYMENT_TYPE("payment_type"),
    /**
     * 物流类型
     */
    OPT_LOGISTICS_TYPE("logistics_type"),
    /**
     * 物流运费
     */
    OPT_LOGISTICS_FEE("logistics_fee"),
    /**
     * 物流支付类型
     */
    OPT_LOGISTICS_PAYMENT("logistics_payment"),

    /**
     * 收货人姓名
     */
    OPT_RECEIVE_NAME("receive_name"),

    /**
     * 收货人地址
     */
    OPT_RECEIVE_ADDRESS("receive_address"),
    /**
     * 收货人邮编
     */
    OPT_RECEIVE_ZIP("receive_zip"),
    /**
     * 收货人电话
     */
    OPT_RECEIVE_PHONE("receive_phone"),
    /**
     * 收货人手机
     */
    OPT_RECEIVE_MOBILE("receive_mobile"),
    /**
     * 退款状态
     */
    OPT_REFUND_STATUS("refund_status"),
    /**
     * 商品展示URL
     */
    OPT_SHOW_URL("show_url"),
    /**
     * 买家动作集合
     */
    OPT_BUYER_ACTIONS("buyer_actions"),
    /**
     * 卖家动作集合
     */
    OPT_SELLER_ACTIONS("seller_actions"),
    /**
     * 交易创建时间
     */
    OPT_GMT_CREATE("gmt_create"),
    /**
     * 交易支付时间
     */
    OPT_GMT_PAYMENT("gmt_payment"),
    /**
     * 物流状态更新时间
     */
    OPT_GMT_LOGISTICS_MODIFY("gmt_logistics_modify"),
    /**
     * 交易退款
     */
    OPT_GMT_REFUND("gmt_refund");

    private String value;

    ResponseKey(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
