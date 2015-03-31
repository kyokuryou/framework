package org.core.jdbc.reader;

import org.core.jdbc.support.SQLTypeEnum;
import org.core.utils.RegexUtil;

import java.util.Map;

public abstract class AbstractSQLReader {

    public abstract String read(Map<String, Object> date);

    public abstract void setParams(SQLTypeEnum typeStr, String nodeStr);

    public abstract void setFileName(String fileName);

    protected final String simplifySQL(String sql) {
        return RegexUtil.convertSQL(sql);
    }
}
