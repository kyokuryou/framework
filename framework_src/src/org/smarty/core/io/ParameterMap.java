package org.smarty.core.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.smarty.core.common.BaseConstant;

/**
 * @author qul
 * @since LVGG1.1
 */
public class ParameterMap extends HashMap<String, Object> implements ParameterSerializable {

    public String getString(String key) {
        return convertString(super.get(key));
    }

    public String[] getStringArray(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return null;
        }
        return res.split(",");
    }

    public byte[] getBytes(String key) {
        String res = getString(key);
        if (res == null || "".equals(res)) {
            return new byte[0];
        }
        return res.getBytes(BaseConstant.DEF_ENCODE);
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

    public List<String> getList(String key) {
        String[] res = getStringArray(key);
        if (res == null || res.length == 0) {
            return null;
        }
        return Arrays.asList(res);
    }

    public List<Integer> getIntList(String key) {
        String[] res = getStringArray(key);
        if (res == null || res.length == 0) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (String re : res) {
            list.add(Integer.valueOf(re));
        }
        return list;
    }

    public List<Long> getLongList(String key) {
        String[] res = getStringArray(key);
        if (res == null || res.length == 0) {
            return null;
        }
        List<Long> list = new ArrayList<Long>();
        for (String re : res) {
            list.add(Long.valueOf(re));
        }
        return list;
    }

    @Override
    public Object put(String key, Object value) {
        if (value instanceof List) {
            return super.put(key, value);
        }
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

    public String removeString(String key) {
        return convertString(super.remove(key));
    }

    public byte[] removeBytes(String key) {
        String res = removeString(key);
        if (res == null || "".equals(res)) {
            return new byte[0];
        }
        return res.getBytes(BaseConstant.DEF_ENCODE);
    }

    public char[] removeChars(String key) {
        String res = removeString(key);
        if (res == null || "".equals(res)) {
            return new char[0];
        }
        return res.toCharArray();
    }

    public short removeShort(String key) {
        String res = removeString(key);
        if (res == null || "".equals(res)) {
            return Short.MIN_VALUE;
        }
        return Short.valueOf(res);
    }

    public int removeInt(String key) {
        String res = removeString(key);
        if (res == null || "".equals(res)) {
            return Integer.MIN_VALUE;
        }
        return Integer.valueOf(res);
    }

    public long removeLong(String key) {
        String res = removeString(key);
        if (res == null || "".equals(res)) {
            return Long.MIN_VALUE;
        }
        return Long.valueOf(res);
    }

    public float removeFloat(String key) {
        String res = removeString(key);
        if (res == null || "".equals(res)) {
            return Float.MIN_VALUE;
        }
        return Float.valueOf(res);
    }

    public double removeDouble(String key) {
        String res = removeString(key);
        if (res == null || "".equals(res)) {
            return Double.MIN_VALUE;
        }
        return Double.valueOf(res);
    }

    public boolean removeBoolean(String key) {
        String res = removeString(key);
        if (res == null || "".equals(res)) {
            return false;
        }
        return Boolean.valueOf(res);
    }

    private String convertString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Enum) {
            return value.toString();
        }
        if (value instanceof String) {
            return (String) value;
        }
        return String.valueOf(value);
    }
}
