package org.core.utils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import org.core.logger.RuntimeLogger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Spring工具
 */
public class SpringUtil implements ApplicationContextAware, ServletContextAware {
    private static RuntimeLogger logger = new RuntimeLogger(SpringUtil.class);

    private static ApplicationContext applicationContext;
    private static ServletContext servletContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext ApplicationContext子类
     * @throws org.springframework.beans.BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext != null) {
            throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
        }
        SpringUtil.applicationContext = applicationContext;
        logger.info("holded applicationContext,displayName:" + applicationContext.getDisplayName());
    }

    public void setServletContext(ServletContext servletContext) {
        if (SpringUtil.servletContext != null) {
            throw new IllegalStateException("ServletContextHolder already holded 'servletContext'.");
        }
        SpringUtil.servletContext = servletContext;
        logger.info("holded servletContext,displayName:" + servletContext.getServletContextName());
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("'applicationContext' property is null,ApplicationContextHolder not yet init.");
        }
        return applicationContext;
    }


    public static ServletContext getServletContext() {
        if (servletContext == null) {
            throw new IllegalStateException("'servletContext' property is null,ServletContextHolder not yet init.");
        }
        return servletContext;
    }

    /**
     * 获取对象
     *
     * @param name bean注册名
     * @return Object 一个以所给名字注册的bean的实例
     * @throws org.springframework.beans.BeansException
     */
    public static Object getBean(String name) {
        try {
            return getApplicationContext().getBean(name);
        } catch (BeansException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 获取类型为requiredType的对象
     * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
     *
     * @param name         bean注册名
     * @param requiredType 返回对象类型
     * @return Object 返回requiredType类型对象
     * @throws org.springframework.beans.BeansException
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        try {
            return getApplicationContext().getBean(name, requiredType);
        } catch (BeansException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name bean注册名
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，return false
     *
     * @param name bean注册名
     * @return boolean
     */
    public static boolean isSingleton(String name) {
        try {
            return getApplicationContext().isSingleton(name);
        } catch (NoSuchBeanDefinitionException e) {
            logger.out("给定名字相应的bean定义没有被找到", e);
        }
        return false;
    }

    /**
     * 获取注册bean的类型
     *
     * @param name bean注册名
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static Class getType(String name) {
        try {
            return getApplicationContext().getType(name);
        } catch (NoSuchBeanDefinitionException e) {
            logger.out("给定名字相应的bean定义没有被找到", e);
        }
        return null;
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name bean注册名
     * @return bean别名
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) {
        try {
            return getApplicationContext().getAliases(name);
        } catch (NoSuchBeanDefinitionException e) {
            logger.out("给定名字相应的bean定义没有被找到", e);
        }
        return null;
    }

    public static Map<String, Object> getCommonData(Map<String,Object> data) {
        Map<String, Object> commonData = new HashMap<String, Object>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
        ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
        commonData.put("message", resourceBundleModel);
        commonData.put("base", servletContext.getContextPath());
        commonData.put("systemConfig", SystemConfigUtil.getSystemConfig());
        if(LogicUtil.isNotEmptyMap(data)){
            commonData.putAll(data);
        }
        return commonData;
    }

    /**
     * 初始化spring容器
     *
     * @param file spring文件名
     */
    public static void initApplicationContext(String... file) {
        try {
            if (SpringUtil.applicationContext != null)
                throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
            SpringUtil.applicationContext = new ClassPathXmlApplicationContext(file);
        } catch (BeansException e) {
            logger.out(e);
        }
    }
}