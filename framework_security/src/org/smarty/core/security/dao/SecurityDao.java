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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        return sqlSession.queryForMap(getAdmByUnSQL(), params);
    }

    /**
     * 更新admin
     *
     * @param params admin
     */
    public int updateAdm(Map<String, Object> params) throws SQLException {
        return sqlSession.executeUpdate(updateAdmSQL(),params);
    }

    /**
     * 查询资源列表
     *
     * @return 资源列表
     * @throws SQLException
     */
    public List<Map<String, Object>> queryRes() throws SQLException {
        return sqlSession.queryForMapList(queryResSQL());
    }

    /**
     * 根据adminId查询权限列表
     *
     * @param adminId adminId
     * @return 权限列表
     * @throws SQLException
     */
    public List<Map<String, Object>> queryRolByAdm(String adminId) throws SQLException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("admin_id", adminId);
        return sqlSession.queryForMapList(queryRolByAdmSQL(), params);
    }

    /**
     * 根据资源ID查询权限列表
     *
     * @param resourceId 资源ID
     * @return 权限列表
     * @throws SQLException
     */
    public List<Map<String, Object>> queryRolByRes(String resourceId) throws SQLException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("resource_id", resourceId);
        return sqlSession.queryForMapList(queryRolByResSQL(), params);
    }

    private String getAdmByUnSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("pk_id,");
        sb.append("create_date,");
        sb.append("modify_date,");
        sb.append("is_enabled,");
        sb.append("is_expired,");
        sb.append("is_locked,");
        sb.append("is_credentials_expired,");

        sb.append("locked_date,");
        sb.append("login_date,");
        sb.append("login_failure_count,");
        sb.append("login_ip,");

        sb.append("password,");
        sb.append("username ");
        sb.append("FROM ");
        sb.append("sec_admin ");
        sb.append("WHERE ");
        sb.append("is_delete=0 ");
        sb.append("AND username=:username");
        return sb.toString();
    }

    private String updateAdmSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE sec_admin SET ");
        sb.append("modify_date=NOW(),");
        sb.append("is_locked=:is_locked,");
        sb.append("locked_date=:locked_date,");
        sb.append("login_date=:login_date,");
        sb.append("login_failure_count=:login_failure_count,");
        sb.append("login_ip=:login_ip ");
        sb.append("WHERE ");
        sb.append("pk_id=:pk_id");
        return sb.toString();
    }

    private String queryResSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("pk_id,");
        sb.append("create_date,");
        sb.append("modify_date,");
        sb.append("name,");
        sb.append("value ");
        sb.append("FROM ");
        sb.append("sec_resource ");
        sb.append("WHERE ");
        sb.append("is_delete=0");
        return sb.toString();
    }

    private String queryRolByAdmSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("R.pk_id,");
        sb.append("R.create_date,");
        sb.append("R.modify_date,");
        sb.append("R.name,");
        sb.append("R.value ");
        sb.append("FROM ");
        sb.append("sec_role AS R ");
        sb.append("LEFT JOIN ");
        sb.append("sec_admin_role AS AR ");
        sb.append("ON ");
        sb.append("R.pk_id = AR.role_id ");
        sb.append("WHERE ");
        sb.append("R.is_delete=0 ");
        sb.append("AND AR.is_delete=0 ");
        sb.append("AND AR.admin_id=:admin_id");
        return sb.toString();
    }

    private String queryRolByResSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("R.pk_id,");
        sb.append("R.create_date,");
        sb.append("R.modify_date,");
        sb.append("R.name,");
        sb.append("R.value ");
        sb.append("FROM ");
        sb.append("sec_role AS R ");
        sb.append("LEFT JOIN ");
        sb.append("sec_role_resource AS RR ");
        sb.append("ON ");
        sb.append("R.pk_id = RR.role_id ");
        sb.append("WHERE ");
        sb.append("R.is_delete=0 ");
        sb.append("AND RR.is_delete=0 ");
        sb.append("AND RR.resource_id=:resource_id");
        return sb.toString();
    }
}
