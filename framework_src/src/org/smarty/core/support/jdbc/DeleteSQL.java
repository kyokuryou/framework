package org.smarty.core.support.jdbc;

import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.sql.StatementType;

/**
 * @author qul
 * @since LVGG1.1
 */
public class DeleteSQL {
    private static final ThreadLocal<SQL> localSQL = new ThreadLocal<SQL>();

    public static void BEGIN() {
        RESET();
    }

    public static void RESET() {
        localSQL.set(new SQL(StatementType.DELETE));
    }


    public static void DELETE(String tableName) {
        sql().setTable(tableName);
    }

    public static void WHERE(String conditions) {
        sql().setWhere(conditions);
    }

    public static void OR() {
        sql().addOr();
    }

    public static void AND() {
        sql().addAnd();
    }

    private static SQL sql() {
        return localSQL.get();
    }

    public static SQL SQL() {
        try {
            return sql();
        } finally {
            RESET();
        }
    }
}
