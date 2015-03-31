package org.core.security.service;

import org.core.logger.RuntimeLogger;
import org.core.security.dao.SecurityDao;
import org.core.utils.LogicUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SecurityService {
    private static RuntimeLogger logger = new RuntimeLogger(SecurityService.class);

    private SecurityDao securityDao;


    /**
     * 获得Admin信息
     *
     * @param userName 用户名
     * @return admin
     */
    public Map<String, Object> getAdmByUserName(String userName) {
        try {
            Map<String, Object> admin = securityDao.findAdmByUn(userName);
            if (LogicUtil.isEmptyMap(admin)) {
                return null;
            }
            admin.put("role_list", securityDao.queryRolByAdm(admin.get("pk_id").toString()));
            return admin;
        } catch (SQLException e) {
            logger.out(e);
        }
        return null;
    }

    public void updateAdm(Map<String,Object> admin){
        try {
            securityDao.updateAdm(admin);
        } catch (SQLException e) {
            logger.out(e);
        }
    }

    public List<Map<String, Object>> getRolListByAdmin(String adminId) {
        try {
            return securityDao.queryRolByAdm(adminId);
        } catch (SQLException e) {
            logger.out(e);
        }
        return null;
    }

    public List<Map<String, Object>> getAllResource() {
        try {
            return securityDao.queryRes();
        } catch (SQLException e) {
            logger.out(e);
        }
        return null;
    }

    public List<Map<String, Object>> getRoleByResource(String resourceId) {
        try {
            return securityDao.queryRolByRes(resourceId);
        } catch (SQLException e) {
            logger.out(e);
        }
        return null;
    }

    public void setSecurityDao(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }
}
