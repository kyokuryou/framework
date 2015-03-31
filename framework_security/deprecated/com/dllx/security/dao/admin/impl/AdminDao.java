package com.dllx.security.dao.admin.impl;

import com.dllx.jdbc.SQLSession;
import com.dllx.security.bean.Admin;
import com.dllx.security.dao.admin.IAdminDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Dao接口 - 管理员
 */
@Repository
public class AdminDao implements IAdminDao {

    @Resource
    private SQLSession sqlSession;

    public Admin findByUserName(String name) throws SQLException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", name);
        return sqlSession.queryForBean(params, Admin.class);
    }

    public void update(Admin admin) {

    }
}
