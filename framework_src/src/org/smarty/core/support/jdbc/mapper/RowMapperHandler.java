package org.smarty.core.support.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public interface RowMapperHandler<T> {

    T rowMapper(ResultSet rs) throws SQLException;
}
