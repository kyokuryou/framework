package org.smarty.core.support.jdbc.holder;

import org.smarty.core.Model;
import org.smarty.core.bean.Pager;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.utils.BeanUtil;
import org.smarty.core.utils.LilystudioUtil;
import org.smarty.core.utils.LogicUtil;
import org.smarty.core.utils.RegexUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQL创建
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class SQLHolder {
    private static RuntimeLogger logger = new RuntimeLogger(SQLHolder.class);
    private String sql;

    public void setSql(String sql) {
        this.sql = RegexUtil.convertSQL(sql);
    }

    /**
     * 返回处理完成的SQL(可直接使用)
     *
     * @return SQL
     */
    public final String getSQLString() {
        return getSQLString(new HashMap<String, Object>());
    }

    /**
     * 返回处理完成的SQL(可直接使用)
     *
     * @param bean velocity参数
     * @return SQL
     */
    public final String getSQLString(Model bean) {
        Map<String, Object> params = BeanUtil.copyToMap(bean);
        return getSQLString(params);
    }

    /**
     * 返回处理完成的SQL(可直接使用)
     *
     * @param params velocity参数
     * @return SQL
     */
    public final String getSQLString(Map<String, Object> params) {
        if (LogicUtil.isEmptyMap(params)) {
            return sql;
        }
        return LilystudioUtil.render(sql, params);
    }

    /**
     * 转换Count SQL
     *
     * @return SQL
     */
    public String convertCountSQL() {
        // SELECT 中包含 DISTINCT 在外层包含COUNT
        StringBuilder sb = new StringBuilder();
        if (sql.contains("SELECT DISTINCT") || sql.contains("GROUP BY")) {
            sb.append("SELECT COUNT(1) as count FROM (").append(sql).append(") t");
        } else {
            sb.append(sql).replace(0, sb.indexOf("FROM") - 1, "SELECT COUNT(1) as count");
        }
        return sb.toString();
    }

    /**
     * 生成ORDER BY语句
     *
     * @param orderBy   orderBy
     * @param orderType orderType
     * @return SQL
     */
    public String orderBy(String orderBy, Pager.OrderType orderType) {
        if (!sql.contains("ORDER BY") && orderType != null && LogicUtil.isNotEmpty(orderBy)) {
            StringBuilder sb = new StringBuilder();
            sb.append(" ORDER BY ");
            sb.append(orderBy);
            sb.append(" ");
            sb.append(orderType.name());
            return sb.toString();
        }
        return null;
    }

    /**
     * 分页时结果处理(MS2000需要重写)
     *
     * @param pager pager
     * @param eList 结果
     * @return pager
     */
    public <E> Pager convertLimitList(Pager pager, List<E> eList) {
        if (pager == null) {
            return new Pager();
        }
        pager.setList(eList);
        return pager;
    }

    public abstract DBType getSQLType();

    /**
     * 转换分页SQL
     *
     * @param pager pager
     * @return SQL
     */
    public abstract String convertLimitSQL(Pager pager);
}