package org.smarty.core.support.jdbc.sql;

/**
 * @author qul
 * @since LVGG1.1
 */
public class SQL extends AbstractSQL<SQL> {

    public SQL(StatementType type) {
        super(type);
    }

    @Override
    public SQL getSelf() {
        return this;
    }

}
