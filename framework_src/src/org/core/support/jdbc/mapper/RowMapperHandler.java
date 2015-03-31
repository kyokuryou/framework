package org.core.support.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public interface RowMapperHandler<T> {

    public T rowMapper(ResultSet rs) throws SQLException;
}
