package org.smarty.core.dao;

import org.smarty.core.dto.AdminRoleDto;
import org.smarty.core.entity.AdminModel;
import org.smarty.core.support.jdbc.SQLSession;
import org.smarty.core.logger.RuntimeLogger;

import java.sql.SQLException;
import java.util.List;

public class AdminRoleDao {
    private static RuntimeLogger logger = new RuntimeLogger(AdminRoleDao.class);
    private SQLSession shopSqlSession;

    public List<AdminRoleDto> getByAdmin(AdminModel admin) {
        try {
            admin =  new AdminModel();
            admin.setIsLocked(false);
            List<AdminRoleDto> ards = shopSqlSession.queryForBeanList(admin,AdminRoleDto.class);
            return ards;
        } catch (SQLException e) {
            logger.out(e);
        }
        return null;
    }

    public void setShopSqlSession(SQLSession shopSqlSession) {
        this.shopSqlSession = shopSqlSession;
    }
}
