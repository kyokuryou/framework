package com.dllx.security.dao.admin;

import com.dllx.security.bean.Admin;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * Dao接口 - 管理员
 */
@Repository
public interface IAdminDao {

    public Admin findByUserName(String name) throws SQLException;

    public void update(Admin admin);
}
