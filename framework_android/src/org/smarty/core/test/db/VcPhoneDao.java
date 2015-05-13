package org.smarty.core.test.db;

import android.content.Context;
import org.smarty.core.jdbc.JdbcTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * VcPhoneDao
 */
public abstract class VcPhoneDao extends JdbcTemplate {
    private static final String DB_NAME = "VCPhone.db";
    private static final int VERSION = 1;
    private final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final String TIME_FORMAT = "HH:mm:ss";
    private final Locale DATE_LOCALE = Locale.getDefault();


    public VcPhoneDao(Context context) {
        super(context, DB_NAME, VERSION);
    }

    protected String getNowDate() {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT, DATE_LOCALE);
        return df.format(new Date());
    }

    protected String getNowTime() {
        DateFormat df = new SimpleDateFormat(TIME_FORMAT, DATE_LOCALE);
        return df.format(new Date());
    }

    protected String getNowDateTime() {
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT, DATE_LOCALE);
        return df.format(new Date());
    }
}
