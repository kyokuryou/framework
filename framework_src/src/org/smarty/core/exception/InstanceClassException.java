package org.smarty.core.exception;

/**
 * 反射创建对象时抛出 InstantiationException, IllegalAccessException,IllegalArgumentException, InvocationTargetException由此类取代
 */
public class InstanceClassException extends Exception {

    public InstanceClassException() {
    }

    public InstanceClassException(Throwable cause) {
        super(cause);
    }
}
