package org.smarty.core.config;

import java.util.LinkedHashSet;
import java.util.Set;
import org.smarty.core.config.statement.ConfigStatement;
import org.springframework.util.Assert;

/**
 * App Context Builder
 */
public class ConfigBuilder {

	private final Set<ConfigStatement> registrations = new LinkedHashSet<ConfigStatement>();

	protected ConfigBuilder() {
	}

	public final void addStatement(ConfigStatement statement) {
		Assert.notNull(statement, "[Assertion failed] - context statement is required; it must not be null");
		this.registrations.add(statement);
	}

	public final void apply() {
		for (ConfigStatement reg : registrations) {
			build(reg);
		}
	}

	protected void build(ConfigStatement contextStatement) {

	}
}
