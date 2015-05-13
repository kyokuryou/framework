package org.smarty.core.jdbc;

/**
 * Created Date 2015/04/14
 *
 * @author kyokuryou
 * @version 1.0
 */
public interface TransactionCallback<T> {
    T doInTransaction() throws SQLException;
}
