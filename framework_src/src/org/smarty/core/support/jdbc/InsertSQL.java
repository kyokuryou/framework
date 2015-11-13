package org.smarty.core.support.jdbc;

import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.sql.StatementType;

/**
 * @author qul
 * @since LVGG1.1
 */
public class InsertSQL {
    private static final ThreadLocal<SQL> localSQL = new ThreadLocal<SQL>();

    public static void BEGIN() {
        RESET();
    }

    public static void RESET() {
        localSQL.set(new SQL(StatementType.INSERT));
    }


    public static void INSERT(String tableName) {
        sql().setTable(tableName);
    }

    public static void VALUES(String columns, String values) {
        sql().addColumn(columns);
        sql().addValue(values);
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
