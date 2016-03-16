package org.smarty.core.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.config.build.SystemBuilder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.util.Assert;

/**
 * AbsContextInitializer replace of AbsLauncher
 */
public abstract class SystemInitializer<T extends ApplicationContext, E extends SystemBuilder> {
	protected final Log logger = LogFactory.getLog(getClass());
	protected final T applicationContext = createApplicationContext();
	protected final E contextBuilder = createContextBuilder();

	public SystemInitializer() {
		try {
			Assert.notNull(applicationContext, "[Assertion failed] - createApplicationContext() is required; it must not be null");
			onInitialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onInitialize() throws Exception {
	}

	protected abstract T createApplicationContext();

	protected abstract E createContextBuilder();

	protected ApplicationContextInitializer<?>[] getApplicationContextInitializers() {
		return null;
	}

	protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

	}
}
