package org.test.commons;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;
import org.test.service.ITestService;

/**
 * @author qul
 * @since LVGG1.1
 */
public class TestBeanFactory implements BeanFactoryAware {


	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		ITestService ts = beanFactory.getBean(ITestService.class);
		System.out.println(ts);
	}
}
