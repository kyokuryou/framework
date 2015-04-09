package org.smarty.core.launcher;

import org.smarty.core.utils.LogicUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Class加载器
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class LauncherWrapper {
    private static Set<ClassLoader> classLoaders = new HashSet<ClassLoader>();

    /**
     * 设置classLoader
     * 该ClassLoader必须受架构与项目支持
     *
     * @param loaders ClassLoader
     */
    public void createClassLoader(Set<ClassLoader> loaders) {
        classLoaders.clear();
        // 系统级别
        classLoaders.add(ClassLoader.getSystemClassLoader());
        classLoaders.add(Thread.currentThread().getContextClassLoader());
        classLoaders.add(LauncherWrapper.class.getClassLoader());
        // 自定义级别
        if (LogicUtil.isNotEmptyCollection(loaders)) {
            classLoaders.addAll(loaders);
        }
    }

    /**
     * 返回加载器
     *
     * @param classLoader 一个加载器
     * @return 加载器
     */
    public static ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        Set<ClassLoader> cls = new HashSet<ClassLoader>(classLoaders);
        // 自定义级别
        if (classLoader != null) {
            cls.add(classLoader);
        }
        return cls.toArray(new ClassLoader[cls.size()]);
    }
}
