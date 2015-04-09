package org.smarty.core.bean;

import java.util.List;

/**
 * CSV定义
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class Csv {
    /**
     * 编码,默认UTF-8
     */
    private String charset = "UTF-8";
    /**
     * 列头定义
     */
    private String[] headers;
    /**
     * 数据
     */
    private List<Object[]> values;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<Object[]> getValues() {
        return values;
    }

    public void setValues(List<Object[]> values) {
        this.values = values;
    }
}
