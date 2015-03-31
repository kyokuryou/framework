package org.core.exception;

/**
 * 反射调用方法时抛出 IllegalAccessException, IllegalArgumentException,InvocationTargetException由此类取代
 */
public class InvokeMethodException extends Exception {
    public InvokeMethodException() {
    }

    public InvokeMethodException(Throwable cause) {
        super(cause);
    }
}
