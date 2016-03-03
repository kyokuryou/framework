package org.smarty.core.exception;

/**
 * 反射创建对象时抛出 InstantiationException,IllegalAccessException, ClassNotFoundException, InvocationTargetException由此类取代
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class InstanceClassException extends Exception {
	private static final long serialVersionUID = -3387516993124229943L;

	public InstanceClassException() {
	}

	public InstanceClassException(Throwable cause) {
		super(cause);
	}
}
