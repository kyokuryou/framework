package org.smarty.core.support.jdbc.mapper;

import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.JdbcUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 映射一行一列数据
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class SingleMapperHandler implements RowMapperHandler<Object> {
    private static RuntimeLogger logger = new RuntimeLogger(SingleMapperHandler.class);

    public Object rowMapper(ResultSet rs) throws SQLException {
        try {
            return JdbcUtil.getResultSetValue(rs, 1);
        } catch (SQLException e) {
            logger.out(e);
            throw e;
        }
    }
}
