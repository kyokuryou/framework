package org.smarty.core.jdbc.mapper;

import android.database.Cursor;
import android.util.Log;
import org.smarty.core.jdbc.SQLException;
import org.smarty.core.utils.JdbcUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 映射一行数据,以Map形式创建
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class MapMapperHandler implements RowMapperHandler<Map<String, Object>> {
    private final String TAG = "MapMapperHandler";

    @Override
    public Map<String, Object> rowMapper(Cursor cursor) throws SQLException {
        try {
            Map<String, Object> value = new HashMap<String, Object>();
            int count = cursor.getColumnCount();
            for (int i = 0; i < count; i++) {
                value.put(cursor.getColumnName(i), JdbcUtil.getColumnValue(cursor, i));
            }
            return value;
        } catch (RuntimeException e) {
            Log.e(TAG, "Cursor iterator failed!", e);
            throw new SQLException(e);
        }
    }
}
