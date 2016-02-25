package org.smarty.core.io;

import java.util.LinkedHashMap;
import org.smarty.core.common.BaseConstant;
import org.smarty.core.utils.ObjectUtil;

/**
 * @author qul
 * @since LVGG1.1
 */
public final class ModelMap extends LinkedHashMap<String, Object> implements ModelSerializable {
	public static final long serialVersionUID = 42L;

	public String getString(String key) {
		return (String) super.get(key);
	}

	public Number getNumber(String key) {
		return (Number) super.get(key);
	}

	public byte[] getBytes(String key) {
		String res = getString(key);
		if (ObjectUtil.isEmpty(res)) {
			return new byte[0];
		}
		return res.getBytes(BaseConstant.DEF_ENCODE);
	}

	public char[] getChars(String key) {
		String res = getString(key);
		if (ObjectUtil.isEmpty(res)) {
			return new char[0];
		}
		return res.toCharArray();
	}

	public short getShort(String key) {
		Number res = getNumber(key);
		if (res == null) {
			return Short.MIN_VALUE;
		}
		return res.shortValue();
	}

	public int getInt(String key) {
		Number res = getNumber(key);
		if (res == null) {
			return Integer.MIN_VALUE;
		}
		return res.intValue();
	}

	public long getLong(String key) {
		Number res = getNumber(key);
		if (res == null) {
			return Long.MIN_VALUE;
		}
		return res.longValue();
	}

	public float getFloat(String key) {
		Number res = getNumber(key);
		if (res == null) {
			return Float.MIN_VALUE;
		}
		return res.floatValue();
	}

	public double getDouble(String key) {
		Number res = getNumber(key);
		if (res == null) {
			return Double.MIN_VALUE;
		}
		return res.doubleValue();
	}

	public boolean getBoolean(String key) {
		String res = getString(key);
		if (res == null || "".equals(res)) {
			return false;
		}
		return Boolean.valueOf(res);
	}

}
