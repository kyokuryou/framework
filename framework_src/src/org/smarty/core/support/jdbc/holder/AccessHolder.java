package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;

/**
 * Access工具箱(未实现)
 */
public class AccessHolder extends SQLHolder {

    @Override
    public DBType getSQLType() {
        return DBType.Access;
    }

    public String convertLimitSQL(Pager pager) {
        return null; //TODO insert code
    }
}
