package org.test;

import org.junit.Test;
import org.smarty.core.test.AbsTestCase;
import org.smarty.core.utils.SpringUtil;
import org.test.commons.TestBeanFactory;

/**
 * Created by Administrator on 2016/1/29.
 */
public class TestMain extends AbsTestCase {

	@Test
	public void testRun() {
		setUpSpring("classpath:spring.xml");
		TestBeanFactory tbf = SpringUtil.getBean("testBeanFactory", TestBeanFactory.class);

	}
}
