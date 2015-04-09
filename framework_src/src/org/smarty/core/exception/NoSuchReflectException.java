package org.smarty.core.exception;

/**
 * 反射获得属性,方法时抛出的 NoSuchMethodException,NoSuchFieldException由此类取代
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class NoSuchReflectException extends Exception {
    public NoSuchReflectException() {
        super();
    }

    public NoSuchReflectException(Throwable cause) {
        super(cause);
    }
}
