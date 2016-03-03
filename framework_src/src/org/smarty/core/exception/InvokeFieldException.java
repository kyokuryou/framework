package org.smarty.core.exception;

/**
 * 反射调用方法时抛出 NoSuchFieldException,SecurityException 由此类取代
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class InvokeFieldException extends Exception {
	private static final long serialVersionUID = -3387516993124229944L;

	public InvokeFieldException() {
	}

	public InvokeFieldException(Throwable cause) {
		super(cause);
	}
}
