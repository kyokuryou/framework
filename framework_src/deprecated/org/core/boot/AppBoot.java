package org.core.boot;

import org.core.bean.SystemConfig;
import org.core.support.ClassLoaderWrapper;
import org.core.utils.SystemConfigUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public abstract class AppBoot {
    // 默认的spring文件名
    private static final String springFile = "spring.xml";

    public static void main(String[] args) {
        // 启动spring
        ApplicationContext cpxac = initSpring();
        // 加载配置文件并缓存
        initSystemConfig();

        // 获得当前程序实例
        AppBoot ab = (AppBoot) cpxac.getBean(args[0]);
        // 加入classloader
        ClassLoaderWrapper.setClassLoaders(ab.getClassLoaders());
        // 执行应用程序初始化
        ab.init(args);
        // 执行应用程序
        ab.run();
        // 执行应用程序销毁
        ab.destroy();
    }

    /**
     * 加载spring配置文件
     *
     * @return ApplicationContext
     */
    protected static ApplicationContext initSpring() {
        return new ClassPathXmlApplicationContext(springFile);
    }

    /**
     * 加载配置文件
     *
     * @return 配置信息
     */
    protected static SystemConfig initSystemConfig() {
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
     * 初始化方法,总在run方法之前执行.
     *
     * @param args 参数
     */
    public void init(String[] args) {

    }

    /**
     * 执行
     */
    public abstract void run();

    /**
     * 销毁;在run方法之后执行;如需要重新设置,重写此方法;
     * <p/>
     * 注:当执行此方法的尾部时,也意味着.该应用程序结束.
     * 如要维持当前程序,请在run方法里做,而不是在此方法内做文章.
     */
    public void destroy() {
        System.gc();
    }
}
