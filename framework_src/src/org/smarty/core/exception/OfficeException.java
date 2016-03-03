package org.smarty.core.exception;

/**
 * Office输出/入或流错误时发生异常
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class OfficeException extends Exception {
	private static final long serialVersionUID = -3387516993124229948L;

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
