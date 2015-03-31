package org.core.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class加载器
 */
public class ClassLoaderWrapper {
    private static ClassLoader[] classLoaders;


    /**
     * 返回加载器
     *
     * @param classLoader 一个加载器
     * @return 加载器
     */
    public static ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        List<ClassLoader> loaders = new ArrayList<ClassLoader>();
        // 系统级别
        loaders.add(ClassLoader.getSystemClassLoader());
        loaders.add(Thread.currentThread().getContextClassLoader());
        loaders.add(ClassLoaderWrapper.class.getClassLoader());
        // 自定义级别
        if (classLoaders != null) {
            loaders.addAll(Arrays.asList(classLoaders));
        }
        // 自定义级别
        if (classLoader != null) {
            loaders.add(classLoader);
        }
        return loaders.toArray(new ClassLoader[loaders.size()]);
    }

    /**
     * 设置classLoader
     * 该ClassLoader必须受架构与项目支持
     *
     * @param classLoaders ClassLoader对象数组
     */
    public static void setClassLoaders(ClassLoader... classLoaders) {
        ClassLoaderWrapper.classLoaders = classLoaders;
    }
}
