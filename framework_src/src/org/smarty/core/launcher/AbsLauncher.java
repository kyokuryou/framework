package org.smarty.core.launcher;

import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.support.cache.CacheMessage;
import org.smarty.core.utils.SpringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 启动器
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class AbsLauncher implements ApplicationContextAware, InitializingBean {
    private static RuntimeLogger logger = new RuntimeLogger(AbsLauncher.class);
    private Integer systemCatchSize;
    private Integer temporaryCatchSize;

    public void afterPropertiesSet() throws Exception {

    }

    public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.setApplicationContext(applicationContext);
    }

    /**
     * 启动方法
     */
    public final void main() {
        // 初始化缓存容器
        initCache();
        // 加入classloader
        LauncherWrapper lw = new LauncherWrapper();
        lw.createClassLoader(getLauncher());
        // 执行程序初始化
        try {
            init();
        } catch (Exception e) {
            logger.out("AbsLauncher 初始化失败");
            exit();
        }
    }


    /**
     * 初始化缓存容器
     */
    public final void initCache() {
        Map<String, Integer> caches = new HashMap<String, Integer>();
        caches.put("system", systemCatchSize);
        caches.put("temporary", temporaryCatchSize);
        CacheMessage cm = new CacheMessage("q1w2e3r4t5");
        cm.initCacheMap(caches);
    }

    protected Set<ClassLoader> getLauncher() {
        return new HashSet<ClassLoader>(0);
    }

    /**
     * 初始化方法
     */
    public void init() throws Exception {

    }

    /**
     * 销毁;如需要重新设置,重写此方法;
     */
    public void exit() {
        System.gc();
    }

    public void setSystemCatchSize(Integer systemCatchSize) {
        this.systemCatchSize = systemCatchSize;
    }

    public void setTemporaryCatchSize(Integer temporaryCatchSize) {
        this.temporaryCatchSize = temporaryCatchSize;
    }
}
