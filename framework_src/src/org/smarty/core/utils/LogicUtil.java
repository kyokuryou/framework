package org.smarty.core.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 逻辑工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class LogicUtil {
	private LogicUtil() {
	}

	/**
	 * 数组空判断
	 *
	 * @param array 数组
	 * @return 结果
	 */
	public static boolean isEmptyArray(Object array) {
		return array == null || !array.getClass().isArray() || Array.getLength(array) == 0;
	}

	/**
	 * 空判断
	 *
	 * @param string 字符串
	 * @return 结果
	 */
	public static boolean isEmpty(String string) {
		return string == null || "".equals(string);
	}

	/**
	 * 空判断
	 *
	 * @param character 字符
	 * @return 结果
	 */
	public static boolean isEmpty(Character character) {
		return character == null || Character.isSpaceChar(character);
	}

	/**
	 * 数组非空判断
	 *
	 * @param array 数组
	 * @return 结果
	 */
	public static boolean isNotEmptyArray(Object array) {
		return array != null && array.getClass().isArray() && Array.getLength(array) > 0;
	}

	/**
	 * 非空判断
	 *
	 * @param string 字符串
	 * @return 结果
	 */
	public static boolean isNotEmpty(String string) {
		return string != null && !"".equals(string);
	}

	/**
	 * 非空判断
	 *
	 * @param character 字符
	 * @return 结果
	 */
	public static boolean isNotEmpty(Character character) {
		return character != null && !Character.isSpaceChar(character);
	}

	/**
	 * 空List判断
	 *
	 * @param list list
	 * @return 结果
	 */
	public static boolean isEmptyCollection(Collection list) {
		return list == null || list.size() == 0;
	}

	/**
	 * 空Map判断
	 *
	 * @param map map
	 * @return 结果
	 */
	public static boolean isEmptyMap(Map map) {
		return map == null || map.size() == 0;
	}

	/**
	 * 非空List判断
	 *
	 * @param list list
	 * @return 结果
	 */
	public static boolean isNotEmptyCollection(Collection list) {
		return list != null && list.size() > 0;
	}

	/**
	 * 非空Map判断
	 *
	 * @param map map
	 * @return 结果
	 */
	public static boolean isNotEmptyMap(Map map) {
		return map != null && map.size() > 0;
	}

	/**
	 * 空白字符判断
	 *
	 * @param ch 字符
	 * @return boolean
	 */
	public static boolean isSpace(Character ch) {
		return ch <= '\0' || ch == '\t' || ch == '\r' || ch == '\n' || ch == '\f';
	}

	/**
	 * 判断是否是八种基本,引用类型
	 *
	 * @param value
	 * @return boolean
	 */
	public static Boolean isPrimitive(Object value) {
		Class<?> klass = value.getClass();
		Boolean isPrimitive = klass.isPrimitive();
		isPrimitive = isPrimitive || klass.isAssignableFrom(Byte.class);
		isPrimitive = isPrimitive || klass.isAssignableFrom(Short.class);
		isPrimitive = isPrimitive || klass.isAssignableFrom(Integer.class);
		isPrimitive = isPrimitive || klass.isAssignableFrom(Long.class);
		isPrimitive = isPrimitive || klass.isAssignableFrom(Float.class);
		isPrimitive = isPrimitive || klass.isAssignableFrom(Double.class);
		isPrimitive = isPrimitive || klass.isAssignableFrom(Character.class);
		isPrimitive = isPrimitive || klass.isAssignableFrom(String.class);
		isPrimitive = isPrimitive || klass.isAssignableFrom(Boolean.class);
		return isPrimitive;
	}
}
