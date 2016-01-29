package org.smarty.core.support.jdbc;

import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.sql.StatementType;

/**
 * @author qul
 * @since LVGG1.1
 */
public class CallSQL {
	private static final ThreadLocal<SQL> localSQL = new ThreadLocal<SQL>();

	public static void BEGIN() {
		RESET();
	}

	public static void RESET() {
		localSQL.set(new SQL(StatementType.CALL));
	}

	public static void CALL(String callName) {
		sql().setTable(callName);
	}

	public static void VALUE(String values) {
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
