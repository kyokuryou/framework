package org.smarty.core.support.jdbc;

import org.dom4j.Element;
import org.smarty.core.bean.Pager;
import org.smarty.core.io.ModelSerializable;
import org.smarty.core.support.jdbc.holder.HolderFactory;
import org.smarty.core.support.jdbc.holder.SQLHolder;
import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.support.DBType;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

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
        return __query_number(getHolder(sql)).intValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是int)
     *
     * @param sql    sql
     * @param params 参数
     * @return int
     */
    public int queryForInt(SQL sql, Map<String, Object> params) {
        return __query_number(getHolder(sql), params).intValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是int)
     *
     * @param sql    sql
     * @param params 参数
     * @return int
     */
    public int queryForInt(SQL sql, ModelSerializable params) {
        return __query_number(getHolder(sql), params).intValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是long)
     *
     * @param sql sql
     * @return Long
     */
    public Long queryForLong(SQL sql) {
        return __query_number(getHolder(sql)).longValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是long)
     *
     * @param sql    sql
     * @param params 参数
     * @return Long
     */
    public Long queryForLong(SQL sql, Map<String, Object> params) {
        return __query_number(getHolder(sql), params).longValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是long)
     *
     * @param sql    sql
     * @param params 参数
     * @return Long
     */
    public Long queryForLong(SQL sql, ModelSerializable params) {
        return __query_number(getHolder(sql), params).longValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param sql sql
     * @return Object
     */
    public Object queryForObject(SQL sql) {
        return __query_object(getHolder(sql));
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Object
     */
    public Object queryForObject(SQL sql, Map<String, Object> params) {
        return __query_object(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Object
     */
    public Object queryForObject(SQL sql, ModelSerializable params) {
        return __query_object(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql sql
     * @return Map
     */
    public Map<String, Object> queryForMap(SQL sql) {
        return __query_map(getHolder(sql));
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Map
     */
    public Map<String, Object> queryForMap(SQL sql, Map<String, Object> params) {
        return __query_map(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Map
     */
    public Map<String, Object> queryForMap(SQL sql, ModelSerializable params) {
        return __query_map(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的单行多列数据)
     *
     * @param sql   sql
     * @param klass Class对象
     * @return E
     */
    public <E extends ModelSerializable> E queryForBean(SQL sql, Class<E> klass) {
        return __query_bean(getHolder(sql), klass);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的单行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return E
     */
    public <E extends ModelSerializable> E queryForBean(SQL sql, Map<String, Object> params, Class<E> klass) {
        return __query_bean(getHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的单行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return E
     */
    public <T extends ModelSerializable> T queryForBean(SQL sql, T params) {
        return __query_bean(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(多行单列数据)
     *
     * @param sql sql
     * @return List
     */
    public List<Object> queryForObjectList(SQL sql) {
        return __query_object_list(getHolder(sql));
    }

    /**
     * 执行由调用者提供SQL文(多行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public List<Object> queryForObjectList(SQL sql, Map<String, Object> params) {
        return __query_object_list(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(多行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public List<Object> queryForObjectList(SQL sql, ModelSerializable params) {
        return __query_object_list(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql sql
     * @return List
     */
    public List<Map<String, Object>> queryForMapList(SQL sql) {
        return __query_map_list(getHolder(sql));
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public List<Map<String, Object>> queryForMapList(SQL sql, Map<String, Object> params) {
        return __query_map_list(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public List<Map<String, Object>> queryForMapList(SQL sql, ModelSerializable params) {
        return __query_map_list(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的多行多列数据)
     *
     * @param sql   sql
     * @param klass Class对象
     * @return List
     */
    public <E extends ModelSerializable> List<E> queryForBeanList(SQL sql, Class<E> klass) {
        return __query_bean_list(getHolder(sql), klass);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的多行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return List
     */
    public <E extends ModelSerializable> List<E> queryForBeanList(SQL sql, Map<String, Object> params, Class<E> klass) {
        return __query_bean_list(getHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的多行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public <T extends ModelSerializable> List<T> queryForBeanList(SQL sql, T params) {
        return __query_bean_list(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(分页)
     *
     * @param sql   sql
     * @param pager 分页信息或null
     * @param klass Class对象
     * @return List
     */
    public <E extends ModelSerializable> Pager queryForPager(SQL sql, Pager pager, Class<E> klass) {
        return __query_pager(getHolder(sql), pager, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql   sql
     * @param klass Class对象
     * @return List
     */
    public <E extends ModelSerializable> List<Element> queryForElement(SQL sql, Class<E> klass) {
        return __query_element_list(getHolder(sql), klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return List
     */
    public <E extends ModelSerializable> List<Element> queryForElement(SQL sql, Map<String, Object> params, Class<E> klass) {
        return __query_element_list(getHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     */
    public <T extends ModelSerializable> List<Element> queryForElement(SQL sql, T params) {
        return __query_element_list(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供更新,插入,删除SQL文
     *
     * @param sql sql
     * @return 影响行数
     */
    public Object executeUpdate(SQL sql) {
        return __execute_update(getHolder(sql));
    }

    /**
     * 执行由调用者提供更新,插入,删除SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     */
    public Object executeUpdate(SQL sql, Map<String, Object> params) {
        return __execute_update(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供更新,插入,删除SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     */
    public Object executeUpdate(SQL sql, ModelSerializable params) {
        return __execute_update(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供存储过程SQL文
     *
     * @param sql sql
     * @return 影响行数
     */
    public boolean executeCall(SQL sql) {
        return __execute_call(getHolder(sql));
    }

    /**
     * 执行由调用者提供存储过程SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     */
    public boolean executeCall(SQL sql, Map<String, Object> params) {
        return __execute_call(getHolder(sql), params);
    }

    /**
     * 执行由调用者提供存储过程SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     */
    public boolean executeCall(SQL sql, ModelSerializable params) {
        return __execute_call(getHolder(sql), params);
    }
}
