package org.smarty.core.http;

import org.springframework.http.HttpStatus;

/**
 * http消息器
 */
public class HttpMessage {
    private int success;
    private String message;
    private String data;
    private HttpStatus status;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
