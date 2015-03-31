package com.dllx.security.service.admin;

import com.dllx.security.bean.Admin;
import com.dllx.security.dao.admin.IAdminDao;
import com.dllx.security.dao.admin.IRoleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * Admin服务
 */
@Service
public class AdminService {
    @Resource
    private IAdminDao adminDao;
    @Resource
    private IRoleDao roleDao;

    /**
     * 获得Admin信息
     *
     * @param userName 用户名
     * @return admin
     */
    public Admin getAdminByUserName(String userName) {
        try {
            Admin admin = adminDao.findByUserName(userName);
            if (admin == null) {
                return null;
            }
            admin.setRoleList(roleDao.findRoleListByAdmin(admin.getPkId()));
            return admin;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
