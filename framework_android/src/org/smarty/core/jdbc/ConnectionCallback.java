package org.smarty.core.jdbc;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created Date 2015/04/14
 *
 * @author kyokuryou
 * @version 1.0
 */
public interface ConnectionCallback<T> {
    T doInConnection(SQLiteDatabase sqlSession) throws SQLException;
}
