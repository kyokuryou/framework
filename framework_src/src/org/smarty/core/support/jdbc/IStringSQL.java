package org.smarty.core.support.jdbc;

import org.dom4j.Element;
import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.parameter.ModelSerializable;
import org.smarty.core.support.jdbc.sql.SQL;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 静态SQL接口
 */
public interface IStringSQL {

    int queryForInt(SQL sql) throws SQLException;

    int queryForInt(SQL sql, Map<String, Object> params) throws SQLException;

    int queryForInt(SQL sql, ModelSerializable params) throws SQLException;

    Long queryForLong(SQL sql) throws SQLException;

    Long queryForLong(SQL sql, Map<String, Object> params) throws SQLException;

    Long queryForLong(SQL sql, ModelSerializable params) throws SQLException;

    Object queryForObject(SQL sql) throws SQLException;

    Object queryForObject(SQL sql, Map<String, Object> params) throws SQLException;

    Object queryForObject(SQL sql, ModelSerializable params) throws SQLException;

    Map<String, Object> queryForMap(SQL sql) throws SQLException;

    Map<String, Object> queryForMap(SQL sql, Map<String, Object> params) throws SQLException;

    Map<String, Object> queryForMap(SQL sql, ModelSerializable params) throws SQLException;

    <E extends ModelSerializable> E queryForBean(SQL sql, Class<E> klass) throws SQLException;

    <E extends ModelSerializable> E queryForBean(SQL sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <T extends ModelSerializable> T queryForBean(SQL sql, T params) throws SQLException;

    List<Object> queryForObjectList(SQL sql) throws SQLException;

    List<Object> queryForObjectList(SQL sql, Map<String, Object> params) throws SQLException;

    List<Object> queryForObjectList(SQL sql, ModelSerializable params) throws SQLException;

    List<Map<String, Object>> queryForMapList(SQL sql) throws SQLException;

    List<Map<String, Object>> queryForMapList(SQL sql, Map<String, Object> params) throws SQLException;

    List<Map<String, Object>> queryForMapList(SQL sql, ModelSerializable params) throws SQLException;

    <E extends ModelSerializable> List<E> queryForBeanList(SQL sql, Class<E> klass) throws SQLException;

    <E extends ModelSerializable> List<E> queryForBeanList(SQL sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <T extends ModelSerializable> List<T> queryForBeanList(SQL sql, T params) throws SQLException;

    <E extends ModelSerializable> Pager queryForPager(SQL sql, Pager pager, Class<E> klass) throws SQLException;

    <E extends ModelSerializable> List<Element> queryForElement(SQL sql, Class<E> klass) throws SQLException;

    <E extends ModelSerializable> List<Element> queryForElement(SQL sql, Map<String, Object> params, Class<E> klass) throws SQLException;

    <T extends ModelSerializable> List<Element> queryForElement(SQL sql, T params) throws SQLException;

    Object executeUpdate(SQL sql) throws SQLException;

    Object executeUpdate(SQL sql, Map<String, Object> params) throws SQLException;

    Object executeUpdate(SQL sql, ModelSerializable params) throws SQLException;

    boolean executeCall(SQL sql) throws SQLException;

    boolean executeCall(SQL sql, Map<String, Object> params) throws SQLException;

    boolean executeCall(SQL sql, ModelSerializable params) throws SQLException;

}