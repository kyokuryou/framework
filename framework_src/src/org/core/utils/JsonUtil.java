package org.core.utils;

import org.core.Model;
import org.core.support.json.JSONArray;
import org.core.support.json.JSONException;
import org.core.support.json.JSONObject;
import org.core.logger.RuntimeLogger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * json工具
 */
public class JsonUtil {
    private static RuntimeLogger logger = new RuntimeLogger(JsonUtil.class);

    private JsonUtil() {
    }

    /**
     * 转换json字符串
     *
     * @param bean 标准javabean
     * @return json字符串
     */
    public static String JsonEncode(Model bean) {
        JSONObject jsonObject = new JSONObject(bean);
        return jsonObject.toString();
    }

    /**
     * 转换json字符串
     *
     * @param list 原子为标准javabean或允许toString对象
     * @return json字符串
     */
    public static String JsonEncode(List<?> list) {
        JSONArray jsonArray = new JSONArray();
        for (Object bean : list) {
            if (bean instanceof Model) {
                jsonArray.put(new JSONObject(bean));
            } else {
                jsonArray.put(bean);
            }
        }
        return jsonArray.toString();
    }

    /**
     * 转换json字符串
     *
     * @param map 原子为标准javabean或允许toString对象
     * @return json字符串
     */
    public static String JsonEncode(Map<String, ?> map) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Object obj = map.get(key);
            if (obj instanceof Model) {
                jsonMap.put(key, new JSONObject(obj));
            } else {
                jsonMap.put(key, obj);
            }
        }
        JSONObject jsonObject = new JSONObject(jsonMap);
        return jsonObject.toString();
    }

    /**
     * 解析json字符串
     *
     * @param source    json字符串
     * @param beanClass 与json字符串描述一致的标准javabean
     * @return 标准javabean
     * @throws JSONException 在json字符串中未发现该key
     */
    public static Object JsonDecode(String source, Class beanClass) throws JSONException {
        Object bean = null;
        try {
            bean = beanClass.newInstance();
        } catch (Exception e1) {
            source = null;
            logger.out(e1);
        }
        if (LogicUtil.isEmpty(source))
            return null;

        JSONObject jsonObj = new JSONObject(source);
        Method[] setMethods = BeanUtil.getSetterMethods(beanClass);

        for (Method setMethod : setMethods) {
            try {
                Field field = BeanUtil.getMethodTargetField(setMethod);
                setMethod.invoke(bean, new Object[]{jsonObj.get(field.getName())});
            } catch (Exception e) {
                logger.out(e);
            }
        }
        return bean;
    }
}
