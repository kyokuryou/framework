package org.smarty.core.bank.alipay;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求支付宝参数
 */
public class RequestParams {
    private Map<RequestKey, Object> params = new LinkedHashMap<RequestKey, Object>(9);

    private final int scale = 2;

    public void addParams(RequestKey key, BigDecimal val) {
        if (val == null) {
            return;
        }
        addParams(key, val.setScale(scale).toString());
    }


    public void addParams(RequestKey key, Integer val) {
        addParams(key, String.valueOf(val));
    }

    public void addParams(RequestKey key, String val) {
        if (key == null || val == null || "".equals(val)) {
            return;
        }
        params.put(key, val);
    }

    public Set<Map.Entry<RequestKey, Object>> getAll() {
        if (params == null || params.isEmpty()) {
            return null;
        }
        return params.entrySet();
    }


}
