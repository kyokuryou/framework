package org.smarty.web.config.statement;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.smarty.core.config.statement.ConfigStatement;

/**
 * FilterStatement
 */
public abstract class WebStatement<T> extends ConfigStatement<T> {

	public WebStatement(T object) {
		super(object);
	}

	public WebStatement(String targetName) {
		super(targetName);
	}

	public WebStatement(String targetName, T object) {
		super(targetName, object);
	}

	public boolean isAsyncSupported() {
		return true;
	}

	public EnumSet<DispatcherType> getDispatcherTypes() {
		return (isAsyncSupported() ?
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC) :
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE));
	}
}
