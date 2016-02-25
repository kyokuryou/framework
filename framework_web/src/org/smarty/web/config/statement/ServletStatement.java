package org.smarty.web.config.statement;

import javax.servlet.Servlet;

/**
 * FilterStatement
 */
public class ServletStatement extends WebStatement<Servlet> {
	private String[] mapping;
	private int onStartup = 0;

	public ServletStatement(String targetName, Servlet servlet, String[] mapping) {
		super(targetName, servlet);
		this.mapping = mapping;
	}

	public void setOnStartup(int onStartup) {
		this.onStartup = onStartup;
	}

	public String[] getMapping() {
		return mapping;
	}

	public int getOnStartup() {
		return onStartup;
	}
}