package org.core.support.jdbc;

import org.core.Model;
import org.core.bean.Pager;
import org.dom4j.Element;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 静态SQL接口
 */
public interface StaticSQL {

    public Integer queryForInt(String sql) throws SQLException;

    public Integer queryForInt(String sql, Map<String, Object> params) throws SQLException;

    public Integer queryForInt(String sql, Model params) throws SQLException;

    public Long queryForLong(String sql) throws SQLException;

    public Long queryForLong(String sql, Map<String, Object> params) throws SQLException;

    public Long queryForLong(String sql, Model params) throws SQLException;

    public Object queryForObject(String sql) throws SQLException;

    public Object queryForObject(String sql, Map<String, Object> params) throws SQLException;

    public Object queryForObject(String sql, Model params) throws SQLException;

    public Map<String, Object> queryForMap(String sql) throws SQLException;

    public Map<String, Object> queryForMap(String sql, Map<String, Object> params) throws SQLException;

    public Map<String, Object> queryForMap(String sql, Model params) throws SQLException;

    public <E extends Model> E queryForBean(String sql, Class<E> klass) throws SQLException;

    public <E extends Model> E queryForBean(String sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    public <E extends Model> E queryForBean(String sql, Model params, Class<E> klass) throws SQLException;

    public List<Object> queryForObjectList(String sql) throws SQLException;

    public List<Object> queryForObjectList(String sql, Map<String, Object> params) throws SQLException;

    public List<Object> queryForObjectList(String sql, Model params) throws SQLException;

    public List<Map<String, Object>> queryForMapList(String sql) throws SQLException;

    public List<Map<String, Object>> queryForMapList(String sql, Map<String, Object> params) throws SQLException;

    public List<Map<String, Object>> queryForMapList(String sql, Model params) throws SQLException;

    public <E extends Model> List<E> queryForBeanList(String sql, Class<E> klass) throws SQLException;

    public <E extends Model> List<E> queryForBeanList(String sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    public <E extends Model> List<E> queryForBeanList(String sql, Model params, Class<E> klass) throws SQLException;

    public <E extends Model> Pager queryForPager(String sql, Pager pager, Class<E> klass) throws SQLException;

    public <E extends Model> List<Element> queryForElement(String sql, Class<E> klass) throws SQLException;

    public <E extends Model> List<Element> queryForElement(String sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    public <E extends Model> List<Element> queryForElement(String sql, Model params, Class<E> klass) throws SQLException;

    public Integer executeUpdate(String sql) throws SQLException;

    public Integer executeUpdate(String sql, Map<String, Object> params) throws SQLException;

    public Integer executeUpdate(String sql, Model params) throws SQLException;

    public Boolean executeCall(String sql) throws SQLException;

    public Boolean executeCall(String sql, Map<String, Object> params) throws SQLException;

    public Boolean executeCall(String sql, Model params) throws SQLException;

}