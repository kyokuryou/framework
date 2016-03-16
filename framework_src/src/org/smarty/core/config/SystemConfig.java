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
import org.quartz.spi.TriggerFiredBundle;
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
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
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
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
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
public class SystemConfig implements TransactionManagementConfigurer, AsyncConfigurer, CachingConfigurer, ApplicationContextAware, InitializingBean {
	public static final String SCHEDULER_PROXY_NAME = "schedulerProxy";
	public static final String DATA_SOURCE_NAME = "dataSource";
	public static final String SQL_SESSION_NAME = "sqlSession";
	public static final String MAIL_SENDER_NAME = "mailSender";
	public static final String SOCKET_SERVER_NAME = "socketServer";
	public static final String CONVERTER_REGISTRY_NAME = "converterRegistry";
	public static final String ASYNC_EXECUTOR_NAME = "asyncExecutor";
	private SystemConfigAdapter configAdapter = new DefaultConfigAdapter();
	@Autowired
	private AdapterProcess adapterProcess;
	@Value("${debug:false}")
	private boolean debug;
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
	@Value("${ehCache.file:classpath:ehcache.xml}")
	private String ehCacheFile;

	@Autowired(required = false)
	public void setConfigAdapter(SystemConfigAdapter configAdapter) {
		this.configAdapter = configAdapter;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public AdapterProcess adapterProcess(AutowireCapableBeanFactory autowireBeanFactory) {
		return new AdapterProcess(autowireBeanFactory);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
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
	public JndiObjectFactoryBean getJndiDataSource() {
		JndiObjectFactoryBean jofb = new JndiObjectFactoryBean();
		jofb.setJndiName(jndiName);
		return jofb;
	}

	@Bean(name = CONVERTER_REGISTRY_NAME)
	public ConverterRegistry getConverterRegistry() {
		ConverterRegistry cr = configAdapter.converterRegistry();
		if (cr == null) {
			cr = new DefaultFormattingConversionService();
		}
		return cr;
	}

	@Bean(name = SCHEDULER_PROXY_NAME)
	@Lazy
	public SchedulerProxy getSchedulerProxy() {
		SchedulerProxy sp = new SchedulerProxy(scheduler());
		List<JobProperty> jobs = new ArrayList<JobProperty>();
		configAdapter.addJobs(jobs);
		for (JobProperty job : jobs) {
			sp.addJobProperty(job);
		}
		return sp;
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
	public SocketServer getSocketServer() {
		SocketServer ss = new SocketServer();
		ss.setTaskExecutor(asyncTaskExecutor());
		return ss;
	}

	@Bean(name = ASYNC_EXECUTOR_NAME)
	@Lazy
	public AsyncTaskExecutor getExecutor() {
		return asyncTaskExecutor();
	}

	// implements

	@Override
	public Executor getAsyncExecutor() {
		return asyncTaskExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		AsyncUncaughtExceptionHandler aueh = configAdapter.asyncUncaughtExceptionHandler();
		if (aueh == null) {
			aueh = new SimpleAsyncUncaughtExceptionHandler();
		}
		return adapterProcess.postProcess(aueh);
	}

	@Override
	public CacheManager cacheManager() {
		CacheManager cacheManager = adapterProcess.getShareObject(CacheManager.class);
		if (cacheManager == null) {
			EhCacheCacheManager eccm = new EhCacheCacheManager();
			eccm.setCacheManager(ehCacheManager());
			cacheManager = (CacheManager) adapterProcess.postFactoryBean(eccm);
			adapterProcess.setShareObject(CacheManager.class, cacheManager);
		}
		return cacheManager;
	}

	@Override
	public CacheResolver cacheResolver() {
		AbstractCacheResolver cr = configAdapter.cacheResolver();
		if (cr == null) {
			cr = new SimpleCacheResolver();
		}
		cr.setCacheManager(cacheManager());
		return adapterProcess.postProcess(cr);
	}

	@Override
	public KeyGenerator keyGenerator() {
		KeyGenerator kg = configAdapter.keyGenerator();
		if (kg == null) {
			kg = new SimpleKeyGenerator();
		}
		return adapterProcess.postProcess(kg);
	}

	@Override
	public CacheErrorHandler errorHandler() {
		CacheErrorHandler ceh = configAdapter.cacheErrorHandler();
		if (ceh == null) {
			ceh = new SimpleCacheErrorHandler();
		}
		return adapterProcess.postProcess(ceh);
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSource ds = SpringUtil.getBean(DataSource.class);
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(ds);
		configAdapter.configure(tm);
		return tm;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.setApplicationContext(applicationContext);
	}

	private AdaptableJobFactory adaptableJobFactory() {
		AdaptableJobFactory ajf = configAdapter.adaptableJobFactory();
		if (ajf == null) {
			ajf = new AutowireJobFactory();
		}
		return ajf;
	}

	private Scheduler scheduler() {
		Scheduler scheduler = adapterProcess.getShareObject(Scheduler.class);
		if (scheduler == null) {
			SchedulerFactoryBean sfb = new SchedulerFactoryBean();
			sfb.setAutoStartup(true);
			sfb.setTaskExecutor(asyncTaskExecutor());
			sfb.setWaitForJobsToCompleteOnShutdown(true);
			sfb.setJobFactory(adaptableJobFactory());
			scheduler = (Scheduler) adapterProcess.postFactoryBean(sfb);
			adapterProcess.setShareObject(Scheduler.class, scheduler);
		}
		return scheduler;
	}

	private AsyncTaskExecutor asyncTaskExecutor() {
		AsyncTaskExecutor taskExecutor = adapterProcess.getShareObject(AsyncTaskExecutor.class);
		if (taskExecutor == null) {
			ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
			tpte.setCorePoolSize(threadCorePoolSize);
			tpte.setMaxPoolSize(threadMaxPoolSize);
			tpte.setQueueCapacity(threadQueueCapacity);
			tpte.setKeepAliveSeconds(threadKeepAliveSeconds);
			tpte.setThreadNamePrefix("executor-");
			taskExecutor = adapterProcess.postProcess(tpte);
			adapterProcess.setShareObject(AsyncTaskExecutor.class, taskExecutor);
		}
		return taskExecutor;
	}

	private net.sf.ehcache.CacheManager ehCacheManager() {
		net.sf.ehcache.CacheManager cacheManager = adapterProcess.getShareObject(net.sf.ehcache.CacheManager.class);
		if (cacheManager == null) {
			EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
			DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
			cmfb.setConfigLocation(resourceLoader.getResource(ehCacheFile));
			cacheManager = (net.sf.ehcache.CacheManager) adapterProcess.postFactoryBean(cmfb);
			adapterProcess.setShareObject(net.sf.ehcache.CacheManager.class, cacheManager);
		}
		return cacheManager;
	}

	public class DefaultConfigAdapter extends SystemConfigAdapter {
	}

	protected final class AutowireJobFactory extends AdaptableJobFactory {
		@Override
		protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
			Object job = super.createJobInstance(bundle);
			return SpringUtil.initializeBean(job.toString(), job);
		}
	}
}
