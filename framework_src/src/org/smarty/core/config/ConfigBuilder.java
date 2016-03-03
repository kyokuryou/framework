package org.smarty.core.config;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.config.statement.ConfigStatement;
import org.springframework.util.Assert;

/**
 * App Context Builder
 */
public class ConfigBuilder {
	protected final Log logger = LogFactory.getLog(getClass());

	private final Set<ConfigStatement<?>> registrations = new LinkedHashSet<ConfigStatement<?>>();

	protected ConfigBuilder() {
	}

	public final void addStatement(ConfigStatement<?> statement) {
		Assert.notNull(statement, "[Assertion failed] - context statement is required; it must not be null");
		this.registrations.add(statement);
	}

	public final void apply() {
		for (ConfigStatement<?> reg : registrations) {
			build(reg);
		}
	}

	protected void build(ConfigStatement<?> contextStatement) {

	}
}
