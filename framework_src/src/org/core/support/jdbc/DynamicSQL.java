package org.core.support.jdbc;

import org.core.Model;
import org.core.bean.Pager;
import org.dom4j.Element;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 动态SQL接口
 */
public interface DynamicSQL {

    public Integer queryForInt() throws SQLException;

    public Integer queryForInt(Map<String, Object> params) throws SQLException;

    public Integer queryForInt(Model params) throws SQLException;

    public Long queryForLong() throws SQLException;

    public Long queryForLong(Map<String, Object> params) throws SQLException;

    public Long queryForLong(Model params) throws SQLException;

    public Object queryForObject() throws SQLException;

    public Object queryForObject(Map<String, Object> params) throws SQLException;

    public Object queryForObject(Model params) throws SQLException;

    public Map<String, Object> queryForMap() throws SQLException;

    public Map<String, Object> queryForMap(Map<String, Object> params) throws SQLException;

    public Map<String, Object> queryForMap(Model params) throws SQLException;

    public <E extends Model> E queryForBean(Class<E> klass) throws SQLException;

    public <E extends Model> E queryForBean(Map<String, Object> params, Class<E> klass) throws SQLException;

    public <E extends Model> E queryForBean(Model params, Class<E> klass) throws SQLException;

    public List<Object> queryForObjectList() throws SQLException;

    public List<Object> queryForObjectList(Map<String, Object> params) throws SQLException;

    public List<Object> queryForObjectList(Model params) throws SQLException;

    public List<Map<String, Object>> queryForMapList() throws SQLException;

    public List<Map<String, Object>> queryForMapList(Map<String, Object> params) throws SQLException;

    public List<Map<String, Object>> queryForMapList(Model params) throws SQLException;

    public <E extends Model> List<E> queryForBeanList(Class<E> klass) throws SQLException;

    public <E extends Model> List<E> queryForBeanList(Map<String, Object> params, Class<E> klass) throws SQLException;

    public <E extends Model> List<E> queryForBeanList(Model params, Class<E> klass) throws SQLException;

    public <E extends Model> Pager queryForPager(Pager pager, Class<E> klass) throws SQLException;

    public <E extends Model> List<Element> queryForElement(Class<E> klass) throws SQLException;

    public <E extends Model> List<Element> queryForElement(Map<String, Object> params, Class<E> klass) throws SQLException;

    public <E extends Model> List<Element> queryForElement(Model params, Class<E> klass) throws SQLException;

    public Integer executeUpdate() throws SQLException;

    public Integer executeUpdate(Map<String, Object> params) throws SQLException;

    public Integer executeUpdate(Model params) throws SQLException;

    public Boolean executeCall() throws SQLException;

    public Boolean executeCall(Map<String, Object> params) throws SQLException;

    public Boolean executeCall(Model params) throws SQLException;

}
