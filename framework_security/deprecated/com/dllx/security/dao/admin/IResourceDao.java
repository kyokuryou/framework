package com.dllx.security.dao.admin;

import com.dllx.security.bean.Resource;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Dao接口 - 资源
 */
@Repository
public interface IResourceDao {

    public List<Resource> findResourceListAll() throws SQLException;

}
