package org.smarty.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.exception.InstanceClassException;
import org.smarty.core.exception.InvokeFieldException;
import org.smarty.core.exception.InvokeMethodException;
import org.smarty.core.exception.NoSuchReflectException;

/**
 * 反射工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public final class BeanUtil {
	private static Log logger = LogFactory.getLog(BeanUtil.class);

	private BeanUtil() {
	}

	/**
	 * 返回与带有给定字符串名的类或接口相关联的 Class 对象。
	 *
	 * @param className 所需类的完全限定名。
	 * @return Class对象。
	 */
	public static Class<?> getClassForName(String className) throws InstanceClassException {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			logger.warn(e);
			throw new InstanceClassException(e);
		}
	}

	/**
	 * 返回字段的声明类型。
	 *
	 * @param clazz Class 对象
	 * @param name  所表示字段的字符串
	 * @return Class对象
	 * @throws InvokeFieldException 未发现字段表示的字符串
	 */
	public static Field getField(Class<?> clazz, String name) throws InvokeFieldException {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			if ("java.lang.Object".equals(clazz.getSuperclass().getName())) {
				logger.warn("not find field:" + name);
				throw new InvokeFieldException(e);
			}
			return getField(clazz.getSuperclass(), name);
		}
	}

	/**
	 * 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段。
	 *
	 * @param clazz   Class 对象。
	 * @param exclude 排除字段
	 * @return Field对象数组
	 */
	public static Field[] getFields(Class<?> clazz, String exclude) {
		List<Field> fs = new ArrayList<Field>();
		Field[] all = clazz.getDeclaredFields();
		for (Field field : all) {
			String fname = field.getName();
			if (!ObjectUtil.isEmpty(exclude) && exclude.contains(fname)) {
				continue;
			}
			fs.add(field);
		}
		return fs.toArray(new Field[fs.size()]);
	}

	/**
	 * 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段。
	 * 与 getFields(clazz, "");等价
	 *
	 * @param clazz Class 对象。
	 * @return Field 对象数组
	 */
	public static Field[] getFields(Class<?> clazz) {
		return getFields(clazz, "");
	}

	/**
	 * 返回字段的声明类型。
	 *
	 * @param clazz Class 对象
	 * @param name  所表示字段的字符串
	 * @return Class对象
	 * @throws InvokeFieldException 未发现字段表示的字符串
	 */
	public static Class<?> getFieldClass(Class<?> clazz, String name) throws InvokeFieldException {
		try {
			return clazz.getDeclaredField(name).getType();
		} catch (NoSuchFieldException e) {
			if ("java.lang.Object".equals(clazz.getSuperclass().getName())) {
				logger.warn("not find field:" + name);
				throw new InvokeFieldException(e);
			}
			return getFieldClass(clazz.getSuperclass(), name);
		}
	}

	/**
	 * 取得targer对象的method方法, 参数是params
	 *
	 * @param target 实例
	 * @param method 所表示方法的字符串
	 * @param params 方法参数
	 * @return Method 对象
	 * @throws InvokeMethodException 未发现方法表示的字符串
	 */
	public static Method getMethod(Object target, String method, Object... params) throws InvokeMethodException {

		Class<?> clazz = target.getClass();
		Class<?>[] cls = null;

		if (params != null) {
			cls = new Class<?>[params.length];
			for (int i = 0; i < cls.length; ++i) {
				if (params[i] == null) {
					cls[i] = null;
				} else {
					cls[i] = params[i].getClass();
				}
			}
		}
		try {
			return clazz.getMethod(method, cls);
		} catch (NoSuchMethodException e) {
			logger.warn(e);
			throw new InvokeMethodException(e);
		}
	}

	/**
	 * 返回字段表示字符串,所对应的Set方法
	 *
	 * @param fieldname 所表示字段的字符串
	 * @return 所表示方法的字符串
	 */
	public static String getSetterName(String fieldname) {
		return getXetName(fieldname, "set");
	}

	/**
	 * 返回字段表示字符串,所对应的get方法
	 *
	 * @param fieldname 所表示字段的字符串
	 * @return 所表示方法的字符串
	 */
	public static String getGetterName(String fieldname) {
		return getXetName(fieldname, "get");
	}

	/**
	 * 返回字段表示字符串,所对应的is方法
	 *
	 * @param fieldname 所表示字段的字符串
	 * @return 所表示方法的字符串
	 */
	public static String getIsName(String fieldname) {
		return getXetName(fieldname, "is");
	}

	/**
	 * 返回字段表示字符串,所对应的Xet方法
	 *
	 * @param fieldname 所表示字段的字符串
	 * @param xxx       方法前缀
	 * @return 所表示方法的字符串
	 */
	private static String getXetName(String fieldname, String xxx) {
		char[] fns = fieldname.toCharArray();
		StringBuilder buff = new StringBuilder();
		buff.append(xxx);
		buff.append(Character.toTitleCase(fns[0]));
		buff.append(fns, 1, fns.length - 1);
		return buff.toString();
	}

	/**
	 * 返回该clazz的所有get方法
	 *
	 * @param clazz class对象
	 * @return 该clazz的所有get方法的Method对象数组
	 */
	public static Method[] getGetterMethods(Class<?> clazz) {
		ArrayList<Method> ms = new ArrayList<Method>();
		Method[] all = clazz.getMethods();
		for (Method anAll : all) {
			String mname = anAll.getName();
			if (mname.startsWith("get") || mname.startsWith("is")) {
				ms.add(anAll);
			}
		}
		return ms.toArray(new Method[ms.size()]);
	}

	/**
	 * 返回该clazz的所有set方法
	 *
	 * @param clazz class对象
	 * @return 该clazz的所有set方法的Method对象数组
	 */
	public static Method[] getSetterMethods(Class<?> clazz) {
		ArrayList<Method> ms = new ArrayList<Method>();
		Method[] all = clazz.getMethods();
		for (Method anAll : all) {
			String mname = anAll.getName();
			if (mname.startsWith("set")) {
				ms.add(anAll);
			}
		}
		return ms.toArray(new Method[ms.size()]);
	}

	/**
	 * 创建clazz类的对象实例,params是构造函数的参数
	 *
	 * @param clazz  class对象
	 * @param params 构造参数
	 * @return 该clazz的实例
	 * @throws InstanceClassException 创建失败
	 */
	public static <T> T instanceClass(Class<T> clazz, Object... params) throws InstanceClassException, InvokeMethodException {
		Class<?>[] cls = new Class<?>[params.length];
		for (int i = 0; i < cls.length; ++i) {
			cls[i] = params[i].getClass();
		}
		try {
			Constructor<T> cons = clazz.getConstructor(cls);
			return cons.newInstance(params);
		} catch (InvocationTargetException e) {
			logger.warn(e);
			throw new InstanceClassException(e);
		} catch (NoSuchMethodException e) {
			logger.warn(e);
			throw new InvokeMethodException(e);
		} catch (InstantiationException e) {
			logger.warn(e);
			throw new InstanceClassException(e);
		} catch (IllegalAccessException e) {
			logger.warn(e);
			throw new InstanceClassException(e);
		}
	}

	/**
	 * 返回该对象继承到的所有的接口(包括继承到的接口)
	 *
	 * @param clzssName Class所表示的字符串
	 * @return 该class的所有接口
	 */
	public static Class<?>[] allInterfaces(String clzssName) {
		List<Class<?>> inters = new ArrayList<Class<?>>();
		try {
			Class<?> clazz = Class.forName(clzssName);
			do {
				Class<?>[] cls = clazz.getInterfaces();
				Collections.addAll(inters, cls);
				clazz = clazz.getSuperclass();
			} while (clazz != null);
			return inters.toArray(new Class<?>[0]);
		} catch (ClassNotFoundException e) {
			logger.warn(e);
		}
		return new Class<?>[0];
	}

	/**
	 * 调用target实例中,由字段名对应的set方法
	 *
	 * @param target    实例
	 * @param fieldName 表示字段的字符串
	 * @param params    方法参数
	 * @throws NoSuchReflectException 未发现方法
	 * @throws InvokeMethodException  调用失败
	 */
	public static void invokeSetterMethod(Object target, String fieldName, Object params) throws NoSuchReflectException, InvokeMethodException {
		if (params == null) {
			return;
		}
		invoke(target, getSetterName(fieldName), params);
	}

	/**
	 * 调用target实例中,由字段名对应的get方法
	 *
	 * @param target    实例
	 * @param fieldName 表示字段的字符串
	 * @throws NoSuchReflectException 未发现方法
	 * @throws InvokeMethodException  调用失败
	 */
	public static Object invokeGetterMethod(Object target, String fieldName) throws NoSuchReflectException, InvokeMethodException {
		return invoke(target, getGetterName(fieldName));
	}

	/**
	 * 执行targer对象的method方法, 参数是params
	 *
	 * @param target 实例
	 * @param method 表示方法的字符串
	 * @param params 参数
	 * @return 方法的结果, void返回null
	 * @throws NoSuchReflectException,java.lang.reflect.InvocationTargetException
	 */
	public static Object invoke(Object target, String method, Object... params) throws NoSuchReflectException, InvokeMethodException {
		Method m = getMethod(target, method, params);
		try {
			return m.invoke(target, params);
		} catch (InvocationTargetException e) {
			logger.warn(e);
			throw new InvokeMethodException(e);
		} catch (IllegalAccessException e) {
			logger.warn(e);
			throw new InvokeMethodException(e);
		}
	}

	/**
	 * 测试obj是否有效
	 *
	 * @param obj 要测试的对象
	 * @return 如果obj不为null, 并且如果obj是Number类型则>=0, 并且obj是String类型长度>0 则返回true;
	 * 否则返回false;
	 */
	public static boolean isValid(Object obj) {
		boolean r = false;
		if (obj != null) {
			r = true;
			if (obj instanceof Number) {
				Number num = (Number) obj;
				r = num.doubleValue() >= 0;
			}
			if (obj instanceof String) {
				String str = obj.toString();
				r = str.trim().length() > 0;
			}
		}
		return r;
	}

	/**
	 * 取得get/set方法对应的属性,属性的类型和带参数方法的参数类型可能不同
	 *
	 * @param m - 方法
	 * @return 属性, 没有返回null
	 */
	public static Field getMethodTargetField(Method m) throws NoSuchReflectException {
		String name = m.getName();

		if (name.length() > 3 && (name.startsWith("set") || name.startsWith("get"))) {
			char[] ch = name.toCharArray();
			ch[3] = Character.toLowerCase(ch[3]);
			name = new String(ch, 3, ch.length - 3);
		} else if (name.length() > 2 && name.startsWith("is")) {
			char[] ch = name.toCharArray();
			ch[2] = Character.toLowerCase(ch[2]);
			name = new String(ch, 2, ch.length - 2);
		}
		try {
			return m.getDeclaringClass().getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			logger.warn(e);
			throw new NoSuchReflectException(e);
		}
	}

	/**
	 * 通过bean对象fieldname属性对应的getter方法取得该属性的值
	 *
	 * @param bean      - 数据对象, 如果为null, 会抛出异常
	 * @param fieldName - 属性名, 如果为null, 则返回null
	 * @return 属性的值
	 */
	public static Object getFieldValue(Object bean, String fieldName) throws NoSuchReflectException, InvokeMethodException {
		ObjectUtil.assertNotEmpty(fieldName, "this fieldName is required; it must not be null");
		String fieldGetMethod = getGetterName(fieldName);
		try {
			return invoke(bean, fieldGetMethod);
		} catch (NoSuchReflectException e) {
			logger.warn(e);
			throw new NoSuchReflectException(e);
		} catch (InvokeMethodException e) {
			logger.warn(e);
			throw e;
		}
	}

	/**
	 * 根据Field类型,转换value
	 *
	 * @param field 字段
	 * @param value 值
	 * @return 转换后的值
	 */
	public static Object toCase(Field field, String value) {
		ObjectUtil.assertNotEmpty(field, "this field is required; it must not be null");
		ObjectUtil.assertNotEmpty(value, "this value is required; it must not be null");

		Class<?> fieldType = field.getType();
		if (fieldType.isAssignableFrom(boolean.class) || fieldType.isAssignableFrom(Boolean.class)) {
			return Boolean.valueOf(value);
		} else if (fieldType.isAssignableFrom(byte.class) || fieldType.isAssignableFrom(Byte.class)) {
			return Byte.valueOf(value);
		} else if (fieldType.isAssignableFrom(char.class) || fieldType.isAssignableFrom(Character.class)) {
			if (fieldType.isArray()) {
				return value.toCharArray();
			}
			return value.toCharArray()[0];
		} else if (fieldType.isAssignableFrom(double.class) || fieldType.isAssignableFrom(Double.class)) {
			return Double.valueOf(value);
		} else if (fieldType.isAssignableFrom(float.class) || fieldType.isAssignableFrom(Float.class)) {
			return Float.valueOf(value);
		} else if (fieldType.isAssignableFrom(int.class) || fieldType.isAssignableFrom(Integer.class)) {
			return Integer.valueOf(value);
		} else if (fieldType.isAssignableFrom(long.class) || fieldType.isAssignableFrom(Long.class)) {
			return Long.valueOf(value);
		} else if (fieldType.isAssignableFrom(short.class) || fieldType.isAssignableFrom(Short.class)) {
			return Short.valueOf(value);
		} else if (fieldType.isAssignableFrom(String.class)) {
			return value;
		} else {
			return fieldType.cast(value);
		}
	}

	/**
	 * 基本类型Class转换引用类型Class字符串
	 *
	 * @param klass 基本类型Class
	 * @return 引用类型Class字符串
	 */
	public static String getClassName(Class<?> klass) {
		ObjectUtil.assertNotEmpty(klass, "this class is required; it must not be null");
		// 判断是否是数组,如果是递归调用
		if (klass.isArray()) {
			return getClassName(klass.getComponentType());
		}

		// 获得基本类型的引用类
		if (klass.isAssignableFrom(boolean.class)) {
			return Boolean.class.getName();
		} else if (klass.isAssignableFrom(byte.class)) {
			return Byte.class.getName();
		} else if (klass.isAssignableFrom(char.class)) {
			return Character.class.getName();
		} else if (klass.isAssignableFrom(double.class)) {
			return Double.class.getName();
		} else if (klass.isAssignableFrom(float.class)) {
			return Float.class.getName();
		} else if (klass.isAssignableFrom(int.class)) {
			return Integer.class.getName();
		} else if (klass.isAssignableFrom(long.class)) {
			return Long.class.getName();
		} else if (klass.isAssignableFrom(short.class)) {
			return Short.class.getName();
		}
		return klass.getName();
	}
}