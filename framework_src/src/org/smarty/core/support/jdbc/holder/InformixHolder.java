package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;

/**
 * Informix工具箱(未实现)
 */
public class InformixHolder extends SQLHolder {
    @Override
    public DBType getSQLType() {
        return DBType.Informix;
    }

    public String convertLimitSQL(Pager pager) {
        return null; //TODO insert code
    }
}