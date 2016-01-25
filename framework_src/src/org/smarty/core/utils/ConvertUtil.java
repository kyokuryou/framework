package org.smarty.core.utils;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 转换工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class ConvertUtil {
    private static Log logger = LogFactory.getLog(ConvertUtil.class);

    private ConvertUtil() {

    }


    /**
     * 批量转换String数组
     *
     * @param objects 数据
     * @return String数组
     */
    public static String[] batchConvert(Object... objects) {
        String[] cs = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].getClass().isAssignableFrom(Date.class)) {
                cs[i] = DateUtil.format((Date) objects[i]);
            } else {
                cs[i] = objects[i].toString();
            }
        }
        return cs;
    }

    /**
     * 基本类型Class转换引用类型Class字符串
     *
     * @param primitiveClass 基本类型Class
     * @return 引用类型Class字符串
     */
    @SuppressWarnings("unchecked")
    public static String getClassName(Class primitiveClass) {
        // 判断是否是数组,如果是递归调用
        if (primitiveClass.isArray()) {
            return getClassName(primitiveClass.getComponentType());
        }

        // 获得基本类型的引用类
        if (primitiveClass.isAssignableFrom(boolean.class)) {
            return Boolean.class.getName();
        } else if (primitiveClass.isAssignableFrom(byte.class)) {
            return Byte.class.getName();
        } else if (primitiveClass.isAssignableFrom(char.class)) {
            return Character.class.getName();
        } else if (primitiveClass.isAssignableFrom(double.class)) {
            return Double.class.getName();
        } else if (primitiveClass.isAssignableFrom(float.class)) {
            return Float.class.getName();
        } else if (primitiveClass.isAssignableFrom(int.class)) {
            return Integer.class.getName();
        } else if (primitiveClass.isAssignableFrom(long.class)) {
            return Long.class.getName();
        } else if (primitiveClass.isAssignableFrom(short.class)) {
            return Short.class.getName();
        }
        return primitiveClass.getName();
    }


    /**
     * 将value转换成字符串
     *
     * @param value 值
     * @return 转换的字符串
     */
    public static String toString(Object value) {
        if (value instanceof Date) {
            return DateUtil.format((Date) value);
        }
        return String.valueOf(value);
    }

    /**
     * 转换文件后缀名
     *
     * @param fileName 原文件名
     * @param suffix   新后缀
     * @return
     */
    public static String reFileSuffix(String fileName, String suffix) {
        if (LogicUtil.isEmpty(fileName)) {
            return null;
        }
        int i = fileName.lastIndexOf(".");
        StringBuilder sb = new StringBuilder(fileName);
        sb.delete(i + 1, fileName.length());
        sb.append(suffix);
        return sb.toString();
    }

    /**
     * 将value转换成type类型
     *
     * @param value 值
     * @param type  类型
     * @return type类型值
     */
    @SuppressWarnings("unchecked")
    public static Object valueToType(Object value, Class type) {
        if (type == null) {
            return value;
        }
        if (value.getClass().isAssignableFrom(type)) {
            return type.cast(value);
        }

        if (String.class.equals(type)) {
            return type.cast(value);
        } else if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
            if (value instanceof Boolean) {
                return value;
            } else if (value instanceof String) {
                return type.cast(Boolean.valueOf(value.toString()));
            } else if (value instanceof Integer) {
                return type.cast(Integer.valueOf(value.toString()) != 0);
            } else {
                logger.warn("Value [" + value + "] is of type [" + value.getClass().getName() + "] and cannot be converted to required type [" + type.getName() + "]");
            }
        } else if (type.isEnum()) {
            return Enum.valueOf(type, value.toString());
        } else {
            logger.warn("Value [" + value + "] is of type [" + value.getClass().getName() + "] and cannot be converted to required type [" + type.getName() + "]");
        }
        return null;
    }
}
