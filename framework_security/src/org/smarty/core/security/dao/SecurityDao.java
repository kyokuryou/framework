package org.smarty.core.security.dao;

import org.smarty.core.support.jdbc.SQLSession;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityDao {

    private SQLSession sqlSession;

    public void setSqlSession(SQLSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 根据用户名查询admin
     *
     * @param username 用户名
     * @return admin
     * @throws SQLException
     */
    public Map<String, Object> findAdmByUn(String username) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("pk_id,");
        sql.append("create_date,");
        sql.append("modify_date,");
        sql.append("is_enabled,");
        sql.append("is_expired,");
        sql.append("is_locked,");
        sql.append("is_credentials_expired,");

        sql.append("locked_date,");
        sql.append("login_date,");
        sql.append("login_failure_count,");
        sql.append("login_ip,");

        sql.append("password,");
        sql.append("username ");
        sql.append("FROM ");
        sql.append("sec_admin ");
        sql.append("WHERE ");
        sql.append("is_delete=0 ");
        sql.append("AND username=:username");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        return sqlSession.queryForMap(sql.toString(), params);
    }

    /**
     * 更新admin
     *
     * @param params admin
     */
    public int updateAdm(Map<String, Object> params) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE sec_admin SET ");
        sql.append("modify_date=NOW(),");
        sql.append("is_locked=:is_locked,");
        sql.append("locked_date=:locked_date,");
        sql.append("login_date=:login_date,");
        sql.append("login_failure_count=:login_failure_count,");
        sql.append("login_ip=:login_ip ");
        sql.append("WHERE ");
        sql.append("pk_id=:pk_id");
        return sqlSession.executeUpdate(sql.toString(), params);
    }

    /**
     * 查询资源列表
     *
     * @return 资源列表
     * @throws SQLException
     */
    public List<Map<String, Object>> queryRes() throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("pk_id,");
        sql.append("create_date,");
        sql.append("modify_date,");
        sql.append("name,");
        sql.append("value ");
        sql.append("FROM ");
        sql.append("sec_resource ");
        sql.append("WHERE ");
        sql.append("is_delete=0");
        return sqlSession.queryForMapList(sql.toString());
    }

    /**
     * 根据adminId查询权限列表
     *
     * @param adminId adminId
     * @return 权限列表
     * @throws SQLException
     */
    public List<Map<String, Object>> queryRolByAdm(String adminId) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("R.pk_id,");
        sql.append("R.create_date,");
        sql.append("R.modify_date,");
        sql.append("R.name,");
        sql.append("R.value ");
        sql.append("FROM ");
        sql.append("sec_role AS R ");
        sql.append("LEFT JOIN ");
        sql.append("sec_admin_role AS AR ");
        sql.append("ON ");
        sql.append("R.pk_id = AR.role_id ");
        sql.append("WHERE ");
        sql.append("R.is_delete=0 ");
        sql.append("AND AR.is_delete=0 ");
        sql.append("AND AR.admin_id=:admin_id");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("admin_id", adminId);
        return sqlSession.queryForMapList(sql.toString(), params);
    }

    /**
     * 根据资源ID查询权限列表
     *
     * @param resourceId 资源ID
     * @return 权限列表
     * @throws SQLException
     */
    public List<Map<String, Object>> queryRolByRes(String resourceId) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("R.pk_id,");
        sql.append("R.create_date,");
        sql.append("R.modify_date,");
        sql.append("R.name,");
        sql.append("R.value ");
        sql.append("FROM ");
        sql.append("sec_role AS R ");
        sql.append("LEFT JOIN ");
        sql.append("sec_role_resource AS RR ");
        sql.append("ON ");
        sql.append("R.pk_id = RR.role_id ");
        sql.append("WHERE ");
        sql.append("R.is_delete=0 ");
        sql.append("AND RR.is_delete=0 ");
        sql.append("AND RR.resource_id=:resource_id");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("resource_id", resourceId);
        return sqlSession.queryForMapList(sql.toString(), params);
    }
}
