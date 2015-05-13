package org.smarty.core.http;

/**
 * http响应监听器
 */
public interface HttpResponseListener {
    void refresh(HttpMessage httpBean);
}
