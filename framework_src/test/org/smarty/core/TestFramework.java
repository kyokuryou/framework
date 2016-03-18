package org.smarty.core;

import org.junit.Test;
import org.smarty.core.config.SystemConfig;
import org.smarty.core.support.schedule.SchedulerProxy;
import org.smarty.core.test.AbsTestCase;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * TestFramework
 */
public class TestFramework extends AbsTestCase {
	@Test
	public void testF() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.register(SystemConfig.class);
		applicationContext.refresh();
		SchedulerProxy sp = applicationContext.getBean("schedulerProxy", SchedulerProxy.class);
		System.out.println(sp);
	}
}
