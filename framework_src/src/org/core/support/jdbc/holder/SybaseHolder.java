package org.core.support.jdbc.holder;

import org.core.bean.Pager;
import org.core.support.jdbc.support.DBType;

/**
 * Sybase工具箱(未实现)
 */
public class SybaseHolder extends SQLHolder {

    @Override
    public DBType getSQLType() {
        return DBType.Sybase;
    }

    public String convertLimitSQL(Pager pager) {
        return null; //TODO insert code
    }
}
