package org.smarty.core.support.jdbc;

import java.util.List;
import org.dom4j.Element;
import org.smarty.core.bean.Pager;
import org.smarty.core.io.ModelMap;
import org.smarty.core.io.ModelSerializable;
import org.smarty.core.io.ParameterSerializable;
import org.smarty.core.support.jdbc.sql.SQL;

/**
 * 静态SQL接口
 */
public interface IStringSQL {

    // int
    int queryForInt(SQL sql);

    <P extends ParameterSerializable> int queryForInt(SQL sql, P params);

    // long
    long queryForLong(SQL sql);

    <P extends ParameterSerializable> long queryForLong(SQL sql, P params);

    // object
    Object queryForObject(SQL sql);

    <P extends ParameterSerializable> Object queryForObject(SQL sql, P params);

    // object list
    List<Object> queryForObjectList(SQL sql);

    <P extends ParameterSerializable> List<Object> queryForObjectList(SQL sql, P params);

    // model
    ModelMap queryForModel(SQL sql);

    <P extends ParameterSerializable> ModelMap queryForModel(SQL sql, P params);

    <M extends ModelSerializable> M queryForModel(SQL sql, Class<M> klass);

    <P extends ParameterSerializable, M extends ModelSerializable> M queryForModel(SQL sql, P params, Class<M> klass);

    // model list
    List<ModelMap> queryForModelList(SQL sql);

    <P extends ParameterSerializable> List<ModelMap> queryForModelList(SQL sql, P params);

    <M extends ModelSerializable> List<M> queryForModelList(SQL sql, Class<M> klass);

    <P extends ParameterSerializable, M extends ModelSerializable> List<M> queryForModelList(SQL sql, P params, Class<M> klass);

    // pager
    <M extends ModelSerializable> Pager queryForPager(SQL sql, Pager pager, Class<M> klass);

    // element
    <M extends ModelSerializable> Element queryForElement(SQL sql, Class<M> klass);

    <P extends ParameterSerializable> Element queryForElement(SQL sql, P params);

    <P extends ParameterSerializable, M extends ModelSerializable> Element queryForElement(SQL sql, P params, Class<M> klass);

    // element list
    <E extends ModelSerializable> List<Element> queryForElementList(SQL sql, Class<E> klass);

    <P extends ParameterSerializable> List<Element> queryForElementList(SQL sql, P params);

    <P extends ParameterSerializable, M extends ModelSerializable> List<Element> queryForElementList(SQL sql, P params, Class<M> klass);

    // update
    Object executeUpdate(SQL sql);

    <P extends ParameterSerializable> Object executeUpdate(SQL sql, P params);

    // call
    boolean executeCall(SQL sql);

    <P extends ParameterSerializable> boolean executeCall(SQL sql, P params);
}