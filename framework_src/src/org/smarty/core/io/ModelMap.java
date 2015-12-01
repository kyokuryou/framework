package org.smarty.core.io;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;

/**
 * @author qul
 * @since LVGG1.1
 */
public final class ModelMap extends LinkedHashMap<String, Object> implements ModelSerializable {
    public static final long serialVersionUID = 42L;
    private final Charset charset = Charset.forName("UTF-8");

    public String getString(String key) {
        return (String) super.get(key);
    }

    public byte[] getBytes(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return new byte[0];
        }
        return res.getBytes(charset);
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
}
