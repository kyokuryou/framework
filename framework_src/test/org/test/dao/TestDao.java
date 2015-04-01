package org.test.dao;

import org.smarty.core.support.jdbc.SQLSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * Created by kyokuryou on 15-4-1.
 */
@Repository
public class TestDao {
    @Resource
    private SQLSession testSqlSession;

    public int getTestCountSql() throws SQLException {
        return testSqlSession.queryForInt("SELECT COUNT(1)");
    }

    public int getTestCountFile() throws SQLException{
        return testSqlSession.queryForInt();
    }
}
