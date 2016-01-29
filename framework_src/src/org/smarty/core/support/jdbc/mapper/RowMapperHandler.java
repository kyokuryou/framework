package org.smarty.core.support.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.smarty.core.utils.CommonUtil;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class RowMapperHandler<T> {

	public abstract T rowMapper(ResultSet rs) throws SQLException;

	protected final String getFieldName(ResultSetMetaData rsmd, int index) throws SQLException {
		String cn = JdbcUtils.lookupColumnName(rsmd, index);
		return CommonUtil.toJavaField(cn);
	}

	protected final String getMapName(ResultSetMetaData rsmd, int index) throws SQLException {
		String cn = JdbcUtils.lookupColumnName(rsmd, index);
		return cn;
	}
}
