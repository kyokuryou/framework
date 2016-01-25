package org.smarty.core.support.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.JdbcUtil;

/**
 * 映射一行一列数据
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class SingleMapperHandler implements RowMapperHandler<Object> {
    private static Log logger = LogFactory.getLog(SingleMapperHandler.class);

    public Object rowMapper(ResultSet rs) throws SQLException {
        try {
            return JdbcUtil.getResultSetValue(rs, 1);
        } catch (SQLException e) {
            logger.warn(e);
            throw e;
        }
    }
}
