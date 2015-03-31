package org.core.support.jdbc.mapper;

import org.core.logger.RuntimeLogger;
import org.core.utils.JdbcUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 映射一行数据,以Map形式创建
 */
public class MapMapperHandler implements RowMapperHandler<Map<String, Object>> {
    private static RuntimeLogger logger = new RuntimeLogger(MapMapperHandler.class);

    public Map<String, Object> rowMapper(ResultSet rs) throws SQLException {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            ResultSetMetaData rsm = rs.getMetaData();
            int cc = rsm.getColumnCount();
            for (int i = 1; i <= cc; i++) {
                String cn = JdbcUtil.getResultSetColumnName(rsm, i);
                setObject(rs, cn, i, map);
            }
            return map;
        } catch (SQLException e) {
            logger.out(e);
            throw e;
        }
    }

    private void setObject(ResultSet rs, String cn, int index, Map<String, Object> map) throws SQLException {
        Object value = JdbcUtil.getResultSetValue(rs, index);
        map.put(cn, value);
    }
}
