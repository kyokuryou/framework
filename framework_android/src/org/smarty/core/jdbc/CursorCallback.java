package org.smarty.core.jdbc;

import android.database.Cursor;

/**
 * Created Date 2015/04/14
 *
 * @author kyokuryou
 * @version 1.0
 */
public interface CursorCallback<T> {
    T doInCursor(Cursor cursor) throws SQLException;
}
