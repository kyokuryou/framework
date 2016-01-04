package org.test.dao;

import org.smarty.core.support.jdbc.InsertSQL;
import org.smarty.core.support.jdbc.SQLSession;
import org.smarty.core.support.jdbc.SelectSQL;
import org.smarty.core.support.jdbc.UpdateSQL;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        SelectSQL.SELECT("COUNT(1)");
        return testSqlSession.queryForInt(SelectSQL.SQL());
    }

    public int getTestCountFile() throws SQLException {
        return testSqlSession.queryForInt(null);
    }

    public Object getTestInsert1() throws SQLException {
        InsertSQL.BEGIN();
        InsertSQL.INSERT("t_z");
        InsertSQL.VALUES("`code`", "'abc'");
        return testSqlSession.executeUpdate(InsertSQL.SQL());
    }

    public Object getTestInsert2() throws SQLException {
        InsertSQL.BEGIN();
        InsertSQL.INSERT("t_z1");
        InsertSQL.VALUES("`code`", "'abc'");
        return testSqlSession.executeUpdate(InsertSQL.SQL());
    }

    public Object getTestUpdate1() throws SQLException {
        UpdateSQL.BEGIN();
        UpdateSQL.UPDATE("t_z");
        UpdateSQL.SET("`code`='abc1'");
        UpdateSQL.WHERE("id=50");
        return testSqlSession.executeUpdate(UpdateSQL.SQL());
    }
}
