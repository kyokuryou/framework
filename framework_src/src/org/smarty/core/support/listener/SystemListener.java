package org.smarty.core.support.listener;

import org.smarty.core.utils.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * SystemListener
 */
public abstract class SystemListener implements ApplicationListener<ApplicationEvent> {
	@Override
	public final void onApplicationEvent(ApplicationEvent event) {
		onEvent(event);
	}

	public void onEvent(ApplicationEvent event) {

	}
}
