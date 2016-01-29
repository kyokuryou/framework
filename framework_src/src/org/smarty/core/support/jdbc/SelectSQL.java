package org.smarty.core.support.jdbc;

import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.sql.StatementType;

/**
 * @author qul
 * @since LVGG1.1
 */
public class SelectSQL {
	private static final ThreadLocal<SQL> localSQL = new ThreadLocal<SQL>();

	public static void BEGIN() {
		RESET();
	}

	public static void RESET() {
		localSQL.set(new SQL(StatementType.SELECT));
	}

	public static void SELECT(String columns) {
		sql().setSelect(columns, false);
	}

	public static void SELECT_DISTINCT(String columns) {
		sql().setSelect(columns, true);
	}

	public static void FROM(String table) {
		sql().setTable(table);
	}

	public static void JOIN(String join) {
		sql().setJoin(join);
	}

	public static void INNER_JOIN(String join) {
		sql().setInnerJoin(join);
	}

	public static void LEFT_JOIN(String join) {
		sql().setLeftJoin(join);
	}

	public static void RIGHT_JOIN(String join) {
		sql().setRightJoin(join);
	}

	public static void OUTER_JOIN(String join) {
		sql().setOuterJoin(join);
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

	public static void GROUP_BY(String columns) {
		sql().setGroupBy(columns);
	}

	public static void HAVING(String conditions) {
		sql().setHaving(conditions);
	}

	public static void ORDER_BY(String columns) {
		sql().setOrderBy(columns);
	}

	public static void LIMIT(int from, int to) {
		sql().setLimit(from, to);
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
