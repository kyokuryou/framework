package org.smarty.core.config.statement;

import org.smarty.core.utils.ObjectUtil;
import org.springframework.core.Conventions;
import org.springframework.util.Assert;

public abstract class ConfigStatement<T> {
	private String targetName;
	private T target;

	public ConfigStatement(T object) {
		this(null, object);
	}

	public ConfigStatement(String targetName) {
		this(targetName, null);
	}

	public ConfigStatement(String targetName, T target) {
		if (ObjectUtil.isEmpty(targetName) && ObjectUtil.isEmpty(target)) {
			Assert.notNull(null, "[Assertion failed] - targetName or target is required; it must not be null");
		}
		if (ObjectUtil.isEmpty(targetName)) {
			this.targetName = Conventions.getVariableName(target);
		} else {
			this.targetName = targetName;
		}
		if (ObjectUtil.isEmpty(target)) {
			this.target = getTarget(targetName);
		} else {
			this.target = target;
		}
		afterPropertiesSet();
	}

	protected void afterPropertiesSet() {

	}

	public T getTarget(String targetFilter) {
		return null;
	}

	public final String getTargetName() {
		return targetName;
	}

	public final T getTarget() {
		return target;
	}
}