package org.smarty.core.support.jdbc;

import org.dom4j.Element;
import org.smarty.core.Model;
import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.sql.SQL;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 静态SQL接口
 */
public interface IStringSQL {

    Integer queryForInt(SQL sql) throws SQLException;

    Integer queryForInt(SQL sql, Map<String, Object> params) throws SQLException;

    Integer queryForInt(SQL sql, Model params) throws SQLException;

    Long queryForLong(SQL sql) throws SQLException;

    Long queryForLong(SQL sql, Map<String, Object> params) throws SQLException;

    Long queryForLong(SQL sql, Model params) throws SQLException;

    Object queryForObject(SQL sql) throws SQLException;

    Object queryForObject(SQL sql, Map<String, Object> params) throws SQLException;

    Object queryForObject(SQL sql, Model params) throws SQLException;

    Map<String, Object> queryForMap(SQL sql) throws SQLException;

    Map<String, Object> queryForMap(SQL sql, Map<String, Object> params) throws SQLException;

    Map<String, Object> queryForMap(SQL sql, Model params) throws SQLException;

    <E extends Model> E queryForBean(SQL sql, Class<E> klass) throws SQLException;

    <E extends Model> E queryForBean(SQL sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <E extends Model> E queryForBean(SQL sql, Model params, Class<E> klass) throws SQLException;

    List<Object> queryForObjectList(SQL sql) throws SQLException;

    List<Object> queryForObjectList(SQL sql, Map<String, Object> params) throws SQLException;

    List<Object> queryForObjectList(SQL sql, Model params) throws SQLException;

    List<Map<String, Object>> queryForMapList(SQL sql) throws SQLException;

    List<Map<String, Object>> queryForMapList(SQL sql, Map<String, Object> params) throws SQLException;

    List<Map<String, Object>> queryForMapList(SQL sql, Model params) throws SQLException;

    <E extends Model> List<E> queryForBeanList(SQL sql, Class<E> klass) throws SQLException;

    <E extends Model> List<E> queryForBeanList(SQL sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <E extends Model> List<E> queryForBeanList(SQL sql, Model params, Class<E> klass) throws SQLException;

    <E extends Model> Pager queryForPager(SQL sql, Pager pager, Class<E> klass) throws SQLException;

    <E extends Model> List<Element> queryForElement(SQL sql, Class<E> klass) throws SQLException;

    <E extends Model> List<Element> queryForElement(SQL sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <E extends Model> List<Element> queryForElement(SQL sql, Model params, Class<E> klass) throws SQLException;

    Integer executeUpdate(SQL sql) throws SQLException;

    Integer executeUpdate(SQL sql, Map<String, Object> params) throws SQLException;

    Integer executeUpdate(SQL sql, Model params) throws SQLException;

    Boolean executeCall(SQL sql) throws SQLException;

    Boolean executeCall(SQL sql, Map<String, Object> params) throws SQLException;

    Boolean executeCall(SQL sql, Model params) throws SQLException;

}