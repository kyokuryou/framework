package org.smarty.core.jdbc.mapper;

import android.database.Cursor;
import android.util.Log;
import org.smarty.core.jdbc.SQLException;
import org.smarty.core.utils.JdbcUtil;

import java.lang.reflect.Method;

/**
 * 映射一行数据,放入标准javaBean中
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class BeanMapperHandler<T> implements RowMapperHandler<T> {
    private final String TAG = "BeanMapperHandler";

    private Class<T> mappedClass;

    public BeanMapperHandler(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
    }

    /**
     * 映射JavaBean(非标准JavaBean时,控制台抛出NoSuchReflectException,InvokeMethodException);
     *
     * @param cursor Cursor
     * @return bean
     * @throws SQLException SQLException 该方法终止,抛给调用者
     *                      InstanceClassException 该方法终止,不抛给调用者
     *                      NoSuchReflectException,InvokeMethodException 该方法不终止,不抛给调用者
     */
    @Override
    public T rowMapper(Cursor cursor) throws SQLException {
        if (mappedClass == null) {
            throw new NullPointerException("class is null");
        }
        T obj;
        try {
            obj = mappedClass.newInstance();
        } catch (Exception e) {
            return null;
        }
        int count = cursor.getColumnCount();
        for (int i = 0; i < count; i++) {
            invokeSetterMethod(obj, cursor.getColumnName(i), JdbcUtil.getColumnValue(cursor, i));
        }
        return obj;
    }

    private void invokeSetterMethod(T obj, String name, Object values) {
        char[] nm = name.toCharArray();
        nm[0] = Character.toUpperCase(nm[0]);
        String nms = String.valueOf(nm);
        try {
            Method setMethod = mappedClass.getMethod(nms, values.getClass());
            setMethod.invoke(obj, values);
        } catch (Exception e) {
            Log.e(TAG, "set" + nms, e);
        }
    }

}
