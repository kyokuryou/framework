package org.smarty.core.jdbc;

/**
 * Created Date 2015/04/09
 *
 * @author kyokuryou
 * @version 1.0
 */
public interface PlatformTransaction {
    void doInTransaction() throws SQLException;

}
