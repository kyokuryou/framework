package org.smarty.core.support.jdbc.support;

import org.smarty.core.support.jdbc.mapper.RowMapperHandler;
import org.smarty.core.support.jdbc.mapper.SingleMapperHandler;
import org.smarty.core.utils.JdbcUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * JDBC扩展
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class AbstractJdbc {
    protected final JdbcTemplate jdbcLocal = new JdbcTemplate();
    private Transaction transaction;

    protected AbstractJdbc() {
        jdbcLocal.setQueryTimeout(1200);
    }

    protected void setDataSource(DataSource dataSource) {
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
     * @throws java.sql.SQLException SQLException
     */
    protected final <T> T queryForSingle(final String sql, final SqlParameterSource sps, final RowMapperHandler<T> rmh) throws SQLException {
        return jdbcLocal.execute(new ConnectionCallback<T>() {
            public T doInConnection(Connection connection) throws SQLException, DataAccessException {
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    ps = JdbcUtil.getStatementCreator(sql, sps).createPreparedStatement(connection);
                    rs = ps.executeQuery();
                    return JdbcUtil.processSingleRow(rs, rmh);
                } finally {
                    JdbcUtil.closeResultSet(rs);
                    JdbcUtil.closeStatement(ps);
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
     * @throws java.sql.SQLException SQLException
     */
    protected final <T> List<T> queryForMulti(final String sql, final SqlParameterSource sps, final RowMapperHandler<T> rmh) throws SQLException {
        return jdbcLocal.execute(new ConnectionCallback<List<T>>() {
            public List<T> doInConnection(Connection connection) throws SQLException, DataAccessException {
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    ps = JdbcUtil.getStatementCreator(sql, sps).createPreparedStatement(connection);
                    rs = ps.executeQuery();
                    return JdbcUtil.processMultipleRow(rs, rmh);
                } finally {
                    JdbcUtil.closeResultSet(rs);
                    JdbcUtil.closeStatement(ps);
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
     * @throws java.sql.SQLException SQLException
     */
    protected final Object executeForUpdate(final String sql, final SqlParameterSource sps) throws SQLException {
        return jdbcLocal.execute(new ConnectionCallback<Object>() {
            public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                PreparedStatement ps = null;
                try {
                    ps = JdbcUtil.getIncrementStatementCreator(sql, sps).createPreparedStatement(connection);
                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    return JdbcUtil.processSingleRow(rs, new SingleMapperHandler());
                } finally {
                    JdbcUtil.closeStatement(ps);
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
     * @throws java.sql.SQLException SQLException
     */
    protected final boolean executeForCall(final String sql, final SqlParameterSource sps) throws SQLException {
        return jdbcLocal.execute(new ConnectionCallback<Boolean>() {
            public Boolean doInConnection(Connection connection) throws SQLException, DataAccessException {
                CallableStatement ps = null;
                try {
                    ps = JdbcUtil.getCallableStatementCreator(sql, sps).createCallableStatement(connection);
                    return ps.execute();
                } finally {
                    JdbcUtil.closeStatement(ps);
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
}
