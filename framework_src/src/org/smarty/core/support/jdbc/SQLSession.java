package org.smarty.core.support.jdbc;

import java.util.List;
import javax.sql.DataSource;
import org.dom4j.Element;
import org.smarty.core.bean.Pager;
import org.smarty.core.io.ModelMap;
import org.smarty.core.io.ModelSerializable;
import org.smarty.core.io.ParameterSerializable;
import org.smarty.core.support.jdbc.holder.HolderFactory;
import org.smarty.core.support.jdbc.holder.SQLHolder;
import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.support.DBType;
import org.springframework.beans.factory.InitializingBean;

/**
 * 简单的JDBC支持
 */
public final class SQLSession extends JdbcSupport implements IStringSQL, InitializingBean {
    private DBType sqlType;

    public SQLSession() {
    }

    public SQLSession(DataSource dataSource, DBType sqlType) {
        this.sqlType = sqlType;
        super.setDataSource(dataSource);
    }

    public void setSqlType(DBType sqlType) {
        this.sqlType = sqlType;
    }

    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /**
     * 静态Holder(sql文来自程序)
     *
     * @param sql sql
     * @return SQLHolder
     */
    protected SQLHolder getHolder(SQL sql) {
        return HolderFactory.getHolder(sql, sqlType);
    }

    public void afterPropertiesSet() throws Exception {

    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是int)
     *
     * @param sql sql
     * @return int
     */
    public int queryForInt(SQL sql) {
        Number res = (Number) __query_object(getHolder(sql), null);
        return res == null ? 0 : res.intValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是int)
     *
     * @param sql    sql
     * @param params 参数
     * @return int
     */
    public <P extends ParameterSerializable> int queryForInt(SQL sql, P params) {
        Number res = (Number) __query_object(getHolder(sql), params);
        return res == null ? 0 : res.intValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是long)
     *
     * @param sql sql
     * @return Long
     */
    public long queryForLong(SQL sql) {
        Number res = (Number) __query_object(getHolder(sql), null);
        return res == null ? 0 : res.longValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是long)
     *
     * @param sql    sql
     * @param params 参数
     * @return Long
     */
    public <P extends ParameterSerializable> long queryForLong(SQL sql, P params) {
        Number res = (Number) __query_object(getHolder(sql), params);
        return res == null ? 0 : res.longValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param sql sql
     * @return Object
     */
    public Object queryForObject(SQL sql) {
        return __query_object(getHolder(sql), null);
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Object
     */
    public <P extends ParameterSerializable> Object queryForObject(SQL sql, P params) {
        return __query_object(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(多行单列数据)
     *
     * @param sql sql
     * @return List
     */
    public List<Object> queryForObjectList(SQL sql) {
        return __query_object_list(getHolder(sql), null);
    }

    /**
     * 执行由调用者提供SQL文(多行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public <P extends ParameterSerializable> List<Object> queryForObjectList(SQL sql, P params) {
        return __query_object_list(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql sql
     * @return List
     */
    public ModelMap queryForModel(SQL sql) {
        return __query_model(getHolder(sql), null, null);
    }


    public <P extends ParameterSerializable> ModelMap queryForModel(SQL sql, P params) {
        return __query_model(getHolder(sql), params, null);
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql   sql
     * @param klass 类型参数
     * @return List
     */
    public <M extends ModelSerializable> M queryForModel(SQL sql, Class<M> klass) {
        return __query_model(getHolder(sql), null, klass);
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql   sql
     * @param klass 类型参数
     * @return List
     */
    public <P extends ParameterSerializable, M extends ModelSerializable> M queryForModel(SQL sql, P params, Class<M> klass) {
        return __query_model(getHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql sql
     * @return List
     */
    public List<ModelMap> queryForModelList(SQL sql) {
        return __query_model_list(getHolder(sql), null, null);
    }

    public <P extends ParameterSerializable> List<ModelMap> queryForModelList(SQL sql, P params) {
        return __query_model_list(getHolder(sql), params, null);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql   sql
     * @param klass 类型参数
     * @return List
     */
    public <M extends ModelSerializable> List<M> queryForModelList(SQL sql, Class<M> klass) {
        return __query_model_list(getHolder(sql), null, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql   sql
     * @param klass 参数
     * @return List
     */
    public <P extends ParameterSerializable, M extends ModelSerializable> List<M> queryForModelList(SQL sql, P params, Class<M> klass) {
        return __query_model_list(getHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(分页)
     *
     * @param sql   sql
     * @param pager 分页信息或null
     * @param klass Class对象
     * @return List
     */
    public <M extends ModelSerializable> Pager queryForPager(SQL sql, Pager pager, Class<M> klass) {
        return __query_pager(getHolder(sql), pager, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql   sql
     * @param klass Class对象
     * @return List
     */
    public <M extends ModelSerializable> Element queryForElement(SQL sql, Class<M> klass) {
        return __query_element(getHolder(sql), null, klass);
    }

    public <P extends ParameterSerializable> Element queryForElement(SQL sql, P params) {
        return __query_element(getHolder(sql), params, null);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public <P extends ParameterSerializable, M extends ModelSerializable> Element queryForElement(SQL sql, P params, Class<M> klass) {
        return __query_element(getHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql   sql
     * @param klass Class对象
     * @return List
     */
    public <M extends ModelSerializable> List<Element> queryForElementList(SQL sql, Class<M> klass) {
        return __query_element_list(getHolder(sql), null, klass);
    }

    public <P extends ParameterSerializable> List<Element> queryForElementList(SQL sql, P params) {
        return __query_element_list(getHolder(sql), params, null);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public <P extends ParameterSerializable, M extends ModelSerializable> List<Element> queryForElementList(SQL sql, P params, Class<M> klass) {
        return __query_element_list(getHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供更新,插入,删除SQL文
     *
     * @param sql sql
     * @return 影响行数
     */
    public Object executeUpdate(SQL sql) {
        return __execute_update(getHolder(sql), null);
    }

    /**
     * 执行由调用者提供更新,插入,删除SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     */
    public <P extends ParameterSerializable> Object executeUpdate(SQL sql, P params) {
        return __execute_update(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供存储过程SQL文
     *
     * @param sql sql
     * @return 影响行数
     */
    public boolean executeCall(SQL sql) {
        return __execute_call(getHolder(sql), null);
    }

    /**
     * 执行由调用者提供存储过程SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     */
    public <P extends ParameterSerializable> boolean executeCall(SQL sql, P params) {
        return __execute_call(getHolder(sql), params);
    }
}
