package org.smarty.core.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.sql.DataSource;
import org.quartz.Scheduler;
import org.smarty.core.bean.JobProperty;
import org.smarty.core.common.BaseConstant;
import org.smarty.core.config.condition.JdbcDataSource;
import org.smarty.core.config.condition.JndiDataSource;
import org.smarty.core.support.jdbc.SQLSession;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.support.jdbc.support.DataSourceType;
import org.smarty.core.support.net.SocketServer;
import org.smarty.core.support.schedule.SchedulerProxy;
import org.smarty.core.utils.SpringUtil;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * SystemConfigurer
 */
@Configurable(value = "system")
@EnableAsync(mode = AdviceMode.PROXY)
@EnableCaching(proxyTargetClass = true)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource(value = "classpath:conf/system.properties", ignoreResourceNotFound = false)
@ComponentScan(useDefaultFilters = false, basePackages = "org.smarty.core", includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class)
})
@Order(0)
public class SystemConfig implements TransactionManagementConfigurer, AsyncConfigurer, CachingConfigurer, ApplicationContextAware {
	public static final String SCHEDULER_NAME = "scheduler";
	public static final String SCHEDULER_PROXY_NAME = "schedulerProxy";
	public static final String DATA_SOURCE_NAME = "dataSource";
	public static final String SQL_SESSION_NAME = "sqlSession";
	public static final String MAIL_SENDER_NAME = "mailSender";
	public static final String SOCKET_SERVER_NAME = "socketServer";
	public static final String CONVERTER_REGISTRY_NAME = "converterRegistry";
	public static final String ASYNC_EXECUTOR_NAME = "asyncExecutor";
	public static final String CACHE_MANAGER_NAME = "cacheManager";
	private SystemConfigAdapter configAdapter = new DefaultConfigAdapter();
	private AutowireCapableBeanFactory autowireBeanFactory;
	@Value("${default.db}")
	private DBType dbType;
	@Value("${default.dataSource}")
	private DataSourceType dataSourceType;
	@Value("${pool.jdbc.maxPoolSize}")
	private int jdbcMaxPoolSize;
	@Value("${pool.jdbc.minPoolSize}")
	private int jdbcMinPoolSize;
	@Value("${pool.jdbc.initialPoolSize}")
	private int jdbcInitialPoolSize;
	@Value("${pool.jdbc.acquireIncrement}")
	private int jdbcAcquireIncrement;
	@Value("${pool.jdbc.maxStatements}")
	private int jdbcMaxStatements;
	@Value("${pool.jdbc.maxIdleTime}")
	private int jdbcMaxIdleTime;
	@Value("${pool.jdbc.idleConnectionTestPeriod}")
	private int jdbcIdleConnectionTestPeriod;
	@Value("${pool.thread.corePoolSize}")
	private int threadCorePoolSize;
	@Value("${pool.thread.maxPoolSize}")
	private int threadMaxPoolSize;
	@Value("${pool.thread.queueCapacity}")
	private int threadQueueCapacity;
	@Value("${pool.thread.keepAliveSeconds}")
	private int threadKeepAliveSeconds;
	@Value("${pool.thread.threadNamePrefix}")
	private String threadNamePrefix;
	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUser;
	@Value("${jdbc.password}")
	private String jdbcPwd;
	@Value("${jndi.name}")
	private String jndiName;

	@Autowired(required = false)
	public void setConfigAdapter(SystemConfigAdapter configAdapter) {
		this.configAdapter = configAdapter;
	}

	@Bean(name = SQL_SESSION_NAME)
	public SQLSession getSQLSession(DataSource dataSource) {
		SQLSession ss = new SQLSession();
		ss.setSqlType(dbType);
		ss.setDataSource(dataSource);
		return ss;
	}

	@Bean(name = DATA_SOURCE_NAME, destroyMethod = "close")
	@Conditional(JdbcDataSource.class)
	public PooledDataSource getPoolDateSource() throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setMaxPoolSize(jdbcMaxPoolSize);
		ds.setMinPoolSize(jdbcMinPoolSize);
		ds.setInitialPoolSize(jdbcInitialPoolSize);
		ds.setAcquireIncrement(jdbcAcquireIncrement);
		ds.setMaxStatements(jdbcMaxStatements);
		ds.setMaxIdleTime(jdbcMaxIdleTime);
		ds.setIdleConnectionTestPeriod(jdbcIdleConnectionTestPeriod);
		ds.setDriverClass(jdbcDriver);
		ds.setJdbcUrl(jdbcUrl);
		ds.setUser(jdbcUser);
		ds.setPassword(jdbcPwd);
		return ds;
	}

	@Bean(name = DATA_SOURCE_NAME)
	@Conditional(JndiDataSource.class)
	public DataSource getJndiDataSource() {
		JndiObjectFactoryBean jofb = new JndiObjectFactoryBean();
		jofb.setJndiName(jndiName);
		return (DataSource) jofb.getObject();
	}

	@Bean(name = CONVERTER_REGISTRY_NAME)
	public ConverterRegistry getConverterRegistry() {
		return configAdapter.converterRegistry();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = SCHEDULER_PROXY_NAME)
	@Lazy
	public SchedulerProxy getSchedulerProxy(Scheduler scheduler) {
		SchedulerProxy sp = new SchedulerProxy(scheduler);
		List<JobProperty> jobs = new ArrayList<JobProperty>();
		configAdapter.addJobs(jobs);
		for (JobProperty job : jobs) {
			sp.addJobProperty(job);
		}
		return sp;
	}

	@Bean(name = SCHEDULER_NAME)
	@Lazy
	public SchedulerFactoryBean getScheduler(AsyncTaskExecutor taskExecutor) {
		SchedulerFactoryBean sfb = new SchedulerFactoryBean();
		sfb.setAutoStartup(true);
		sfb.setTaskExecutor(taskExecutor);
		sfb.setWaitForJobsToCompleteOnShutdown(true);
		sfb.setJobFactory(configAdapter.adaptableJobFactory());
		return sfb;
	}

	@Bean(name = MAIL_SENDER_NAME)
	@Lazy
	public JavaMailSender getMailSender() {
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.timeout", "25000");

		JavaMailSenderImpl msi = new JavaMailSenderImpl();
		msi.setJavaMailProperties(prop);
		msi.setDefaultEncoding(BaseConstant.STR_CHARSET);
		return msi;
	}

	@Bean(name = SOCKET_SERVER_NAME)
	@Lazy
	public SocketServer getSocketServer(AsyncTaskExecutor taskExecutor) {
		SocketServer ss = new SocketServer();
		ss.setTaskExecutor(taskExecutor);
		return ss;
	}

	@Bean(name = ASYNC_EXECUTOR_NAME)
	@Lazy
	public AsyncTaskExecutor getExecutor() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(threadCorePoolSize);
		tpte.setMaxPoolSize(threadMaxPoolSize);
		tpte.setQueueCapacity(threadQueueCapacity);
		tpte.setKeepAliveSeconds(threadKeepAliveSeconds);
		tpte.setThreadNamePrefix("executor-");
		return tpte;
	}

	@Bean(name = CACHE_MANAGER_NAME)
	@Lazy
	public CacheManager getCacheManager() {
		SimpleCacheManager cm = new SimpleCacheManager();
		List<ConcurrentMapCache> caches = new ArrayList<ConcurrentMapCache>();
		caches.add(new ConcurrentMapCache("default"));
		configAdapter.addCaches(caches);
		cm.setCaches(caches);
		return cm;
	}

	// implements
	@Override
	public CacheResolver cacheResolver() {
		CacheManager cm = autowireBeanFactory.getBean(CACHE_MANAGER_NAME, CacheManager.class);
		AbstractCacheResolver cr = configAdapter.cacheResolver();
		cr.setCacheManager(cm);
		return cr;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return configAdapter.keyGenerator();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return configAdapter.cacheErrorHandler();
	}

	@Override
	public CacheManager cacheManager() {
		return autowireBeanFactory.getBean(CACHE_MANAGER_NAME, CacheManager.class);
	}

	@Override
	public Executor getAsyncExecutor() {
		return autowireBeanFactory.getBean(ASYNC_EXECUTOR_NAME, Executor.class);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return configAdapter.asyncUncaughtExceptionHandler();
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSource ds = autowireBeanFactory.getBean(DATA_SOURCE_NAME, DataSource.class);
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(ds);
		configAdapter.configure(tm);
		return tm;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.setApplicationContext(applicationContext);
		this.autowireBeanFactory = applicationContext.getAutowireCapableBeanFactory();
	}

	public class DefaultConfigAdapter extends SystemConfigAdapter {
	}
}
