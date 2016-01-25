package org.smarty.core.support.jdbc;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.smarty.core.bean.Pager;
import org.smarty.core.io.ModelMap;
import org.smarty.core.io.ModelSerializable;
import org.smarty.core.io.ParameterMap;
import org.smarty.core.io.ParameterSerializable;
import org.smarty.core.support.jdbc.holder.SQLHolder;
import org.smarty.core.support.jdbc.mapper.ElementMapperHandler;
import org.smarty.core.support.jdbc.mapper.ModelMapperHandler;
import org.smarty.core.support.jdbc.mapper.SingleMapperHandler;
import org.smarty.core.support.jdbc.support.AbstractJdbc;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * jdbc从文件中读取支持
 */
abstract class JdbcSupport extends AbstractJdbc {
    private static Log logger = LogFactory.getLog(JdbcSupport.class);

    protected <E extends ParameterSerializable> SqlParameterSource getParameterSource(E params) {
        if (params == null) {
            return new MapSqlParameterSource();
        }
        if (params instanceof ParameterMap) {
            return new MapSqlParameterSource((ParameterMap) params);
        } else {
            return new BeanPropertySqlParameterSource(params);
        }
    }

    protected <T extends ModelSerializable> Class<?> getSuperClass(Class<T> klass) {
        if (klass != null) {
            return klass;
        } else {
            return ModelMap.class;
        }
    }

    /**
     * 查询(返回结果Object)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return klass 实例
     */
    protected <P extends ParameterSerializable> Object __query_object(SQLHolder sqlHolder, P params) {
        String hsql = sqlHolder.getSQLString(params);
        logger.debug(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, getParameterSource(params), new SingleMapperHandler());
    }

    /**
     * 查询(返回结果Object集合)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return klass 实例
     */
    protected <P extends ParameterSerializable> List<Object> __query_object_list(SQLHolder sqlHolder, P params) {
        String hsql = sqlHolder.getSQLString(params);
        logger.debug(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, getParameterSource(params), new SingleMapperHandler());
    }

    /**
     * 查询(返回结果bean)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Model实例
     */
    protected <P extends ParameterSerializable, M extends ModelSerializable> M __query_model(SQLHolder sqlHolder, P params, Class<M> klass) {
        String hsql = sqlHolder.getSQLString(params);
        logger.debug(sqlHolder.getSQLType() + ":" + hsql);
        return queryForSingle(hsql, getParameterSource(params), new ModelMapperHandler<M>(getSuperClass(klass)));
    }

    /**
     * 查询(返回结果bean集合)
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Model实例
     */
    protected <P extends ParameterSerializable, M extends ModelSerializable> List<M> __query_model_list(SQLHolder sqlHolder, P params, Class<M> klass) {
        String hsql = sqlHolder.getSQLString(params);
        logger.debug(sqlHolder.getSQLType() + ":" + hsql);
        return queryForMulti(hsql, getParameterSource(params), new ModelMapperHandler<M>(getSuperClass(klass)));
    }

    /**
     * 执行插入,更新,删除操作
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return int   影响行数
     */
    protected <P extends ParameterSerializable> Object __execute_update(SQLHolder sqlHolder, P params) {
        String hsql = sqlHolder.getSQLString(params);
        logger.debug(sqlHolder.getSQLType() + ":" + hsql);
        return executeForUpdate(hsql, getParameterSource(params));
    }

    /**
     * 执行存储过程
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return boolean
     */
    protected <P extends ParameterSerializable> boolean __execute_call(SQLHolder sqlHolder, P params) {
        String hsql = sqlHolder.getSQLString(params);
        logger.debug(sqlHolder.getSQLType() + ":" + hsql);
        return executeForCall(hsql, getParameterSource(params));
    }

    /**
     * 分页查询
     *
     * @param sqlHolder sql工具
     * @param pager     page信息
     * @param klass     class
     * @return pager
     */
    protected <M extends ModelSerializable> Pager __query_pager(SQLHolder sqlHolder, Pager pager, Class<M> klass) {
        ParameterMap paramMap = pager.getParams();
        // 获得总记录数
        String countSql = sqlHolder.convertCountSQL();
        logger.debug(sqlHolder.getSQLType() + ":" + countSql);

        Number totalCount = (Number) queryForSingle(countSql, new MapSqlParameterSource(paramMap), new SingleMapperHandler());

        pager.setTotalCount(totalCount != null ? totalCount.intValue() : 0);

        // 查询记录 Limit
        String limitSql = sqlHolder.convertLimitSQL(pager);
        logger.debug(sqlHolder.getSQLType() + ":" + limitSql);
        List<M> list = queryForMulti(sqlHolder.convertLimitSQL(pager), new MapSqlParameterSource(paramMap), new ModelMapperHandler<M>(klass));
        return sqlHolder.convertLimitList(pager, list);
    }

    /**
     * 查询
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Element集合
     */
    protected <P extends ParameterSerializable, M extends ModelSerializable> Element __query_element(SQLHolder sqlHolder, P params, Class<M> klass) {
        String hsql = sqlHolder.getSQLString(params);
        return queryForSingle(hsql, getParameterSource(params), new ElementMapperHandler(getSuperClass(klass)));
    }

    /**
     * 查询(返回结果Element集合),符合SpringBean标准
     *
     * @param sqlHolder sql工具
     * @param params    参数
     * @return Element集合
     */
    protected <P extends ParameterSerializable, M extends ModelSerializable> List<Element> __query_element_list(SQLHolder sqlHolder, P params, Class<M> klass) {
        String hsql = sqlHolder.getSQLString(params);
        return queryForMulti(hsql, getParameterSource(params), new ElementMapperHandler(getSuperClass(klass)));
    }
}
