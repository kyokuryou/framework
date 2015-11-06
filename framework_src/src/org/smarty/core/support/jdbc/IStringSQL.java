package org.smarty.core.support.jdbc;

import org.dom4j.Element;
import org.smarty.core.Model;
import org.smarty.core.bean.Pager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 静态SQL接口
 */
public interface IStringSQL {

    Integer queryForInt(String sql) throws SQLException;

    Integer queryForInt(String sql, Map<String, Object> params) throws SQLException;

    Integer queryForInt(String sql, Model params) throws SQLException;

    Long queryForLong(String sql) throws SQLException;

    Long queryForLong(String sql, Map<String, Object> params) throws SQLException;

    Long queryForLong(String sql, Model params) throws SQLException;

    Object queryForObject(String sql) throws SQLException;

    Object queryForObject(String sql, Map<String, Object> params) throws SQLException;

    Object queryForObject(String sql, Model params) throws SQLException;

    Map<String, Object> queryForMap(String sql) throws SQLException;

    Map<String, Object> queryForMap(String sql, Map<String, Object> params) throws SQLException;

    Map<String, Object> queryForMap(String sql, Model params) throws SQLException;

    <E extends Model> E queryForBean(String sql, Class<E> klass) throws SQLException;

    <E extends Model> E queryForBean(String sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <E extends Model> E queryForBean(String sql, Model params, Class<E> klass) throws SQLException;

    List<Object> queryForObjectList(String sql) throws SQLException;

    List<Object> queryForObjectList(String sql, Map<String, Object> params) throws SQLException;

    List<Object> queryForObjectList(String sql, Model params) throws SQLException;

    List<Map<String, Object>> queryForMapList(String sql) throws SQLException;

    List<Map<String, Object>> queryForMapList(String sql, Map<String, Object> params) throws SQLException;

    List<Map<String, Object>> queryForMapList(String sql, Model params) throws SQLException;

    <E extends Model> List<E> queryForBeanList(String sql, Class<E> klass) throws SQLException;

    <E extends Model> List<E> queryForBeanList(String sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <E extends Model> List<E> queryForBeanList(String sql, Model params, Class<E> klass) throws SQLException;

    <E extends Model> Pager queryForPager(String sql, Pager pager, Class<E> klass) throws SQLException;

    <E extends Model> List<Element> queryForElement(String sql, Class<E> klass) throws SQLException;

    <E extends Model> List<Element> queryForElement(String sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <E extends Model> List<Element> queryForElement(String sql, Model params, Class<E> klass) throws SQLException;

    Integer executeUpdate(String sql) throws SQLException;

    Integer executeUpdate(String sql, Map<String, Object> params) throws SQLException;

    Integer executeUpdate(String sql, Model params) throws SQLException;

    Boolean executeCall(String sql) throws SQLException;

    Boolean executeCall(String sql, Map<String, Object> params) throws SQLException;

    Boolean executeCall(String sql, Model params) throws SQLException;

}