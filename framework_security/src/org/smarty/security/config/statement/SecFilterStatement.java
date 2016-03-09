package org.smarty.security.config.statement;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import org.smarty.web.config.statement.FilterStatement;

/**
 * Created by Administrator on 2016/2/23.
 */
public class SecFilterStatement extends FilterStatement {

	public SecFilterStatement(Filter filter) {
		super(filter);
	}

	public SecFilterStatement(String targetName) {
		super(targetName);
	}

	public SecFilterStatement(String targetName, Filter filter) {
		super(targetName, filter);
	}

	public EnumSet<DispatcherType> getDispatcherTypes() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC);
	}
}