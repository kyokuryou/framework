package org.smarty.web.config.statement;

import javax.servlet.Servlet;

/**
 * FilterStatement
 */
public class ServletStatement extends WebStatement<Servlet> {
	private String[] mapping;
	private int onStartup = 1;

	public ServletStatement(String targetName, Servlet servlet) {
		super(targetName, servlet);
	}

	public void setOnStartup(int onStartup) {
		this.onStartup = onStartup;
	}

	public void setMapping(String... mapping) {
		this.mapping = mapping;
	}

	public String[] getMapping() {
		return mapping;
	}

	public int getOnStartup() {
		return onStartup;
	}
}