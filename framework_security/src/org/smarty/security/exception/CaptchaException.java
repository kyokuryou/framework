package org.smarty.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * CaptchaException
 */
public class CaptchaException extends AuthenticationException {
	static final long serialVersionUID = -7034897190745766170L;

	public CaptchaException(String msg) {
		super(msg);
	}

	public CaptchaException(String msg, Throwable t) {
		super(msg, t);
	}
}
