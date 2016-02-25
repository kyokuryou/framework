package org.smarty.security.config.statement;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import org.smarty.web.config.statement.FilterStatement;

/**
 * Created by Administrator on 2016/2/23.
 */
public class SecurityFilterStatement extends FilterStatement {
	public SecurityFilterStatement(Filter filter, String urlPattern) {
		super(filter, urlPattern);
	}

	public SecurityFilterStatement(String targetName, String urlPattern) {
		super(targetName, urlPattern);
	}

	public SecurityFilterStatement(String name, Filter filter, String urlPattern) {
		super(name, filter, urlPattern);
	}

	public EnumSet<DispatcherType> getDispatcherTypes() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC);
	}
}