package org.smarty.web.commons;

/**
 * AjaxType
 */
public enum AjaxType {
	APPLICATION_JSON("application/json"),
	TEXT_PLAIN("text/plain"),
	TEXT_HTML("text/html"),
	TEXT_XML("text/xml");
	String type;

	AjaxType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
