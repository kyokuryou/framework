package org.smarty.core.utils;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JdbcUtil
 */
public final class JdbcUtil {

    public static Object getColumnValue(Cursor cursor, int index) {
        int type = cursor.getType(index);
        switch (type) {
            case Cursor.FIELD_TYPE_INTEGER:
                return cursor.getInt(index);
            case Cursor.FIELD_TYPE_FLOAT:
                return cursor.getFloat(index);
            case Cursor.FIELD_TYPE_STRING:
                return cursor.getString(index);
            case Cursor.FIELD_TYPE_BLOB:
                return cursor.getBlob(index);
            default:
                return cursor.getString(index);
        }
    }

    public static String[] convertParams(ContentValues values) {
        if (values == null) {
            return null;
        }
        List<String> arr = new ArrayList<String>();
        Set<Map.Entry<String, Object>> mes = values.valueSet();
        for (Map.Entry<String, Object> me : mes) {
            arr.add(String.valueOf(me.getValue()));
        }
        return arr.toArray(new String[arr.size()]);
    }
}
