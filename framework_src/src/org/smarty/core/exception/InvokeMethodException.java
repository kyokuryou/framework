package org.smarty.core.exception;

/**
 * 反射调用方法时抛出 NoSuchMethodException, SecurityException,InvocationTargetException由此类取代
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class InvokeMethodException extends Exception {
	public InvokeMethodException() {
	}

	public InvokeMethodException(Throwable cause) {
		super(cause);
	}
}
