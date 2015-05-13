package org.smarty.core.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import org.smarty.core.jdbc.SQLException;

/**
 * LocationHisDao
 */
public class LocationHisDao extends VcPhoneDao {
    private final String TAG = "LocationHisDao";

    public LocationHisDao(Context context) {
        super(context);
    }

    @Override
    public String getTableName() {
        return "VCLocationHis";
    }

    @Override
    protected String getCreateSQL() {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE IF NOT EXISTS");
        sql.append(" VCLocationHis(");
        sql.append(" _id integer primary key autoincrement,");
        sql.append(" userid integer,");
        sql.append(" latitude text,");
        sql.append(" longitude text,");
        sql.append(" dt text");
        sql.append(")");
        return sql.toString();
    }

    @Override
    protected String getDowngradeSQL() {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE IF NOT EXISTS");
        sql.append(" VCLocationHis(");
        sql.append(" _id integer primary key autoincrement,");
        sql.append(" userid integer,");
        sql.append(" latitude text,");
        sql.append(" longitude text,");
        sql.append(" dt text");
        sql.append(")");
        return sql.toString();
    }

    @Override
    protected String getUpgradeSQL() {
        return null;
    }

    public long addGps(ContentValues values) {
        if (values == null) {
            return 0;
        }
        values.put("dt", getNowDateTime());
        try {
            return insert(values);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return 0l;
    }
}
