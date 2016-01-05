package org.smarty.core.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.smarty.core.common.BaseConstant;

/**
 * @author qul
 * @since LVGG1.1
 */
public class ParameterMap extends HashMap<String, Object> implements ParameterSerializable {

    public String getString(String key) {
        Object val = super.get(key);
        return convertString(val);
    }

    public byte[] getBytes(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return new byte[0];
        }
        return res.getBytes(BaseConstant.DEF_CHARSET);
    }

    public char[] getChars(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return new char[0];
        }
        return res.toCharArray();
    }

    public short getShort(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return Short.MIN_VALUE;
        }
        return Short.valueOf(res);
    }

    public int getInt(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return Integer.MIN_VALUE;
        }
        return Integer.valueOf(res);
    }

    public long getLong(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return Long.MIN_VALUE;
        }
        return Long.valueOf(res);
    }

    public float getFloat(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return Float.MIN_VALUE;
        }
        return Float.valueOf(res);
    }

    public double getDouble(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return Double.MIN_VALUE;
        }
        return Double.valueOf(res);
    }

    public boolean getBoolean(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return false;
        }
        return Boolean.valueOf(res);
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(key, convertString(value));
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        if (m == null || m.isEmpty()) {
            return;
        }
        Set<? extends Map.Entry<? extends String, ?>> mes = m.entrySet();
        for (Map.Entry<? extends String, ?> me : mes) {
            put(me.getKey(), me.getValue());
        }
    }

    @Override
    public String remove(Object key) {
        Object val = super.remove(key);
        return convertString(val);
    }

    private String convertString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Enum) {
            return value.toString();
        }
        return String.valueOf(value);
    }
}
