package org.smarty.core.launcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.support.cache.CacheMessage;
import org.smarty.core.utils.SpringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 启动器
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class AbsLauncher implements ApplicationContextAware, InitializingBean, DisposableBean {
	private static Log logger = LogFactory.getLog(AbsLauncher.class);
	@Value("${cache.system.size}")
	private int systemCatchSize;
	@Value("${cache.temporary.size}")
	private int temporaryCatchSize;

	@Override
	public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.setApplicationContext(applicationContext);
	}

	@Override
	public final void afterPropertiesSet() throws Exception {
		// 初始化缓存容器
		initCache();
		// 加入classloader
		LauncherWrapper lw = new LauncherWrapper();
		lw.createClassLoader(getLauncher());
		// 执行程序初始化
		try {
			init();
		} catch (Exception e) {
			logger.warn("AbsLauncher 初始化失败");
		}
	}

	/**
	 * 初始化方法
	 */
	public void init() throws Exception {

	}

	@Override
	public void destroy() throws Exception {
		System.gc();
	}

	protected Set<ClassLoader> getLauncher() {
		return new HashSet<ClassLoader>(0);
	}

	/**
	 * 初始化缓存容器
	 */
	private void initCache() {
		Map<String, Integer> caches = new HashMap<String, Integer>();
		caches.put("system", systemCatchSize);
		caches.put("temporary", temporaryCatchSize);
		CacheMessage cm = new CacheMessage("q1w2e3r4t5");
		cm.initCacheMap(caches);
	}
}
