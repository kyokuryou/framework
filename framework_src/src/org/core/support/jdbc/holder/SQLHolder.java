package org.core.support.jdbc.holder;

import org.core.Model;
import org.core.bean.Pager;
import org.core.support.jdbc.reader.ISQLLink;
import org.core.support.jdbc.reader.XmlSQLLink;
import org.core.support.jdbc.support.DBType;
import org.core.support.jdbc.support.SessionClass;
import org.core.logger.RuntimeLogger;
import org.core.utils.LogicUtil;
import org.core.utils.RegexUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class SQLHolder {
    private static RuntimeLogger logger = new RuntimeLogger(SQLHolder.class);
    private ISQLLink sqlLink;
    protected String baseSQL;

    public void initSQLHolder(String sql) {
        baseSQL = RegexUtil.convertSQL(sql);
    }

    /**
     * 初始化ReaderBuilder
     *
     * @param sqlClass sqlClass
     * @param sqlType  类型
     */
    public void initReaderBuilder(SessionClass sqlClass, DBType sqlType) {
        try {
            sqlLink = new XmlSQLLink(sqlClass.getXMLFile());
            sqlLink.set("sn", sqlClass.getClassElement().getMethodName());
            if (sqlType != null) {
                sqlLink.set("st", sqlType.name());
            }
        } catch (IOException e) {
            logger.out(sqlClass.getXMLFile() + "未找到");
        }
    }

    /**
     * 与SQL XML值关联
     *
     * @param bean xml参数
     * @return map
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getBeanMap(Model bean) {
        if (bean == null) {
            return new HashMap<String, Object>();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        char[] beanCh = bean.getClass().getSimpleName().toCharArray();
        beanCh[0] = Character.toLowerCase(beanCh[0]);
        param.put(new String(beanCh), bean);
        Set<Map.Entry<String, Object>> mes = bean.getParamMaps().entrySet();
        for (Map.Entry<String, Object> me : mes) {
            String key = me.getKey();
            if (param.containsKey(key)) {
                continue;
            }
            param.put(key, me.getValue());
        }
        return param;
    }

    /**
     * 返回处理完成的SQL(可直接使用)
     *
     * @return SQL
     */
    @SuppressWarnings("unchecked")
    public final String getSQLString() {
       return getSQLString(new HashMap<String, Object>());
    }

    /**
     * 返回处理完成的SQL(可直接使用)
     *
     * @param params velocity参数
     * @return SQL
     */
    @SuppressWarnings("unchecked")
    public final String getSQLString(Map<String, Object> params) {
        if (params == null) params = new HashMap<String, Object>();
        if (LogicUtil.isNotEmpty(baseSQL)) {
            return baseSQL;
        }
        return baseSQL = sqlLink.getResultSql(params);
    }

    /**
     * 返回处理完成的SQL(可直接使用)
     *
     * @param bean velocity参数
     * @return SQL
     */
    public final String getSQLString(Model bean) {
        Map<String, Object> params = getBeanMap(bean);
        return getSQLString(params);
    }

    /**
     * 转换分页SQL
     *
     * @param pager pager
     * @return SQL
     */
    public abstract String convertLimitSQL(Pager pager);

    /**
     * 转换Count SQL
     *
     * @return SQL
     */
    public String convertCountSQL() {
        // SELECT 中包含 DISTINCT 在外层包含COUNT
        StringBuilder sb = new StringBuilder();
        if (baseSQL.contains("SELECT DISTINCT") || baseSQL.contains("GROUP BY")) {
            sb.append("SELECT COUNT(1) as count FROM (").append(baseSQL).append(") t");
        } else {
            sb.append(baseSQL).replace(0, sb.indexOf("FROM") - 1, "SELECT COUNT(1) as count");
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
        if (!baseSQL.contains("ORDER BY") && orderType != null && LogicUtil.isNotEmpty(orderBy)) {
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
}