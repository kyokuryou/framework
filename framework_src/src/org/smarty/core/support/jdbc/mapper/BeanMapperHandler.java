package org.smarty.core.support.jdbc.mapper;

import org.smarty.core.exception.InstanceClassException;
import org.smarty.core.exception.InvokeMethodException;
import org.smarty.core.exception.NoSuchReflectException;
import org.smarty.core.support.jdbc.parameter.ModelSerializable;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.BeanUtil;
import org.smarty.core.utils.CommonUtil;
import org.smarty.core.utils.JdbcUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 映射一行数据,放入标准javaBean中
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class BeanMapperHandler<T extends ModelSerializable> implements RowMapperHandler<T> {
    private static RuntimeLogger logger = new RuntimeLogger(BeanMapperHandler.class);

    private Class superClass;

    public BeanMapperHandler(Class superClass) {
        this.superClass = superClass;
    }

    /**
     * 映射JavaBean(非标准JavaBean时,控制台抛出NoSuchReflectException,InvokeMethodException);
     *
     * @param rs resultSet
     * @return bean
     * @throws java.sql.SQLException SQLException 该方法终止,抛给调用者
     *                               InstanceClassException 该方法终止,不抛给调用者
     *                               NoSuchReflectException,InvokeMethodException 该方法不终止,不抛给调用者
     */
    public T rowMapper(ResultSet rs) throws SQLException {
        T obj;
        try {
            obj = (T) BeanUtil.instanceClass(superClass);
        } catch (InstanceClassException e) {
            logger.out(e);
            return null;
        }
        ResultSetMetaData rsm = rs.getMetaData();
        Integer cc = rsm.getColumnCount();
        for (int i = 1; i <= cc; i++) {
            String cn = JdbcUtil.getResultSetColumnName(rsm, i);
            setObject(rs, cn, i, obj);
        }
        return obj;
    }

    private void setObject(ResultSet rs, String cn, int index, T obj) throws SQLException {
        String fn = CommonUtil.toJavaField(cn);
        try {
            Class fc = BeanUtil.getFieldClass(superClass, fn);
            Object value = JdbcUtil.getResultSetValue(rs, index, fc);
            BeanUtil.invokeSetterMethod(obj, fn, value);
        } catch (NoSuchReflectException e) {
            logger.out(e);
        } catch (InvokeMethodException e) {
            logger.out(e);
        }
    }

}
