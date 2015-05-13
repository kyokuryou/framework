package org.smarty.core.jdbc.mapper;

import android.database.Cursor;
import android.util.Log;
import org.smarty.core.jdbc.SQLException;
import org.smarty.core.utils.JdbcUtil;

/**
 * 映射一行一列数据
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class SingleMapperHandler<T> implements RowMapperHandler<T> {
    private final String TAG = "SingleMapperHandler";

    public T rowMapper(Cursor cursor) throws SQLException {
        try {
            if (cursor.moveToFirst()) {
                return (T) JdbcUtil.getColumnValue(cursor, 0);
            }
            return null;
        } catch (RuntimeException e) {
            Log.e(TAG, "Cursor iterator failed!", e);
            throw new SQLException(e);
        }
    }
}

