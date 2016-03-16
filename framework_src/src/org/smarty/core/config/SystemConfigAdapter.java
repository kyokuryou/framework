package org.smarty.core.config;

import java.util.List;
import org.quartz.spi.TriggerFiredBundle;
import org.smarty.core.bean.JobProperty;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * SystemConfigurerAdapter
 */
public abstract class SystemConfigAdapter implements ApplicationContextAware {
	private AutowireCapableBeanFactory autowireBeanFactory;

	protected void configure(DataSourceTransactionManager transactionManager) {

	}

	protected AdaptableJobFactory adaptableJobFactory() {
		return new AutowireJobFactory();
	}

	protected ConverterRegistry converterRegistry() {
		return new DefaultFormattingConversionService();
	}

	protected AbstractCacheResolver cacheResolver() {
		return new SimpleCacheResolver();
	}

	protected KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	protected CacheErrorHandler cacheErrorHandler() {
		return new SimpleCacheErrorHandler();
	}

	protected void addCaches(List<ConcurrentMapCache> cacheList) {

	}

	protected void addJobs(List<JobProperty> jobList) {

	}

	protected AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	protected final <T> T initializeBean(T t) {
		return (T) autowireBeanFactory.initializeBean(t, t.getClass().getName());
	}

	@Override
	public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.autowireBeanFactory = applicationContext.getAutowireCapableBeanFactory();
	}

	protected final class AutowireJobFactory extends AdaptableJobFactory {
		@Override
		protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
			Object job = super.createJobInstance(bundle);
			autowireBeanFactory.autowireBean(job);
			return job;
		}
	}
}
