package org.core.common;

import org.core.bean.ClassElement;

/**
 * 运行堆栈
 */
public class ClassStackTrace {
    /**
     * 获得当前运行的类和方法
     *
     * @return classInfo
     */
    public static ClassElement getInstance(Integer level) {
        if (level < 0) {
            return null;
        }
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();

        if (stes == null || level > stes.length) {
            return null;
        }

        StackTraceElement ste = stes[level];
        String methodName = ste.getMethodName();
        return getRuntimeClassInfo(ste.getClassName(), methodName);
    }

    /**
     * 创建ClassInfo
     *
     * @param klass      class
     * @param methodName 方法名
     * @return classInfo
     */
    private static ClassElement getRuntimeClassInfo(String klass, String methodName) {
        ClassElement rci = new ClassElement();
        rci.setClassName(klass);
        rci.setMethodName(methodName);
        return rci;
    }
}
