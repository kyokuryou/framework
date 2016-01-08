package org.smarty.core.support.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.commons.beanutils.BeanUtils;
import org.smarty.core.exception.InstanceClassException;
import org.smarty.core.io.ModelMap;
import org.smarty.core.io.ModelSerializable;
import org.smarty.core.io.RuntimeLogger;
import org.smarty.core.utils.BeanUtil;
import org.smarty.core.utils.CommonUtil;
import org.smarty.core.utils.JdbcUtil;

/**
 * @author qul
 * @since LVGG1.1
 */
public class ModelMapperHandler<T extends ModelSerializable> implements RowMapperHandler<T> {

    private static RuntimeLogger logger = new RuntimeLogger(ModelMapperHandler.class);

    private Class<?> superClass;

    public ModelMapperHandler(Class<?> superClass) {
        this.superClass = superClass;
    }

    @Override
    public T rowMapper(ResultSet rs) throws SQLException {
        if (superClass == null) {
            return null;
        }
        if (superClass.isAssignableFrom(ModelMap.class)) {
            return createModelMap(rs);
        }
        return createModelBean(rs);
    }

    @SuppressWarnings("unchecked")
    private T createModelMap(ResultSet rs) throws SQLException {
        ModelMap mm = new ModelMap();
        ResultSetMetaData rsm = rs.getMetaData();
        int cc = rsm.getColumnCount();
        for (int i = 1; i <= cc; i++) {
            String cn = JdbcUtil.getResultSetColumnName(rsm, i);
            Object value = JdbcUtil.getResultSetValue(rs, i);
            mm.put(cn, value);
        }
        return (T) mm;
    }

    @SuppressWarnings("unchecked")
    private T createModelBean(ResultSet rs) throws SQLException {
        T obj;
        try {
            obj = (T) BeanUtil.instanceClass(superClass);
        } catch (InstanceClassException e) {
            logger.out(e);
            return null;
        }
        ResultSetMetaData rsm = rs.getMetaData();
        int cc = rsm.getColumnCount();
        for (int i = 1; i <= cc; i++) {
            String cn = JdbcUtil.getResultSetColumnName(rsm, i);
            Object value = JdbcUtil.getResultSetValue(rs, i);
            String fn = CommonUtil.toJavaField(cn);
            try {
                BeanUtils.setProperty(obj, fn, value);
            } catch (IllegalAccessException e) {
                logger.out(e);
            } catch (InvocationTargetException e) {
                logger.out(e);
            }
        }
        return obj;
    }
}
