package org.smarty.core.exception;

/**
 * 反射创建对象时抛出 InstantiationException, IllegalAccessException,IllegalArgumentException, InvocationTargetException由此类取代
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class InstanceClassException extends Exception {

    public InstanceClassException() {
    }

    public InstanceClassException(Throwable cause) {
        super(cause);
    }
}
