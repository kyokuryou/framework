package com.dllx.security.dao.admin;

import com.dllx.security.bean.Role;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Dao接口 - 角色
 */
@Repository
public interface IRoleDao {

    public List<Role> findRoleListByAdmin(String adminId) throws SQLException;

    public List<Role> findRoleListByResource(String resourceId) throws SQLException;
}
