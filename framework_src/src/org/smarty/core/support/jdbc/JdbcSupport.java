package org.smarty.core.support.jdbc;

import org.dom4j.Element;
import org.smarty.core.Model;
import org.smarty.core.bean.Pager;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.support.jdbc.holder.SQLHolder;
import org.smarty.core.support.jdbc.mapper.BeanMapperHandler;
import org.smarty.core.support.jdbc.mapper.ElementMapperHandler;
import org.smarty.core.support.jdbc.mapper.MapMapperHandler;
import org.smarty.core.support.jdbc.mapper.SingleMapperHandler;
import org.smarty.core.support.jdbc.support.AbstractJdbc;
import org.smarty.core.utils.CommonUtil;
import org.smarty.core.utils.LogicUtil;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * jdbc从文件中读取支持
 */
abstract class JdbcSupport extends AbstractJdbc {
    private static RuntimeLogger logger = new RuntimeLogger(JdbcSupport.class);

    protected abstract SQLHolder getDynamicHolder();

    protected abstract SQLHolder getStaticHolder(String sql);

    /**
     * 查询(返回结果int)
     *
     * @param sqlHolder sql工具
     * @return int 影响行数
     * @throws java.sql.SQLException
     */
    protected Number __query_number(SQLHolder sqlHolder) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        Object ro = queryForSingle(hsql, new MapSqlParameterSource(), new SingleMapperHandler());
        return ro != null ? (Number) ro : 0;
    }

    /**
     * 查询(返回结果int)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return int 影响行数
     * @throws java.sql.SQLException
     */
    protected Number __query_number(SQLHolder sqlHolder, Map<String, Object> params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        Object ro = queryForSingle(hsql, new MapSqlParameterSource(params), new SingleMapperHandler());
        return ro != null ? (Number) ro : 0;
    }

    /**
     * 查询(返回结果Number)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Number
     * @throws java.sql.SQLException
     */
    protected Number __query_number(SQLHolder sqlHolder, Model params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        Object ro = queryForSingle(hsql, new BeanPropertySqlParameterSource(params), new SingleMapperHandler());
        return ro != null ? (Number) ro : 0;
    }

    /**
     * 查询(返回结果Object)
     *
     * @param sqlHolder sql工具
     * @return klass 实例
     * @throws java.sql.SQLException
     */
    protected Object __query_object(SQLHolder sqlHolder) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new MapSqlParameterSource(), new SingleMapperHandler());
    }

    /**
     * 查询(返回结果Object)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return klass 实例
     * @throws java.sql.SQLException
     */
    protected Object __query_object(SQLHolder sqlHolder, Map<String, Object> params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new MapSqlParameterSource(params), new SingleMapperHandler());
    }

    /**
     * 查询(返回结果Object)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return klass 实例
     * @throws java.sql.SQLException
     */
    protected Object __query_object(SQLHolder sqlHolder, Model params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new BeanPropertySqlParameterSource(params), new SingleMapperHandler());
    }

    /**
     * 查询(返回结果bean)
     *
     * @param sqlHolder sql工具
     * @param klass     class
     * @return Model实例
     * @throws java.sql.SQLException
     */
    protected <E extends Model> E __query_bean(SQLHolder sqlHolder, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new MapSqlParameterSource(), new BeanMapperHandler<E>(klass));
    }

    /**
     * 查询(返回结果bean)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @param klass     class
     * @return Model实例
     * @throws java.sql.SQLException
     */
    protected <E extends Model> E __query_bean(SQLHolder sqlHolder, Map<String, Object> params, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new MapSqlParameterSource(params), new BeanMapperHandler<E>(klass));
    }

    /**
     * 查询(返回结果bean)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @param klass     class
     * @return Model实例
     * @throws java.sql.SQLException
     */
    protected <E extends Model> E __query_bean(SQLHolder sqlHolder, Model params, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new BeanPropertySqlParameterSource(params), new BeanMapperHandler<E>(klass));
    }

    /**
     * 查询(返回结果Map)
     *
     * @param sqlHolder sql工具
     * @return Map
     * @throws java.sql.SQLException
     */
    protected Map<String, Object> __query_map(SQLHolder sqlHolder) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new MapSqlParameterSource(), new MapMapperHandler());
    }

    /**
     * 查询(返回结果Map)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Map
     * @throws java.sql.SQLException
     */
    protected Map<String, Object> __query_map(SQLHolder sqlHolder, Map<String, Object> params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new MapSqlParameterSource(params), new MapMapperHandler());
    }

    /**
     * 查询(返回结果Map)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Map
     * @throws java.sql.SQLException
     */
    protected Map<String, Object> __query_map(SQLHolder sqlHolder, Model params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, new BeanPropertySqlParameterSource(params), new MapMapperHandler());
    }

    /**
     * 执行插入,更新,删除操作
     *
     * @param sqlHolder sql工具
     * @return int 影响行数
     * @throws java.sql.SQLException
     */
    protected Integer __execute_update(SQLHolder sqlHolder) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return executeForUpdate(hsql, new MapSqlParameterSource());
    }

    /**
     * 执行插入,更新,删除操作
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return int 影响行数
     * @throws java.sql.SQLException
     */
    protected Integer __execute_update(SQLHolder sqlHolder, Map<String, Object> params) throws SQLException {
        if (LogicUtil.isNotEmptyMap(params) && !params.containsKey("pkId")) {
            params.put("pkId", CommonUtil.getUUID());
        }
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return executeForUpdate(hsql, new MapSqlParameterSource(params));
    }

    /**
     * 执行插入,更新,删除操作
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return int   影响行数
     * @throws java.sql.SQLException
     */
    protected Integer __execute_update(SQLHolder sqlHolder, Model params) throws SQLException {
        if (LogicUtil.isEmpty(params.getPkId())) {
            params.setPkId(CommonUtil.getUUID());
        }
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return executeForUpdate(hsql, new BeanPropertySqlParameterSource(params));
    }

    /**
     * 执行存储过程
     *
     * @param sqlHolder sql工具
     * @return boolean
     */
    protected Boolean __execute_call(SQLHolder sqlHolder) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return executeForCall(hsql, new MapSqlParameterSource());
    }

    /**
     * 执行存储过程
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return boolean
     */
    protected Boolean __execute_call(SQLHolder sqlHolder, Map<String, Object> params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return executeForCall(hsql, new MapSqlParameterSource(params));
    }

    /**
     * 执行存储过程
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return boolean
     */
    protected Boolean __execute_call(SQLHolder sqlHolder, Model params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return executeForCall(hsql, new BeanPropertySqlParameterSource(params));
    }

    /**
     * 分页查询
     *
     * @param sqlHolder sql工具
     * @param pager     page信息
     * @param klass     class
     * @return pager
     * @throws java.sql.SQLException
     */
    protected <E extends Model> Pager __query_pager(SQLHolder sqlHolder, Pager pager, Class<E> klass) throws SQLException {
        Map<String, Object> paramMap = pager.getParams();
        sqlHolder.getSQLString(paramMap);
        // 获得总记录数
        String countSql = sqlHolder.convertCountSQL();
        logger.out(sqlHolder.getSQLType() + ":" + countSql);

        Number totalCount = (Number) queryForSingle(countSql,
                new MapSqlParameterSource(paramMap),
                new SingleMapperHandler()
        );

        pager.setTotalCount(totalCount != null ? totalCount.longValue() : 0);

        // 查询记录 Limit
        String limitSql = sqlHolder.convertLimitSQL(pager);
        logger.out(sqlHolder.getSQLType() + ":" + limitSql);
        List<E> EList = queryForMulti(
                sqlHolder.convertLimitSQL(pager),
                new MapSqlParameterSource(paramMap),
                new BeanMapperHandler<E>(klass)
        );
        return sqlHolder.convertLimitList(pager, EList);
    }

    /**
     * 查询,符合SpringBean标准
     *
     * @param sqlHolder sql工具
     * @param klass     class
     * @return Element集合
     * @throws java.sql.SQLException
     */
    protected <E extends Model> Element __query_element(SQLHolder sqlHolder, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        return queryForSingle(hsql, new MapSqlParameterSource(), new ElementMapperHandler(klass));
    }

    /**
     * 查询,符合SpringBean标准
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @param klass     class
     * @return Element集合
     * @throws java.sql.SQLException
     */
    protected <E extends Model> Element __query_element(SQLHolder sqlHolder, Map<String, Object> params, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        return queryForSingle(hsql, new MapSqlParameterSource(params), new ElementMapperHandler(klass));
    }

    /**
     * 查询
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Element集合
     * @throws java.sql.SQLException
     */
    protected <E extends Model> Element __query_element(SQLHolder sqlHolder, Model params, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        return queryForSingle(hsql, new BeanPropertySqlParameterSource(params), new ElementMapperHandler(klass));
    }

    /**
     * 查询(返回结果Object集合)
     *
     * @param sqlHolder sql工具
     * @return klass 实例
     * @throws java.sql.SQLException
     */
    protected List<Object> __query_object_list(SQLHolder sqlHolder) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new MapSqlParameterSource(), new SingleMapperHandler());
    }

    /**
     * 查询(返回结果Object集合)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return klass 实例
     * @throws java.sql.SQLException
     */
    protected List<Object> __query_object_list(SQLHolder sqlHolder, Map<String, Object> params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new MapSqlParameterSource(params), new SingleMapperHandler());
    }

    /**
     * 查询(返回结果Object集合)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return klass 实例
     * @throws java.sql.SQLException
     */
    protected List<Object> __query_object_list(SQLHolder sqlHolder, Model params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new BeanPropertySqlParameterSource(params), new SingleMapperHandler());
    }

    /**
     * 查询(返回结果bean集合)
     *
     * @param sqlHolder sql工具
     * @param klass     class
     * @return Model实例
     * @throws java.sql.SQLException
     */
    protected <E extends Model> List<E> __query_bean_list(SQLHolder sqlHolder, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new MapSqlParameterSource(), new BeanMapperHandler<E>(klass));
    }

    /**
     * 查询(返回结果bean集合)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @param klass     class
     * @return Model实例
     * @throws java.sql.SQLException
     */
    protected <E extends Model> List<E> __query_bean_list(SQLHolder sqlHolder, Map<String, Object> params, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new MapSqlParameterSource(params), new BeanMapperHandler<E>(klass));
    }

    /**
     * 查询(返回结果bean集合)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @param klass     class
     * @return Model实例
     * @throws java.sql.SQLException
     */
    protected <E extends Model> List<E> __query_bean_list(SQLHolder sqlHolder, Model params, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new BeanPropertySqlParameterSource(params), new BeanMapperHandler<E>(klass));
    }

    /**
     * 查询(返回结果Map集合)
     *
     * @param sqlHolder sql工具
     * @return Map
     * @throws java.sql.SQLException
     */
    protected List<Map<String, Object>> __query_map_list(SQLHolder sqlHolder) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new MapSqlParameterSource(), new MapMapperHandler());
    }

    /**
     * 查询(返回结果Map集合)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Map
     * @throws java.sql.SQLException
     */
    protected List<Map<String, Object>> __query_map_list(SQLHolder sqlHolder, Map<String, Object> params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new MapSqlParameterSource(params), new MapMapperHandler());
    }

    /**
     * 查询(返回结果Map集合)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Map
     * @throws java.sql.SQLException
     */
    protected List<Map<String, Object>> __query_map_list(SQLHolder sqlHolder, Model params) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        logger.out(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, new BeanPropertySqlParameterSource(params), new MapMapperHandler());
    }


    /**
     * 查询(返回结果Element集合),符合SpringBean标准
     *
     * @param sqlHolder sql工具
     * @param klass     class
     * @return Element集合
     * @throws java.sql.SQLException
     */
    protected <E extends Model> List<Element> __query_element_list(SQLHolder sqlHolder, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString();
        return queryForMulti(hsql, new MapSqlParameterSource(), new ElementMapperHandler(klass));
    }

    /**
     * 查询(返回结果Element集合),符合SpringBean标准
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @param klass     class
     * @return Element集合
     * @throws java.sql.SQLException
     */
    protected <E extends Model> List<Element> __query_element_list(SQLHolder sqlHolder, Map<String, Object> params, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        return queryForMulti(hsql, new MapSqlParameterSource(params), new ElementMapperHandler(klass));
    }

    /**
     * 查询(返回结果Element集合),符合SpringBean标准
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Element集合
     * @throws java.sql.SQLException
     */
    protected <E extends Model> List<Element> __query_element_list(SQLHolder sqlHolder, Model params, Class<E> klass) throws SQLException {
        String hsql = sqlHolder.getSQLString(params);
        return queryForMulti(hsql, new BeanPropertySqlParameterSource(params), new ElementMapperHandler(klass));
    }
}
