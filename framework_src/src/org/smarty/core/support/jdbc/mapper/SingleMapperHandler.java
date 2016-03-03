package org.smarty.core.support.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * 映射一行一列数据
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class SingleMapperHandler<T> extends RowMapperHandler<T> {
	private static Log logger = LogFactory.getLog(SingleMapperHandler.class);

	private Class<T> requiredType;

	public SingleMapperHandler() {
	}

	public SingleMapperHandler(Class<T> superClass) {
		this.requiredType = superClass;
	}

	@SuppressWarnings("unchecked")
	public T rowMapper(ResultSet rs) throws SQLException {
		try {
			Object val = JdbcUtils.getResultSetValue(rs, 1);
			if (requiredType != null && !requiredType.isAssignableFrom(val.getClass())) {
				throw new ClassCastException("must be of " + requiredType + ", but was actually of " + val.getClass());
			}
			return (T) val;
		} catch (SQLException e) {
			logger.warn(e);
			throw e;
		}
	}
}
