package org.smarty.core.jdbc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import com.example.db.supper.mapper.RowMapperHandler;
import com.example.db.supper.mapper.SingleMapperHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created Date 2015/04/14
 *
 * @author kyokuryou
 * @version 1.0
 */
public abstract class JdbcTemplate extends JdbcAccessor {

    public JdbcTemplate(Context context, String name, int version) {
        super(context, name, new DefaultCursorFactory(), version);
    }

    public abstract String getTableName();

    /**
     * 清空表数据,等价于 {@code delete(null, null)}
     *
     * @return 影响行数
     * @throws SQLException
     */
    public int truncate() throws SQLException {
        return delete(null, null);
    }

    /**
     * 删除表数据
     *
     * @param where 条件
     * @param args  条件值
     * @return 影响行数
     * @throws SQLException
     */
    public int delete(final String where, final String[] args) throws SQLException {
        return execute(new ConnectionCallback<Integer>() {
            @Override
            public Integer doInConnection(SQLiteDatabase sqlSession) throws SQLException {
                return sqlSession.delete(getTableName(), where, args);
            }
        });
    }

    /**
     * 更新表数据
     *
     * @param values set值
     * @param where  条件
     * @param args   条件值
     * @return 影响行数
     * @throws SQLException
     */
    public int update(final ContentValues values, final String where, final String[] args) throws SQLException {
        if (values == null || values.size() == 0) {
            return 0;
        }
        return execute(new ConnectionCallback<Integer>() {
            @Override
            public Integer doInConnection(SQLiteDatabase sqlSession) throws SQLException {
                return sqlSession.update(getTableName(), values, where, args);
            }
        });
    }

    /**
     * 插入单行数据
     *
     * @param values 插入值得
     * @return 影响行数
     * @throws SQLException
     */
    public long insert(final ContentValues values) throws SQLException {
        if (values == null || values.size() == 0) {
            return 0l;
        }
        return execute(new ConnectionCallback<Long>() {
            @Override
            public Long doInConnection(SQLiteDatabase sqlSession) throws SQLException {
                return sqlSession.insert(getTableName(), null, values);
            }
        });
    }

    /**
     * 单行查询
     *
     * @param sql       sql
     * @param args      条件值
     * @param rowMapper 映射方式
     * @param <T>       映射类型
     * @return 查询结果
     * @throws SQLException
     */
    public <T> T queryForSingle(String sql, String[] args, final RowMapperHandler<T> rowMapper) throws SQLException {
        if (sql == null || "".equals(sql)) {
            return null;
        }
        return execute(sql, args, new CursorCallback<T>() {
            @Override
            public T doInCursor(Cursor cursor) throws SQLException {
                if (cursor.moveToNext()) {
                    return rowMapper.rowMapper(cursor);
                }
                return null;
            }
        });
    }

    /**
     * 多行映射
     *
     * @param sql       sql
     * @param args      条件值
     * @param rowMapper 映射方式
     * @param <T>       映射类型
     * @return 查询结果
     * @throws SQLException
     */
    public <T> List<T> queryForList(String sql, String[] args, final RowMapperHandler<T> rowMapper) throws SQLException {
        if (sql == null || "".equals(sql)) {
            return null;
        }
        return execute(sql, args, new CursorCallback<List<T>>() {
            @Override
            public List<T> doInCursor(Cursor cursor) throws SQLException {
                List<T> values = new ArrayList<T>();
                while (cursor.moveToNext()) {
                    values.add(rowMapper.rowMapper(cursor));
                }
                return values;
            }
        });
    }

    /**
     * 单行单列
     *
     * @param sql  sql
     * @param args 条件值
     * @param <T>  映射类型
     * @return 查询结果
     * @throws SQLException
     */
    public <T> T queryForObject(String sql, String[] args) throws SQLException {
        return queryForSingle(sql, args, new SingleMapperHandler<T>());
    }

    protected static class DefaultCursorFactory implements CursorFactory {

        @Override
        public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
            return new SQLiteCursor(db, masterQuery, editTable, query);
        }
    }
}
