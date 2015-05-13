package org.smarty.core.utils;

import android.util.Log;
import org.smarty.core.beans.Entry;
import org.smarty.core.beans.EntryType;
import org.smarty.core.http.HttpMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public final class EntryUtil {
    private final static String TAG = "EntryUtil";
    private final static String DATA_ROOT = "result";

    public static <T extends Entry> T parseEntry(HttpMessage hm, Class<T> cls) {
        Assert.notNull(cls, "'entry class' must not be null");

        String data = hm.getData();
        try {
            JSONObject dobj = new JSONObject(data);
            JSONObject robj = dobj.getJSONObject(DATA_ROOT);
            getMessageValue(hm, robj);
            return createEntry(robj, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Entry> T createEntry(JSONObject rootData, Class<T> cls) {
        Assert.notNull(cls, "'entry class' must not be null");
        try {
            T entry = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                String fnm = field.getName();
                Object val = getObject(field, rootData, fnm);
                if (val == null) {
                    continue;
                }
                Method m = cls.getMethod(getSetMethod(fnm), field.getType());
                m.invoke(entry, val);
            }
            return entry;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    private static String getSetMethod(String fieldName) {
        char[] fnmc = fieldName.toCharArray();
        fnmc[0] = Character.toUpperCase(fnmc[0]);
        return "set" + String.valueOf(fnmc);
    }

    private static Object getObject(Field field, JSONObject rootData, String name) {
        Assert.notNull(field, "'field' must not be null");

        Class<?> ft = field.getType();
        try {
            if (List.class.isAssignableFrom(ft)) {
                return getEntryList(getFieldGenericType(field), rootData.getJSONArray(name));
            } else if (Entry.class.isAssignableFrom(ft)) {
                return getEntryBean(ft, rootData.getJSONObject(name));
            } else {
                return castValue(ft, rootData.get(name));
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    private static Object castValue(Class<?> cls, Object value) {
        if (Number.class.isAssignableFrom(cls)) {
            return Integer.valueOf((String) value);
        } else if (byte.class.isAssignableFrom(cls)) {
            return Byte.parseByte((String) value);
        } else if (short.class.isAssignableFrom(cls)) {
            return Short.parseShort((String) value);
        } else if (int.class.isAssignableFrom(cls)) {
            return Integer.parseInt((String) value);
        } else if (long.class.isAssignableFrom(cls)) {
            return Long.parseLong((String) value);
        } else if (boolean.class.isAssignableFrom(cls)) {
            return Boolean.parseBoolean((String) value);
        } else if (float.class.isAssignableFrom(cls)) {
            return Float.parseFloat((String) value);
        } else if (double.class.isAssignableFrom(cls)) {
            return Double.parseDouble((String) value);
        } else if (Date.class.isAssignableFrom(cls)) {
            // TODO Date convert from yyyy-MM-dd HH:mm:ss
        }
        return String.valueOf(value);
    }

    private static List<?> getEntryList(Class<?> ft, JSONArray array) {
        List list = new ArrayList();
        for (int i = 0, len = array.length(); i < len; i++) {
            try {
                list.add(getEntryBean(ft, array.getJSONObject(i)));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return list;
    }

    private static Object getEntryBean(Class<?> ft, JSONObject object) {
        Assert.notNull(ft, "'field class' must not be null");
        try {
            Object obj = ft.newInstance();
            Field[] fields = ft.getDeclaredFields();
            for (Field field : fields) {
                String fnm = field.getName();
                Object val = getObject(field, object, fnm);
                if (val == null) {
                    continue;
                }
                Class<?> ftp = field.getType();
                Method m = ft.getMethod(getSetMethod(fnm), ftp);
                m.invoke(obj, val);
            }
            return obj;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    private static Class<?> getFieldGenericType(Field field) throws Exception {
        Assert.notNull(field, "'field class' must not be null");
        EntryType et = field.getAnnotation(EntryType.class);
        return et.cls();
    }

    private static void getMessageValue(HttpMessage hm, JSONObject rootData) throws JSONException {
        String success = rootData.getString("success");
        String msg = rootData.getString("msg");
        hm.setSuccess(Integer.valueOf(success));
        hm.setMessage(msg);
    }
}
