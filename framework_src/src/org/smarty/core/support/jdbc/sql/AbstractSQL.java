package org.smarty.core.support.jdbc.sql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qul
 * @since LVGG1.1
 */
public abstract class AbstractSQL<T> {

    private static final String AND = ") AND (";
    private static final String OR = ") OR (";

    private SQLStatement sql = new SQLStatement();

    protected AbstractSQL(StatementType type) {
        sql().statementType = type;
    }

    protected abstract T getSelf();

    public T setSets(String sets) {
        sql().sets.add(sets);
        return getSelf();
    }

    public T setSelect(String column, boolean distinct) {
        sql().distinct = distinct;
        sql().select.add(column);
        return getSelf();
    }

    public T setTable(String table) {
        sql().tables.add(table);
        return getSelf();
    }


    public T setJoin(String join) {
        sql().join.add(join);
        return getSelf();
    }

    public T setInnerJoin(String join) {
        sql().innerJoin.add(join);
        return getSelf();
    }

    public T setOuterJoin(String join) {
        sql().outerJoin.add(join);
        return getSelf();
    }


    public T setLeftOuterJoin(String join) {
        sql().leftOuterJoin.add(join);
        return getSelf();
    }

    public T setRightOuterJoin(String join) {
        sql().rightOuterJoin.add(join);
        return getSelf();
    }

    public T setWhere(String conditions) {
        sql().where.add(conditions);
        sql().lastList = sql().where;
        return getSelf();
    }

    public T setHaving(String conditions) {
        sql().having.add(conditions);
        sql().lastList = sql().having;
        return getSelf();
    }

    public T setGroupBy(String columns) {
        sql().groupBy.add(columns);
        return getSelf();
    }

    public T setOrderBy(String columns) {
        sql().orderBy.add(columns);
        return getSelf();
    }

    public T addColumn(String column) {
        sql().columns.add(column);
        return getSelf();
    }

    public T addValue(String value) {
        sql().values.add(value);
        return getSelf();
    }

    public T addOr() {
        sql().lastList.add(OR);
        return getSelf();
    }

    public T addAnd() {
        sql().lastList.add(AND);
        return getSelf();
    }


    private SQLStatement sql() {
        return sql;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sql().sql(sb);
        return sb.toString();
    }

    private static class SafeAppend {
        private final Appendable a;
        private boolean empty = true;

        protected SafeAppend(Appendable a) {
            super();
            this.a = a;
        }

        protected SafeAppend append(CharSequence s) {
            try {
                if (empty && s.length() > 0) {
                    empty = false;
                }
                a.append(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        protected boolean isEmpty() {
            return empty;
        }

    }

    public static class SQLStatement {

        StatementType statementType;
        List<String> sets = new ArrayList<String>();
        List<String> select = new ArrayList<String>();
        List<String> tables = new ArrayList<String>();
        List<String> join = new ArrayList<String>();
        List<String> innerJoin = new ArrayList<String>();
        List<String> outerJoin = new ArrayList<String>();
        List<String> leftOuterJoin = new ArrayList<String>();
        List<String> rightOuterJoin = new ArrayList<String>();
        List<String> where = new ArrayList<String>();
        List<String> having = new ArrayList<String>();
        List<String> groupBy = new ArrayList<String>();
        List<String> orderBy = new ArrayList<String>();
        List<String> lastList = new ArrayList<String>();
        List<String> columns = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        boolean distinct;

        protected SQLStatement() {
            // Prevent Synthetic Access
        }

        private void sqlClause(SafeAppend builder, String keyword, List<String> parts, String open, String close,
                               String conjunction) {
            if (!parts.isEmpty()) {
                if (!builder.isEmpty()) {
                    builder.append(" ");
                }
                builder.append(keyword);
                builder.append(" ");
                builder.append(open);
                String last = "________";
                for (int i = 0, n = parts.size(); i < n; i++) {
                    String part = parts.get(i);
                    if (i > 0 && !part.equals(AND) && !part.equals(OR) && !last.equals(AND) && !last.equals(OR)) {
                        builder.append(conjunction);
                    }
                    builder.append(part);
                    last = part;
                }
                builder.append(close);
            }
        }

        private String selectSQL(SafeAppend builder) {
            if (distinct) {
                sqlClause(builder, "SELECT DISTINCT", select, "", "", ", ");
            } else {
                sqlClause(builder, "SELECT", select, "", "", ", ");
            }

            sqlClause(builder, "FROM", tables, "", "", ", ");
            sqlClause(builder, "JOIN", join, "", "", " JOIN ");
            sqlClause(builder, "INNER JOIN", innerJoin, "", "", " INNER JOIN ");
            sqlClause(builder, "OUTER JOIN", outerJoin, "", "", " OUTER JOIN ");
            sqlClause(builder, "LEFT OUTER JOIN", leftOuterJoin, "", "", " LEFT OUTER JOIN ");
            sqlClause(builder, "RIGHT OUTER JOIN", rightOuterJoin, "", "", " RIGHT OUTER JOIN ");
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
            sqlClause(builder, "HAVING", having, "(", ")", " AND ");
            sqlClause(builder, "ORDER BY", orderBy, "", "", ", ");
            return builder.toString();
        }

        private String insertSQL(SafeAppend builder) {
            sqlClause(builder, "INSERT INTO", tables, "", "", "");
            sqlClause(builder, "", columns, "(", ")", ", ");
            sqlClause(builder, "VALUES", values, "(", ")", ", ");
            return builder.toString();
        }

        private String deleteSQL(SafeAppend builder) {
            sqlClause(builder, "DELETE FROM", tables, "", "", "");
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            return builder.toString();
        }

        private String updateSQL(SafeAppend builder) {
            sqlClause(builder, "UPDATE", tables, "", "", "");
            sqlClause(builder, "SET", sets, "", "", ", ");
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            return builder.toString();
        }

        protected String sql(Appendable a) {
            SafeAppend builder = new SafeAppend(a);
            if (statementType == null) {
                return null;
            }

            String answer;

            switch (statementType) {
                case DELETE:
                    answer = deleteSQL(builder);
                    break;

                case INSERT:
                    answer = insertSQL(builder);
                    break;

                case SELECT:
                    answer = selectSQL(builder);
                    break;

                case UPDATE:
                    answer = updateSQL(builder);
                    break;

                default:
                    answer = null;
            }

            return answer;
        }
    }
}