package org.core.support.jdbc.holder;

import org.core.bean.Pager;
import org.core.support.jdbc.support.DBType;

/**
 * PostgreSQL工具箱(未实现)
 */
public class PostgreHolder extends SQLHolder {

    @Override
    public DBType getSQLType() {
        return DBType.PostgreSQL;
    }

    public String convertLimitSQL(Pager pager) {
        return null; //TODO insert code
    }
}
