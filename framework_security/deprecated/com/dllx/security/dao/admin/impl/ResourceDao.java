package com.dllx.security.dao.admin.impl;

import com.dllx.jdbc.SQLSession;
import com.dllx.security.bean.Resource;
import com.dllx.security.dao.admin.IResourceDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Dao - 资源
 */
@Repository
public class ResourceDao implements IResourceDao {
    @javax.annotation.Resource
    private SQLSession sqlSession;

    @Override
    public List<Resource> findResourceListAll() throws SQLException {
        return sqlSession.queryForBeanList(Resource.class);
    }
}
