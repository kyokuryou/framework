package org.smarty.core.support.jdbc;

import org.dom4j.Element;
import org.smarty.core.Model;
import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.holder.HolderFactory;
import org.smarty.core.support.jdbc.holder.SQLHolder;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.support.jdbc.support.SessionClass;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 简单的JDBC支持
 */
public final class SQLSession extends JdbcSupport implements DynamicSQL, StaticSQL, InitializingBean {
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
     * 动态Holder(sql文来自Xml)
     *
     * @return SQLHolder
     */
    protected SQLHolder getDynamicHolder() {
        SessionClass classInfo = SessionClass.getInstance(4);
        return HolderFactory.getHolderInstance(sqlType, classInfo);
    }

    /**
     * 静态Holder(sql文来自程序)
     *
     * @param sql sql
     * @return SQLHolder
     */
    protected SQLHolder getStaticHolder(String sql) {
        return HolderFactory.getHolderInstanceBySQL(sql, sqlType);
    }

    public void afterPropertiesSet() throws Exception {

    }

    // DynamicSQL start

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果是int)
     *
     * @return Integer
     * @throws java.sql.SQLException
     */
    public Integer queryForInt() throws SQLException {
        return __query_number(getDynamicHolder()).intValue();
    }

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果是int)
     *
     * @param params 参数
     * @return Integer
     * @throws java.sql.SQLException
     */
    public Integer queryForInt(Map<String, Object> params) throws SQLException {
        return __query_number(getDynamicHolder(), params).intValue();
    }

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果是int)
     *
     * @param params 参数
     * @return Integer
     * @throws java.sql.SQLException
     */
    public Integer queryForInt(Model params) throws SQLException {
        return __query_number(getDynamicHolder(), params).intValue();
    }

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果是long)
     *
     * @return Long
     * @throws java.sql.SQLException
     */
    public Long queryForLong() throws SQLException {
        return __query_number(getDynamicHolder()).longValue();
    }

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果是long)
     *
     * @param params 参数
     * @return Long
     * @throws java.sql.SQLException
     */
    public Long queryForLong(Map<String, Object> params) throws SQLException {
        return __query_number(getDynamicHolder(), params).longValue();
    }

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果是long)
     *
     * @param params 参数
     * @return Long
     * @throws java.sql.SQLException
     */
    public Long queryForLong(Model params) throws SQLException {
        return __query_number(getDynamicHolder(), params).longValue();
    }

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @return Object
     * @throws java.sql.SQLException
     */
    public Object queryForObject() throws SQLException {
        return __query_object(getDynamicHolder());
    }

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param params 参数
     * @return Object
     * @throws java.sql.SQLException
     */
    public Object queryForObject(Map<String, Object> params) throws SQLException {
        return __query_object(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param params 参数
     * @return Object
     * @throws java.sql.SQLException
     */
    public Object queryForObject(Model params) throws SQLException {
        return __query_object(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行(单行多列数据)
     *
     * @return Map
     * @throws java.sql.SQLException
     */
    public Map<String, Object> queryForMap() throws SQLException {
        return __query_map(getDynamicHolder());
    }

    /**
     * 从文件读取SQL文并执行(单行多列数据)
     *
     * @param params 参数
     * @return Map
     * @throws java.sql.SQLException
     */
    public Map<String, Object> queryForMap(Map<String, Object> params) throws SQLException {
        return __query_map(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行(单行多列数据)
     *
     * @param params 参数
     * @return Map
     * @throws java.sql.SQLException
     */
    public Map<String, Object> queryForMap(Model params) throws SQLException {
        return __query_map(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行(与JavaBean形式一致的单行多列数据)
     *
     * @param klass Class对象
     * @return E
     * @throws java.sql.SQLException
     */
    public <E extends Model> E queryForBean(Class<E> klass) throws SQLException {
        return __query_bean(getDynamicHolder(), klass);
    }

    /**
     * 从文件读取SQL文并执行(与JavaBean形式一致的单行多列数据)
     *
     * @param params 参数
     * @param klass  Class对象
     * @return E
     * @throws java.sql.SQLException
     */
    public <E extends Model> E queryForBean(Map<String, Object> params, Class<E> klass) throws SQLException {
        return __query_bean(getDynamicHolder(), params, klass);
    }

    /**
     * 从文件读取SQL文并执行(与JavaBean形式一致的单行多列数据)
     *
     * @param params 参数
     * @param klass  Class对象
     * @return E
     * @throws java.sql.SQLException
     */
    public <E extends Model> E queryForBean(Model params, Class<E> klass) throws SQLException {
        return __query_bean(getDynamicHolder(), params, klass);
    }

    /**
     * 从文件读取SQL文并执行(多行单列数据)
     *
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Object> queryForObjectList() throws SQLException {
        return __query_object_list(getDynamicHolder());
    }

    /**
     * 从文件读取SQL文并执行(多行单列数据)
     *
     * @param params 参数
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Object> queryForObjectList(Map<String, Object> params) throws SQLException {
        return __query_object_list(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行(多行单列数据)
     *
     * @param params 参数
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Object> queryForObjectList(Model params) throws SQLException {
        return __query_object_list(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行(多行多列数据)
     *
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Map<String, Object>> queryForMapList() throws SQLException {
        return __query_map_list(getDynamicHolder());
    }

    /**
     * 从文件读取SQL文并执行(多行多列数据)
     *
     * @param params 参数
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Map<String, Object>> queryForMapList(Map<String, Object> params) throws SQLException {
        return __query_map_list(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行(多行多列数据)
     *
     * @param params 参数
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Map<String, Object>> queryForMapList(Model params) throws SQLException {
        return __query_map_list(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行(与JavaBean形式一致的多行多列数据)
     *
     * @param klass Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<E> queryForBeanList(Class<E> klass) throws SQLException {
        return __query_bean_list(getDynamicHolder(), klass);
    }

    /**
     * 从文件读取SQL文并执行(与JavaBean形式一致的多行多列数据)
     *
     * @param params 参数
     * @param klass  Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<E> queryForBeanList(Map<String, Object> params, Class<E> klass) throws SQLException {
        return __query_bean_list(getDynamicHolder(), params, klass);
    }

    /**
     * 从文件读取SQL文并执行(与JavaBean形式一致的多行多列数据)
     *
     * @param params 参数
     * @param klass  Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<E> queryForBeanList(Model params, Class<E> klass) throws SQLException {
        return __query_bean_list(getDynamicHolder(), params, klass);
    }

    /**
     * 从文件读取SQL文并执行(分页)
     *
     * @param pager 分页信息或null
     * @param klass Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> Pager queryForPager(Pager pager, Class<E> klass) throws SQLException {
        return __query_pager(getDynamicHolder(), pager, klass);
    }

    /**
     * 从文件读取SQL文并执行(多行多列数据并创建springBean形式的Element)
     *
     * @param klass Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<Element> queryForElement(Class<E> klass) throws SQLException {
        return __query_element_list(getDynamicHolder(), klass);
    }

    /**
     * 从文件读取SQL文并执行(多行多列数据并创建springBean形式的Element)
     *
     * @param params 参数
     * @param klass  Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<Element> queryForElement(Map<String, Object> params, Class<E> klass) throws SQLException {
        return __query_element_list(getDynamicHolder(), params, klass);
    }

    /**
     * 从文件读取SQL文并执行(多行多列数据并创建springBean形式的Element)
     *
     * @param params 参数
     * @param klass  Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<Element> queryForElement(Model params, Class<E> klass) throws SQLException {
        return __query_element_list(getDynamicHolder(), params, klass);
    }

    /**
     * 从文件读取SQL文并执行更新,插入,删除SQL
     *
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Integer executeUpdate() throws SQLException {
        return __execute_update(getDynamicHolder());
    }

    /**
     * 从文件读取SQL文并执行更新,插入,删除SQL
     *
     * @param params 参数
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Integer executeUpdate(Map<String, Object> params) throws SQLException {
        return __execute_update(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行更新,插入,删除SQL
     *
     * @param params 参数
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Integer executeUpdate(Model params) throws SQLException {
        return __execute_update(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行存储过程
     *
     * @return 执行结果
     * @throws java.sql.SQLException
     */
    public Boolean executeCall() throws SQLException {
        return __execute_call(getDynamicHolder());
    }

    /**
     * 从文件读取SQL文并执行存储过程
     *
     * @param params 参数
     * @return 执行结果
     * @throws java.sql.SQLException
     */
    public Boolean executeCall(Map<String, Object> params) throws SQLException {
        return __execute_call(getDynamicHolder(), params);
    }

    /**
     * 从文件读取SQL文并执行存储过程
     *
     * @param params 参数
     * @return 执行结果
     * @throws java.sql.SQLException
     */
    public Boolean executeCall(Model params) throws SQLException {
        return __execute_call(getDynamicHolder(), params);
    }

    // DynamicSQL end

    // StaticSQL start

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是int)
     *
     * @param sql sql
     * @return Integer
     * @throws java.sql.SQLException
     */
    public Integer queryForInt(String sql) throws SQLException {
        return __query_number(getStaticHolder(sql)).intValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是int)
     *
     * @param sql    sql
     * @param params 参数
     * @return Integer
     * @throws java.sql.SQLException
     */
    public Integer queryForInt(String sql, Map<String, Object> params) throws SQLException {
        return __query_number(getStaticHolder(sql), params).intValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是int)
     *
     * @param sql    sql
     * @param params 参数
     * @return Integer
     * @throws java.sql.SQLException
     */
    public Integer queryForInt(String sql, Model params) throws SQLException {
        return __query_number(getStaticHolder(sql), params).intValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是long)
     *
     * @param sql sql
     * @return Long
     * @throws java.sql.SQLException
     */
    public Long queryForLong(String sql) throws SQLException {
        return __query_number(getStaticHolder(sql)).longValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是long)
     *
     * @param sql    sql
     * @param params 参数
     * @return Long
     * @throws java.sql.SQLException
     */
    public Long queryForLong(String sql, Map<String, Object> params) throws SQLException {
        return __query_number(getStaticHolder(sql), params).longValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果是long)
     *
     * @param sql    sql
     * @param params 参数
     * @return Long
     * @throws java.sql.SQLException
     */
    public Long queryForLong(String sql, Model params) throws SQLException {
        return __query_number(getStaticHolder(sql), params).longValue();
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param sql sql
     * @return Object
     * @throws java.sql.SQLException
     */
    public Object queryForObject(String sql) throws SQLException {
        return __query_object(getStaticHolder(sql));
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Object
     * @throws java.sql.SQLException
     */
    public Object queryForObject(String sql, Map<String, Object> params) throws SQLException {
        return __query_object(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(聚合SQL文或SQL文返回结果非数值类型的单行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Object
     * @throws java.sql.SQLException
     */
    public Object queryForObject(String sql, Model params) throws SQLException {
        return __query_object(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql sql
     * @return Map
     * @throws java.sql.SQLException
     */
    public Map<String, Object> queryForMap(String sql) throws SQLException {
        return __query_map(getStaticHolder(sql));
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Map
     * @throws java.sql.SQLException
     */
    public Map<String, Object> queryForMap(String sql, Map<String, Object> params) throws SQLException {
        return __query_map(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(单行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return Map
     * @throws java.sql.SQLException
     */
    public Map<String, Object> queryForMap(String sql, Model params) throws SQLException {
        return __query_map(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的单行多列数据)
     *
     * @param sql   sql
     * @param klass Class对象
     * @return E
     * @throws java.sql.SQLException
     */
    public <E extends Model> E queryForBean(String sql, Class<E> klass) throws SQLException {
        return __query_bean(getStaticHolder(sql), klass);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的单行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return E
     * @throws java.sql.SQLException
     */
    public <E extends Model> E queryForBean(String sql, Map<String, Object> params, Class<E> klass) throws SQLException {
        return __query_bean(getStaticHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的单行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return E
     * @throws java.sql.SQLException
     */
    public <E extends Model> E queryForBean(String sql, Model params, Class<E> klass) throws SQLException {
        return __query_bean(getStaticHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行单列数据)
     *
     * @param sql sql
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Object> queryForObjectList(String sql) throws SQLException {
        return __query_object_list(getStaticHolder(sql));
    }

    /**
     * 执行由调用者提供SQL文(多行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Object> queryForObjectList(String sql, Map<String, Object> params) throws SQLException {
        return __query_object_list(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(多行单列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Object> queryForObjectList(String sql, Model params) throws SQLException {
        return __query_object_list(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql sql
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Map<String, Object>> queryForMapList(String sql) throws SQLException {
        return __query_map_list(getStaticHolder(sql));
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Map<String, Object>> queryForMapList(String sql, Map<String, Object> params) throws SQLException {
        return __query_map_list(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @return List
     * @throws java.sql.SQLException
     */
    public List<Map<String, Object>> queryForMapList(String sql, Model params) throws SQLException {
        return __query_map_list(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的多行多列数据)
     *
     * @param sql   sql
     * @param klass Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<E> queryForBeanList(String sql, Class<E> klass) throws SQLException {
        return __query_bean_list(getStaticHolder(sql), klass);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的多行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<E> queryForBeanList(String sql, Map<String, Object> params, Class<E> klass) throws SQLException {
        return __query_bean_list(getStaticHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(与JavaBean形式一致的多行多列数据)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<E> queryForBeanList(String sql, Model params, Class<E> klass) throws SQLException {
        return __query_bean_list(getStaticHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(分页)
     *
     * @param sql   sql
     * @param pager 分页信息或null
     * @param klass Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> Pager queryForPager(String sql, Pager pager, Class<E> klass) throws SQLException {
        return __query_pager(getStaticHolder(sql), pager, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql   sql
     * @param klass Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<Element> queryForElement(String sql, Class<E> klass) throws SQLException {
        return __query_element_list(getStaticHolder(sql), klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<Element> queryForElement(String sql, Map<String, Object> params, Class<E> klass) throws SQLException {
        return __query_element_list(getStaticHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供SQL文(多行多列数据并创建springBean形式的Element)
     *
     * @param sql    sql
     * @param params 参数
     * @param klass  Class对象
     * @return List
     * @throws java.sql.SQLException
     */
    public <E extends Model> List<Element> queryForElement(String sql, Model params, Class<E> klass) throws SQLException {
        return __query_element_list(getStaticHolder(sql), params, klass);
    }

    /**
     * 执行由调用者提供更新,插入,删除SQL文
     *
     * @param sql sql
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Integer executeUpdate(String sql) throws SQLException {
        return __execute_update(getStaticHolder(sql));
    }

    /**
     * 执行由调用者提供更新,插入,删除SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Integer executeUpdate(String sql, Map<String, Object> params) throws SQLException {
        return __execute_update(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供更新,插入,删除SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Integer executeUpdate(String sql, Model params) throws SQLException {
        return __execute_update(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供存储过程SQL文
     *
     * @param sql sql
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Boolean executeCall(String sql) throws SQLException {
        return __execute_call(getStaticHolder(sql));
    }

    /**
     * 执行由调用者提供存储过程SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Boolean executeCall(String sql, Map<String, Object> params) throws SQLException {
        return __execute_call(getStaticHolder(sql), params);
    }

    /**
     * 执行由调用者提供存储过程SQL文
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     * @throws java.sql.SQLException
     */
    public Boolean executeCall(String sql, Model params) throws SQLException {
        return __execute_call(getStaticHolder(sql), params);
    }
    // StaticSQL end
}
