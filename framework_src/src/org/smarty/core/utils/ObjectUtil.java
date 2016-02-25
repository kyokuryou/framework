package org.smarty.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.smarty.core.common.BaseConstant;
import org.smarty.core.exception.InvokeMethodException;
import org.smarty.core.exception.NoSuchReflectException;
import org.smarty.core.io.ParameterMap;

/**
 * Created by Administrator on 2016/2/24.
 */
public final class ObjectUtil {
	private static final Log logger = LogFactory.getLog(ObjectUtil.class);

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof Character) {
			Character ch = (Character) obj;
			return ch <= '\0' || ch == '\t' || ch == '\r' || ch == '\n' || ch == '\f';
		}
		if (obj instanceof String) {
			return "".equals(obj);
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		if (obj instanceof File) {
			return !((File) obj).isFile();
		}
		if (obj instanceof Attribute) {
			return "".equals(((Attribute) obj).getValue());
		}
		// other
		return false;
	}

	public static boolean isEquals(Object obj1, Object obj2) {
		if (obj1 instanceof String && obj2 instanceof String) {
			return obj1.equals(obj2);
		}
		if (obj1 instanceof Date && obj2 instanceof Date) {
			return ((Date) obj1).getTime() == ((Date) obj2).getTime();
		}
		// Character,Number,Null
		return obj1 == obj2;
	}

	public static void assertEmpty(Object obj, String text) {
		assertExpression(isEmpty(obj), text);
	}

	public static void assertNotEmpty(Object obj, String text) {
		assertExpression(!isEmpty(obj), text);
	}

	public static void assertExpression(boolean expression, String text) {
		if (!expression) {
			String message = MessageFormat.format(BaseConstant.ASSERT_EXPRESSION, text);
			throw new IllegalArgumentException(message);
		}
	}

	public static void main(String[] args) {
		Integer a = null;
		Integer b = null;
		assertEmpty("1231", "a and b1");
		assertNotEmpty(null, "a and b2");
	}

	/**
	 * 将指定数组的指定范围复制到一个新数组。该范围的初始索引 (from) 必须位于 0 和 dataArray.length（包括）之间。
	 *
	 * @param array 将要从其复制一个范围的数组
	 * @param from  要复制的范围的初始索引（包括）
	 * @param to    要复制的范围的最后索引（不包括）。（此索引可能位于数组范围之外）。
	 * @return 包含取自原数组指定范围的新数组，截取或用 false 元素填充以获得所需长度
	 */
	public static Object copyArray(Object array, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		int len = Array.getLength(array);
		Class ct = array.getClass().getComponentType();
		Object copy = Array.newInstance(ct, newLength);
		System.arraycopy(array, from, copy, 0, Math.min(len - from, newLength));
		return copy;
	}

	public static <T extends Serializable> ParameterMap copyToParameterMap(T t) {
		if (t == null) {
			return null;
		}
		ParameterMap param = new ParameterMap();

		Field[] fields = BeanUtil.getFields(t.getClass());
		for (Field field : fields) {
			String name = field.getName();
			try {
				param.put(name, BeanUtil.getFieldValue(t, name));
			} catch (NoSuchReflectException e) {
				logger.warn(e);
			} catch (InvokeMethodException e) {
				logger.warn(e);
			}
		}
		return param;
	}

	/**
	 * 克隆一个与原对象相同类型新的对象
	 *
	 * @param src 原对象
	 * @param <E> 对象类型
	 * @return 新对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Serializable> E copy(E src) throws Exception {
		Class<E> ct = (Class<E>) src.getClass();
		return copy(src, ct);
	}

	/**
	 * 克隆一个与原对象相同模型的新的对象
	 *
	 * @param src    原对象
	 * @param target 新对象Class信息
	 * @param <E>    对象类型
	 * @return 新对象
	 * @throws Exception
	 */
	public static <E extends Serializable, T extends Serializable> T copy(E src, Class<T> target) throws Exception {
		Object srcObj = cloneBean(src);
		Class<?> srcCls = src.getClass();
		T targetObj = target.newInstance();
		Field[] fs = target.getDeclaredFields();
		for (Field f : fs) {
			String fn = f.getName();
			String getMethod = BeanUtil.getGetterName(fn);
			String setMethod = BeanUtil.getSetterName(fn);
			Object getValue = srcCls.getDeclaredMethod(getMethod).invoke(srcObj);
			target.getDeclaredMethod(setMethod, f.getType()).invoke(targetObj, getValue);
		}
		return targetObj;
	}

	/**
	 * 深度克隆
	 *
	 * @param src 对象源
	 * @param <E> 对象类型
	 * @return 克隆对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static <E extends Serializable> E cloneBean(E src) throws Exception {
		if (!Serializable.class.isInstance(src)) {
			throw new NotSerializableException();
		}

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(src);
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (E) oi.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
}
