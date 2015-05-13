package org.smarty.core.jdbc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created Date 2015/04/14
 *
 * @author kyokuryou
 * @version 1.0
 */
public abstract class JdbcAccessor extends SQLiteOpenHelper {
    protected final String TAG = JdbcAccessor.class.getSimpleName();

    public JdbcAccessor(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    protected abstract String getCreateSQL();

    protected abstract String getUpgradeSQL();

    public final void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateSQL());
    }

    public final void onUpgrade(SQLiteDatabase db) {
        db.execSQL(getUpgradeSQL());
        db.execSQL(getCreateSQL());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db);
    }

    protected <T> T execute(ConnectionCallback<T> action) throws SQLException {
        SQLiteDatabase sqlSession = getWritableDatabase();

        try {
            return action.doInConnection(sqlSession);
        } catch (RuntimeException e) {
            Log.e(TAG, "execute SQL failed", e);
            throw new SQLException(e);
        } finally {
            closeSession(sqlSession);
        }
    }

    protected <T> T execute(TransactionCallback<T> action) throws SQLException {
        SQLiteDatabase sqlSession = getWritableDatabase();
        T t;
        try {
            sqlSession.beginTransaction();
            t = action.doInTransaction();
            sqlSession.setTransactionSuccessful();
            return t;
        } catch (RuntimeException e) {
            Log.e(TAG, "execute transaction failed,rollback", e);
            throw new SQLException(e);
        } finally {
            sqlSession.endTransaction();
            closeSession(sqlSession);
        }
    }


    protected <T> T execute(String sql, String[] args, CursorCallback<T> action) throws SQLException {
        SQLiteDatabase sqlSession = getReadableDatabase();
        Cursor cursor = sqlSession.rawQuery(sql, args);
        try {
            return action.doInCursor(cursor);
        } catch (RuntimeException e) {
            Log.e(TAG, "execute SQL failed", e);
            throw new SQLException(e);
        } finally {
            closeSession(sqlSession, cursor);
        }
    }

    protected void closeSession(SQLiteDatabase sdb) throws SQLException {
        closeSession(sdb, null);
    }

    protected void closeSession(SQLiteDatabase sdb, Cursor cursor) throws SQLException {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (sdb == null || sdb.inTransaction()) {
            return;
        }
        try {
            sdb.close();
        } catch (RuntimeException e) {
            Log.e(TAG, "try close db failed!", e);
            throw new SQLException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
