package org.smarty.core.support.jdbc.support;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.support.jdbc.mapper.RowMapperHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.CallableStatementCreatorFactory;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * JDBC扩展
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class AbstractJdbc {
	protected final Log logger = LogFactory.getLog(getClass());
	protected final JdbcTemplate jdbcLocal = new JdbcTemplate();
	private Transaction transaction;

	protected AbstractJdbc() {
		jdbcLocal.setQueryTimeout(1200);
	}

	public void setDataSource(DataSource dataSource) {
		jdbcLocal.setDataSource(dataSource);
	}

	protected DataSource getDataSource() {
		return jdbcLocal.getDataSource();
	}

	/**
	 * 查询单行数据
	 *
	 * @param sql sql
	 * @param sps sqlParameterSource
	 *            MapSqlParameterSource (org.springframework.jdbc.core.namedparam)
	 *            BeanPropertySqlParameterSource (org.springframework.jdbc.core.namedparam)
	 * @param rmh rowMapperHandler
	 *            BeanMapperHandler (org.jdbc.mapper)
	 *            SingleMapperHandler (org.jdbc.mapper)
	 *            MapMapperHandler (org.jdbc.mapper)
	 * @param <T> 映射类型
	 * @return T
	 */
	protected final <T> T queryForSingle(final String sql, final SqlParameterSource sps, final RowMapperHandler<T> rmh) {
		return jdbcLocal.execute(new ConnectionCallback<T>() {
			public T doInConnection(Connection connection) throws SQLException, DataAccessException {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = getStatementCreator(sql, sps, false).createPreparedStatement(connection);
					rs = ps.executeQuery();
					return processSingleRow(rs, rmh);
				} finally {
					JdbcUtils.closeResultSet(rs);
					JdbcUtils.closeStatement(ps);
				}
			}
		});
	}

	/**
	 * 查询数据集合
	 *
	 * @param sql sql
	 * @param sps sqlParameterSource
	 *            MapSqlParameterSource (org.springframework.jdbc.core.namedparam)
	 *            BeanPropertySqlParameterSource (org.springframework.jdbc.core.namedparam)
	 * @param rmh rowMapperHandler
	 *            BeanMapperHandler (org.jdbc.mapper)
	 *            SingleMapperHandler (org.jdbc.mapper)
	 *            MapMapperHandler (org.jdbc.mapper)
	 * @param <T> 映射类型
	 * @return List<T>
	 */
	protected final <T> List<T> queryForMulti(final String sql, final SqlParameterSource sps, final RowMapperHandler<T> rmh) {
		return jdbcLocal.execute(new ConnectionCallback<List<T>>() {
			public List<T> doInConnection(Connection connection) throws SQLException, DataAccessException {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = getStatementCreator(sql, sps, false).createPreparedStatement(connection);
					rs = ps.executeQuery();
					return processMultipleRow(rs, rmh);
				} finally {
					JdbcUtils.closeResultSet(rs);
					JdbcUtils.closeStatement(ps);
				}
			}
		});
	}

	/**
	 * 执行更新/增加/删除操作
	 *
	 * @param sql sql
	 * @param sps sqlParameterSource
	 *            MapSqlParameterSource (org.springframework.jdbc.core.namedparam)
	 *            BeanPropertySqlParameterSource (org.springframework.jdbc.core.namedparam)
	 * @return 执行结果
	 */
	protected final boolean update(final String sql, final SqlParameterSource sps) {
		return jdbcLocal.execute(new ConnectionCallback<Boolean>() {
			public Boolean doInConnection(Connection connection) throws SQLException, DataAccessException {
				PreparedStatement ps = null;
				try {
					ps = getStatementCreator(sql, sps, false).createPreparedStatement(connection);
					return ps.executeUpdate() > 0;
				} finally {
					JdbcUtils.closeStatement(ps);
				}
			}
		});
	}

	protected final <T> T update(final String sql, final SqlParameterSource sps, final RowMapperHandler<T> rmh) {
		return jdbcLocal.execute(new ConnectionCallback<T>() {
			public T doInConnection(Connection connection) throws SQLException, DataAccessException {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = getStatementCreator(sql, sps, true).createPreparedStatement(connection);
					if (ps.executeUpdate() == 0) {
						return null;
					}
					rs = ps.getGeneratedKeys();
					return processSingleRow(rs, rmh);
				} finally {
					JdbcUtils.closeResultSet(rs);
					JdbcUtils.closeStatement(ps);
				}
			}
		});
	}

	/**
	 * 执行存储过程
	 *
	 * @param sql sql
	 * @param sps sqlParameterSource
	 *            MapSqlParameterSource (org.springframework.jdbc.core.namedparam)
	 *            BeanPropertySqlParameterSource (org.springframework.jdbc.core.namedparam)
	 * @return 执行结果
	 */
	protected final <T> T callForSingle(final String sql, final SqlParameterSource sps, final RowMapperHandler<T> rmh) {
		return jdbcLocal.execute(new ConnectionCallback<T>() {
			public T doInConnection(Connection connection) throws SQLException, DataAccessException {
				CallableStatement ps = null;
				ResultSet rs = null;
				try {
					ps = getCallableStatementCreator(sql, sps).createCallableStatement(connection);
					if (!ps.execute()) {
						return null;
					}
					rs = ps.getResultSet();
					return processSingleRow(rs, rmh);
				} finally {
					JdbcUtils.closeResultSet(rs);
					JdbcUtils.closeStatement(ps);
				}
			}
		});
	}

	/**
	 * 执行存储过程
	 *
	 * @param sql sql
	 * @param sps sqlParameterSource
	 *            MapSqlParameterSource (org.springframework.jdbc.core.namedparam)
	 *            BeanPropertySqlParameterSource (org.springframework.jdbc.core.namedparam)
	 * @return 执行结果
	 */
	protected final <T> List<T> callForMulti(final String sql, final SqlParameterSource sps, final RowMapperHandler<T> rmh) {
		return jdbcLocal.execute(new ConnectionCallback<List<T>>() {
			public List<T> doInConnection(Connection connection) throws SQLException, DataAccessException {
				CallableStatement ps = null;
				ResultSet rs = null;
				try {
					ps = getCallableStatementCreator(sql, sps).createCallableStatement(connection);
					if (!ps.execute()) {
						return null;
					}
					rs = ps.getResultSet();
					return processMultipleRow(rs, rmh);
				} finally {
					JdbcUtils.closeResultSet(rs);
					JdbcUtils.closeStatement(ps);
				}
			}
		});
	}

	/**
	 * 执行存储过程
	 *
	 * @param sql sql
	 * @param sps sqlParameterSource
	 *            MapSqlParameterSource (org.springframework.jdbc.core.namedparam)
	 *            BeanPropertySqlParameterSource (org.springframework.jdbc.core.namedparam)
	 * @return 执行结果
	 */
	protected final boolean call(final String sql, final SqlParameterSource sps) {
		return jdbcLocal.execute(new ConnectionCallback<Boolean>() {
			public Boolean doInConnection(Connection connection) throws SQLException, DataAccessException {
				CallableStatement ps = null;
				try {
					ps = getCallableStatementCreator(sql, sps).createCallableStatement(connection);
					return ps.execute();
				} finally {
					JdbcUtils.closeStatement(ps);
				}
			}
		});
	}

	/**
	 * 创建一个事务管理器
	 *
	 * @return 事务管理器
	 */
	public final Transaction createTransaction() {
		if (transaction == null) {
			transaction = new Transaction(getDataSource());
		}
		return transaction;
	}

	/**
	 * 创建preparedStatement
	 *
	 * @param sql             sql
	 * @param parameterSource parameterSource
	 *                        MapSqlParameterSource (org.springframework.jdbc.core.namedparam)
	 *                        BeanPropertySqlParameterSource (org.springframework.jdbc.core.namedparam)
	 * @return PreparedStatement
	 * @throws java.sql.SQLException SQLException
	 */
	private PreparedStatementCreator getStatementCreator(String sql, SqlParameterSource parameterSource, boolean generatedKey) throws SQLException {
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		List<SqlParameter> declaredParams = NamedParameterUtils.buildSqlParameterList(parsedSql, parameterSource);

		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, parameterSource);
		Object[] params = NamedParameterUtils.buildValueArray(parsedSql, parameterSource, declaredParams);

		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, declaredParams);
		pscf.setReturnGeneratedKeys(generatedKey);
		return pscf.newPreparedStatementCreator(params);
	}

	/**
	 * 创建callableStatement
	 *
	 * @param sql             sql
	 * @param parameterSource parameterSource
	 *                        MapSqlParameterSource (org.springframework.jdbc.core.namedparam)
	 *                        BeanPropertySqlParameterSource (org.springframework.jdbc.core.namedparam)
	 * @return callableStatement
	 * @throws java.sql.SQLException SQLException
	 */
	private CallableStatementCreator getCallableStatementCreator(String sql, SqlParameterSource parameterSource) throws SQLException {
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		List<SqlParameter> declaredParams = NamedParameterUtils.buildSqlParameterList(parsedSql, parameterSource);

		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, parameterSource);

		CallableStatementCreatorFactory cscf = new CallableStatementCreatorFactory(sqlToUse, declaredParams);
		Map<String, Object> params = buildValueMap(parameterSource);
		return cscf.newCallableStatementCreator(params);
	}

	private Map<String, Object> buildValueMap(SqlParameterSource parameterSource) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		if (parameterSource instanceof MapSqlParameterSource) {
			params = ((MapSqlParameterSource) parameterSource).getValues();
		} else if (parameterSource instanceof BeanPropertySqlParameterSource) {
			BeanPropertySqlParameterSource bpss = (BeanPropertySqlParameterSource) parameterSource;
			String[] propertyNames = bpss.getReadablePropertyNames();
			for (String pn : propertyNames) {
				params.put(pn, bpss.getValue(pn));
			}
		}
		return params;
	}

	/**
	 * 解析多行ResultSet
	 *
	 * @param rs  ResultSet
	 * @param rmh 映射工具
	 * @param <T> 类型
	 * @return T 列表
	 * @throws java.sql.SQLException
	 */
	private <T> List<T> processMultipleRow(ResultSet rs, RowMapperHandler<T> rmh) throws SQLException {
		List<T> list = new ArrayList<T>(0);
		while (rs.next()) {
			list.add(rmh.rowMapper(rs));
		}
		return list;
	}

	/**
	 * 解析单行ResultSet
	 *
	 * @param rs  ResultSet
	 * @param rmh 映射工具
	 * @param <T> 类型
	 * @return T 列表
	 * @throws java.sql.SQLException
	 */
	private <T> T processSingleRow(ResultSet rs, RowMapperHandler<T> rmh) throws SQLException {
		if (rs.next()) {
			return rmh.rowMapper(rs);
		}
		return null;
	}
}
