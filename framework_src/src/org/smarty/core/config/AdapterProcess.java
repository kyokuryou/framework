package org.smarty.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * AdapterProcess
 */
public class AdapterProcess implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {
	private final Log logger = LogFactory.getLog(getClass());
	private final AutowireCapableBeanFactory autowireBeanFactory;
	private final List<Object> disposableBeans = new ArrayList<Object>();
	private final Map<Class<?>, Object> shareObject = new HashMap<Class<?>, Object>();

	AdapterProcess(AutowireCapableBeanFactory autowireBeanFactory) {
		this.autowireBeanFactory = autowireBeanFactory;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		shareObject.clear();
	}

	@SuppressWarnings("unchecked")
	public <T> T postProcess(T object) {
		if (object == null) {
			return null;
		}
		T result = null;
		try {
			result = (T) autowireBeanFactory.initializeBean(object, object.toString());
		} catch (RuntimeException e) {
			Class<?> type = object.getClass();
			throw new RuntimeException("Could not postProcess " + object + " of type " + type, e);
		}
		if (result instanceof DisposableBean) {
			disposableBeans.add(result);
		}
		return result;
	}

	public Object postFactoryBean(Object object) {
		Object bean = postProcess(object);
		if (bean instanceof FactoryBean) {
			try {
				return ((FactoryBean) bean).getObject();
			} catch (Exception e) {
				Class<?> type = object.getClass();
				throw new RuntimeException("Could not postFactoryBean " + object + " of type " + type, e);
			}
		}
		return bean;
	}

	public <T> void setShareObject(Class<T> klass, T object) {
		shareObject.put(klass, object);
	}

	@SuppressWarnings("unchecked")
	public <T> T getShareObject(Class<T> klass) {
		return (T) shareObject.get(klass);
	}


	@Override
	public void destroy() throws Exception {
		for (Object disposable : disposableBeans) {
			autowireBeanFactory.destroyBean(disposable);
		}
	}
}
