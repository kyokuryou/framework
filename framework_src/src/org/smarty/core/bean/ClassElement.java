package org.smarty.core.bean;

import java.lang.annotation.Annotation;

/**
 * 运行堆栈信息
 */
public class ClassElement {
    /**
     * 类表示的字符串
     */
    private String className;
    /**
     * 方法表示的字符串
     */
    private String methodName;
    /**
     * 类注解
     */
    private Annotation[] classAnnotation;
    /**
     * 方法注解
     */
    private Annotation[] methodAnnotation;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Annotation[] getClassAnnotation() {
        return classAnnotation;
    }

    public void setClassAnnotation(Annotation[] classAnnotation) {
        this.classAnnotation = classAnnotation;
    }

    public Annotation[] getMethodAnnotation() {
        return methodAnnotation;
    }

    public void setMethodAnnotation(Annotation[] methodAnnotation) {
        this.methodAnnotation = methodAnnotation;
    }
}
