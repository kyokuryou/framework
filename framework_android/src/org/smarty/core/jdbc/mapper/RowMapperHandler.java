package org.smarty.core.jdbc.mapper;

import android.database.Cursor;
import org.smarty.core.jdbc.SQLException;

/**
 * Created Date 2015/04/14
 *
 * @author kyokuryou
 * @version 1.0
 */
public interface RowMapperHandler<T> {
    T rowMapper(Cursor cursor) throws SQLException;
}
