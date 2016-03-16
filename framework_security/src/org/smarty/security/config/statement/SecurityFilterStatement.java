package org.smarty.security.config.statement;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import org.smarty.web.config.statement.FilterStatement;

/**
 * SecurityFilterStatement
 */
public class SecurityFilterStatement extends FilterStatement {

	public SecurityFilterStatement(Filter filter) {
		super(filter);
	}

	public SecurityFilterStatement(String targetName) {
		super(targetName);
	}

	public SecurityFilterStatement(String targetName, Filter filter) {
		super(targetName, filter);
	}

	public EnumSet<DispatcherType> getDispatcherTypes() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC);
	}
}