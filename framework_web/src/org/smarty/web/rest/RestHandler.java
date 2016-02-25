package org.smarty.web.rest;

/**
 * RestHandler
 */
public abstract class RestHandler {

	public void beforeSend() {

	}

	public void complete(byte[] data) {

	}

	public void bad(int statusCode) {

	}
}
