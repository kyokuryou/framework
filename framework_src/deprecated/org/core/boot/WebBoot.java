package org.smarty.core.boot;

import org.smarty.core.bean.SystemConfig;
import org.smarty.core.support.cache.CacheMessage;
import org.smarty.core.launcher.ClassLoaderWrapper;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.SystemConfigUtil;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * web程序启动器
 */
public abstract class WebBoot implements ServletContextAware {
    private boolean debugModel;
    private Integer systemCatchSize;
    private Integer temporaryCatchSize;

    private static RuntimeLogger logger = new RuntimeLogger(WebBoot.class);
    protected ServletContext servletContext;

    public final void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * 启动方法
     */
    public final void main() {
        // 初始化缓存容器
        initCache();
        // 加入缓存
        CacheMessage.putSystemCache("debugModel", debugModel);
        // 加入classloader
        ClassLoaderWrapper.setClassLoaders(getClassLoaders());
        // 执行程序初始化
        try {
            init();
        } catch (Exception e) {
            logger.out("WebBoot 初始化失败");
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

    /**
     * 加载配置文件
     *
     * @return 配置信息
     */
    protected SystemConfig initSystemConfig() {
        return SystemConfigUtil.getSystemConfig();
    }

    /**
     * 返回默认加载器,如需要重新设置,重写此方法
     *
     * @return 加载器
     */
    protected ClassLoader[] getClassLoaders() {
        return new ClassLoader[]{};
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

    public void setDebugModel(boolean debugModel) {
        this.debugModel = debugModel;
    }

    public void setSystemCatchSize(Integer systemCatchSize) {
        this.systemCatchSize = systemCatchSize;
    }

    public void setTemporaryCatchSize(Integer temporaryCatchSize) {
        this.temporaryCatchSize = temporaryCatchSize;
    }
}
