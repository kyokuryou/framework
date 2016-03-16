package org.smarty.core.config;

import java.util.List;
import org.smarty.core.bean.JobProperty;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * SystemConfigurerAdapter
 */
public abstract class SystemConfigAdapter {

	protected void configure(DataSourceTransactionManager transactionManager) {

	}

	protected AdaptableJobFactory adaptableJobFactory() {
		return null;
	}

	protected ConverterRegistry converterRegistry() {
		return null;
	}

	protected AbstractCacheResolver cacheResolver() {
		return null;
	}

	protected KeyGenerator keyGenerator() {
		return null;
	}

	protected CacheErrorHandler cacheErrorHandler() {
		return null;
	}

	protected void addCaches(List<ConcurrentMapCache> cacheList) {

	}

	protected void addJobs(List<JobProperty> jobList) {

	}

	protected AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
		return null;
	}
}
