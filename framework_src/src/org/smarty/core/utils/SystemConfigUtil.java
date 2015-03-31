package org.smarty.core.utils;

import org.smarty.core.bean.SystemConfig;
import org.smarty.core.support.cache.CacheMessage;
import org.smarty.core.exception.CacheNameNotExistException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.lang.reflect.Field;

/**
 * 系统配置(无Log日志)
 */
public class SystemConfigUtil {
    public static String CONFIG_FILE_NAME = "system-config.xml";// 系统配置文件名称
    public static final String SYSTEM_CONFIG_CACHE_KEY = "systemConfig";// systemConfig缓存Key

    private SystemConfigUtil() {
    }

    /**
     * 获取系统配置信息
     *
     * @return SystemConfig对象
     */
    public static SystemConfig getSystemConfig() {
        if (LogicUtil.isEmpty(CONFIG_FILE_NAME)) {
            return null;
        }

        // 从System缓存中读取配置信息
        SystemConfig systemConfig = null;
        try {
            systemConfig = (SystemConfig) CacheMessage.getSystemCache(SYSTEM_CONFIG_CACHE_KEY);
            return new SystemConfig(systemConfig);
        } catch (CacheNameNotExistException e) {
            // 缓存中不存在时,创建对象
            systemConfig = new SystemConfig();
        }

        // 未缓存,重新从配置文件中读取;打开配置文件
        Document document = DocumentUtil.getDocument(PathUtil.getResourceAsFile(CONFIG_FILE_NAME));
        if (document == null) {
            return null;
        }

        // 通过反射机制映射xml->bean
        Field[] fields = BeanUtil.getFields(SystemConfig.class);
        for (Field field : fields) {
            String fn = field.getName();
            Node node = document.selectSingleNode("/system/systemConfig/" + fn);
            try {
                BeanUtil.invoke(systemConfig, BeanUtil.getSetterName(fn), BeanUtil.toCase(field, node.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 放入缓存
        CacheMessage.putSystemCache(SYSTEM_CONFIG_CACHE_KEY, systemConfig);
        return new SystemConfig(systemConfig);
    }

    /**
     * 更新系统配置信息
     *
     * @param systemConfig SystemConfig对象
     */
    public static void update(SystemConfig systemConfig) {
        if (LogicUtil.isEmpty(CONFIG_FILE_NAME)) {
            return;
        }
        if (systemConfig == null) {
            return;
        }
        // 打开配置文件
        Document document = DocumentUtil.getDocument(PathUtil.getResourceAsFile(CONFIG_FILE_NAME));
        if (document == null) {
            return;
        }
        // 获得配置文件中的根节点
        Element rootElement = document.getRootElement();
        Element systemConfigElement = rootElement.element("systemConfig");

        // 通过反射机制 bean->xml
        Field[] fields = BeanUtil.getFields(SystemConfig.class);
        for (Field field : fields) {
            String fn = field.getName();
            Node node = document.selectSingleNode("/system/systemConfig/" + fn);
            if (node == null) {
                node = systemConfigElement.addElement(fn);
            }
            try {
                Object text = BeanUtil.invoke(systemConfig, BeanUtil.getGetterName(fn));
                if ("smtpPort".equals(fn)) {
                    node.setText((text == null) ? "25" : text.toString());
                } else if ("cacheSize".equals(fn)) {
                    node.setText((text == null) ? "512" : text.toString());
                } else {
                    node.setText((text == null) ? "" : text.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 写入配置信息
        DocumentUtil.writerDocument(PathUtil.getResourceAsFile(CONFIG_FILE_NAME), document);

        // 刷新缓存
        CacheMessage.flushSystemCache(SYSTEM_CONFIG_CACHE_KEY, systemConfig);
    }
}