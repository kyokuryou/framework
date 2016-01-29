package org.smarty.core.support.jdbc;

import java.util.List;
import org.smarty.core.io.ParameterMap;
import org.smarty.core.io.ParameterSerializable;
import org.smarty.core.support.jdbc.holder.HolderFactory;
import org.smarty.core.support.jdbc.holder.SQLHolder;
import org.smarty.core.support.jdbc.mapper.RowMapperHandler;
import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.sql.StatementType;
import org.smarty.core.support.jdbc.support.AbstractJdbc;
import org.smarty.core.support.jdbc.support.DBType;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * jdbc从文件中读取支持
 */
abstract class JdbcSupport extends AbstractJdbc {
	private DBType sqlType;

	public void setSqlType(DBType sqlType) {
		this.sqlType = sqlType;
	}

	protected SQLHolder getHolder(SQL sql) {
		return HolderFactory.getHolder(sql, sqlType);
	}

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

	protected <P extends ParameterSerializable> boolean execute(SQL sql, P params) {
		StatementType type = sql.getStatementType();
		SQLHolder holder = getHolder(sql);
		String hsql = holder.getSQLString(params);
		SqlParameterSource sps = getParameterSource(params);
		if (StatementType.INSERT == type || StatementType.UPDATE == type || StatementType.DELETE == type) {
			return update(hsql, sps);
		}
		if (StatementType.CALL == type) {
			return call(hsql, sps);
		} else {
			throw new UnsupportedOperationException("does not support the SELECT operation");
		}
	}

	protected <P extends ParameterSerializable, T> T executeForSingle(SQL sql, P params, RowMapperHandler<T> rmh) {
		StatementType type = sql.getStatementType();
		SQLHolder holder = getHolder(sql);
		String hsql = holder.getSQLString(params);
		SqlParameterSource sps = getParameterSource(params);
		if (StatementType.SELECT == type) {
			return queryForSingle(hsql, sps, rmh);
		}
		if (StatementType.INSERT == type) {
			return update(hsql, sps, rmh);
		}
		if (StatementType.CALL == type) {
			return callForSingle(hsql, sps, rmh);
		} else {
			throw new UnsupportedOperationException("does not support the UPDATE or DELETE operation");
		}
	}

	protected <P extends ParameterSerializable, T> List<T> executeForMulti(SQL sql, P params, RowMapperHandler<T> rmh) {
		StatementType type = sql.getStatementType();
		SQLHolder holder = getHolder(sql);
		String hsql = holder.getSQLString(params);
		SqlParameterSource sps = getParameterSource(params);
		if (StatementType.SELECT == type) {
			return queryForMulti(hsql, sps, rmh);
		}
		if (StatementType.CALL == type) {
			return callForMulti(hsql, sps, rmh);
		} else {
			throw new UnsupportedOperationException("does not support the INSERT or UPDATE or DELETE operation");
		}
	}
}
