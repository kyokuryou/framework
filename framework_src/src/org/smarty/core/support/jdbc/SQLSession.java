package org.smarty.core.support.jdbc;

import java.util.List;
import javax.sql.DataSource;
import org.dom4j.Element;
import org.smarty.core.bean.Pager;
import org.smarty.core.io.ModelMap;
import org.smarty.core.io.ModelSerializable;
import org.smarty.core.io.ParameterSerializable;
import org.smarty.core.support.jdbc.holder.SQLHolder;
import org.smarty.core.support.jdbc.mapper.ElementMapperHandler;
import org.smarty.core.support.jdbc.mapper.ModelMapperHandler;
import org.smarty.core.support.jdbc.mapper.RowMapperHandler;
import org.smarty.core.support.jdbc.mapper.SingleMapperHandler;
import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.support.DBType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * 简单的JDBC支持
 */
public final class SQLSession extends JdbcSupport implements InitializingBean {

	public SQLSession() {
	}

	public SQLSession(DataSource dataSource, DBType sqlType) {
		super.setSqlType(sqlType);
		super.setDataSource(dataSource);
	}

	public void afterPropertiesSet() throws Exception {

	}

	//	-----------------
	public Object executeForSingle(SQL sql) {
		return executeForSingle(sql, null, new SingleMapperHandler());
	}

	public <P extends ParameterSerializable> Object executeForSingle(SQL sql, P params) {
		return executeForSingle(sql, params, new SingleMapperHandler());
	}

	public <T> T executeForSingle(SQL sql, Class<T> klass) {
		return executeForSingle(sql, null, new SingleMapperHandler<T>(klass));
	}

	public <P extends ParameterSerializable, T> T executeForSingle(SQL sql, P params, Class<T> klass) {
		return executeForSingle(sql, params, new SingleMapperHandler<T>(klass));
	}

	//	-----------------
	public List<?> executeForSingleList(SQL sql) {
		return executeForMulti(sql, null, new SingleMapperHandler());
	}

	public <P extends ParameterSerializable> List<?> executeForSingleList(SQL sql, P params) {
		return executeForMulti(sql, params, new SingleMapperHandler());
	}

	public <T> List<T> executeForSingleList(SQL sql, Class<T> klass) {
		return executeForMulti(sql, null, new SingleMapperHandler<T>(klass));
	}

	public <P extends ParameterSerializable, T> List<T> executeForSingleList(SQL sql, P params, Class<T> klass) {
		return executeForMulti(sql, params, new SingleMapperHandler<T>(klass));
	}

	//	-----------------
	public ModelMap executeForModel(SQL sql) {
		return executeForSingle(sql, null, new ModelMapperHandler<ModelMap>(ModelMap.class));
	}

	public <P extends ParameterSerializable> ModelMap executeForModel(SQL sql, P params) {
		return executeForSingle(sql, params, new ModelMapperHandler<ModelMap>(ModelMap.class));
	}

	public <M extends ModelSerializable> M executeForModel(SQL sql, Class<M> klass) {
		return executeForSingle(sql, null, new ModelMapperHandler<M>(klass));
	}

	public <P extends ParameterSerializable, M extends ModelSerializable> M executeForModel(SQL sql, P params, Class<M> klass) {
		return executeForSingle(sql, params, new ModelMapperHandler<M>(klass));
	}

	//	-----------------
	public List<ModelMap> executeForModelList(SQL sql) {
		return executeForMulti(sql, null, new ModelMapperHandler<ModelMap>(ModelMap.class));
	}

	public <P extends ParameterSerializable> List<ModelMap> executeForModelList(SQL sql, P params) {
		return executeForMulti(sql, params, new ModelMapperHandler<ModelMap>(ModelMap.class));
	}

	public <M extends ModelSerializable> List<M> executeForModelList(SQL sql, Class<M> klass) {
		return executeForMulti(sql, null, new ModelMapperHandler<M>(klass));
	}

	public <P extends ParameterSerializable, M extends ModelSerializable> List<M> executeForModelList(SQL sql, P params, Class<M> klass) {
		return executeForMulti(sql, params, new ModelMapperHandler<M>(klass));
	}

	//	-----------------
	private <P extends ParameterSerializable> Element executeForElement(SQL sql, P params) {
		return executeForSingle(sql, params, new ElementMapperHandler(ModelMap.class));
	}

	private <P extends ParameterSerializable> List<Element> executeForElementList(SQL sql, P params) {
		return executeForMulti(sql, params, new ElementMapperHandler(ModelMap.class));
	}

	private <P extends ParameterSerializable, M extends ModelSerializable> Element executeForElement(SQL sql, P params, Class<M> klass) {
		return executeForSingle(sql, params, new ElementMapperHandler(klass));
	}

	private <P extends ParameterSerializable, M extends ModelSerializable> List<Element> executeForElementList(SQL sql, P params, Class<M> klass) {
		return executeForMulti(sql, params, new ElementMapperHandler(klass));
	}

	public <M extends ModelSerializable> Pager executeForPager(SQL sql, Pager pager, Class<M> klass) {
		SqlParameterSource sps = new MapSqlParameterSource(pager.getParams());
		RowMapperHandler<?> rmh = (klass == null) ? new ModelMapperHandler<ModelMap>(ModelMap.class) : new ModelMapperHandler<M>(klass);
		SQLHolder sqlHolder = getHolder(sql);
		// 获得总记录数
		String countSql = sqlHolder.convertCountSQL();
		logger.debug(sqlHolder.getSQLType() + ":" + countSql);
		Number count = queryForSingle(countSql, sps, new SingleMapperHandler<Number>());
		// 查询记录 Limit
		String limitSql = sqlHolder.convertLimitSQL(pager, count != null ? count.intValue() : 0);
		logger.debug(sqlHolder.getSQLType() + ":" + limitSql);
		List<?> list = queryForMulti(limitSql, sps, rmh);
		return sqlHolder.convertLimitList(pager, list);
	}
}
