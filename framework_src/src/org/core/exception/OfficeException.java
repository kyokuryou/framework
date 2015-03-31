package org.core.exception;

/**
 * Office输出/入或流错误时发生异常
 */
public class OfficeException extends Exception {
    public OfficeException() {
        super();
    }

    public OfficeException(Throwable cause) {
        super(cause);
    }

    public OfficeException(String message) {
        super(message);
    }
}
