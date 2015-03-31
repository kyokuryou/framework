package com.dllx.security.dao.admin.impl;

import com.dllx.jdbc.SQLSession;
import com.dllx.security.bean.Role;
import com.dllx.security.dao.admin.IRoleDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao - 角色
 */
@Repository
public class RoleDao implements IRoleDao {

    @Resource
    private SQLSession sqlSession;

    public List<Role> findRoleListByAdmin(String adminId) throws SQLException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("adminId", adminId);
        return sqlSession.queryForBeanList(params, Role.class);
    }

    @Override
    public List<Role> findRoleListByResource(String resourceId) throws SQLException {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("resourceId",resourceId);
        return sqlSession.queryForBeanList(params, Role.class);
    }
}
