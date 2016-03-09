package org.smarty.security.common;

/**
 * SecurityStatus
 */
public enum SecurityStatus {
	NONE(0),
	ACCESS_DENIED(1),
	TIMEOUT(2),
	LOGIN_SUCCESS(3),
	LOGIN_FAILURE(4),
	LOGOUT_SUCCESS(5);

	int status;

	SecurityStatus(int status) {
		this.status = status;
	}

	public static SecurityStatus getSecurityStatus(int status) {
		SecurityStatus[] sss = SecurityStatus.values();
		for (SecurityStatus ss : sss) {
			if (ss.status == status) {
				return ss;
			}
		}
		return NONE;
	}

	@Override
	public String toString() {
		return String.valueOf(status);
	}
}
