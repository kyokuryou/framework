package org.smarty.core.support.jdbc;

import org.dom4j.Element;
import org.smarty.core.bean.Pager;
import org.smarty.core.io.ModelSerializable;
import org.smarty.core.support.jdbc.sql.SQL;

import java.util.List;
import java.util.Map;

/**
 * 静态SQL接口
 */
public interface IStringSQL {

    int queryForInt(SQL sql);

    int queryForInt(SQL sql, Map<String, Object> params);

    int queryForInt(SQL sql, ModelSerializable params);

    Long queryForLong(SQL sql);

    Long queryForLong(SQL sql, Map<String, Object> params);

    Long queryForLong(SQL sql, ModelSerializable params);

    Object queryForObject(SQL sql);

    Object queryForObject(SQL sql, Map<String, Object> params);

    Object queryForObject(SQL sql, ModelSerializable params);

    Map<String, Object> queryForMap(SQL sql);

    Map<String, Object> queryForMap(SQL sql, Map<String, Object> params);

    Map<String, Object> queryForMap(SQL sql, ModelSerializable params);

    <E extends ModelSerializable> E queryForBean(SQL sql, Class<E> klass);

    <E extends ModelSerializable> E queryForBean(SQL sql, Map<String, Object> params, Class<E> klass);

    <T extends ModelSerializable> T queryForBean(SQL sql, T params);

    List<Object> queryForObjectList(SQL sql);

    List<Object> queryForObjectList(SQL sql, Map<String, Object> params);

    List<Object> queryForObjectList(SQL sql, ModelSerializable params);

    List<Map<String, Object>> queryForMapList(SQL sql);

    List<Map<String, Object>> queryForMapList(SQL sql, Map<String, Object> params);

    List<Map<String, Object>> queryForMapList(SQL sql, ModelSerializable params);

    <E extends ModelSerializable> List<E> queryForBeanList(SQL sql, Class<E> klass);

    <E extends ModelSerializable> List<E> queryForBeanList(SQL sql, Map<String, Object> params, Class<E> klass);

    <T extends ModelSerializable> List<T> queryForBeanList(SQL sql, T params);

    <E extends ModelSerializable> Pager queryForPager(SQL sql, Pager pager, Class<E> klass);

    <E extends ModelSerializable> List<Element> queryForElement(SQL sql, Class<E> klass);

    <E extends ModelSerializable> List<Element> queryForElement(SQL sql, Map<String, Object> params, Class<E> klass);

    <T extends ModelSerializable> List<Element> queryForElement(SQL sql, T params);

    Object executeUpdate(SQL sql);

    Object executeUpdate(SQL sql, Map<String, Object> params);

    Object executeUpdate(SQL sql, ModelSerializable params);

    boolean executeCall(SQL sql);

    boolean executeCall(SQL sql, Map<String, Object> params);

    boolean executeCall(SQL sql, ModelSerializable params);

}