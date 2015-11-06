package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.utils.LogicUtil;

/**
 * MySQL工具箱
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class MySQLHolder extends SQLHolder {

    public DBType getSQLType() {
        return DBType.MySQL;
    }

    public String convertLimitSQL(Pager pager) {
        String sql = getSQLString(pager.getParams());
        StringBuilder sb = new StringBuilder(sql);

        // 计算总页数
        Long pageCount = 0L;
        if (pager.getTotalCount() % pager.getPageSize() == 0) {
            pageCount = pager.getTotalCount() / pager.getPageSize();
        } else if (pager.getTotalCount() % pager.getPageSize() > 0) {
            pageCount = pager.getTotalCount() / pager.getPageSize() + 1;
        }
        pager.setPageCount(pageCount);

        String orderBy = orderBy(pager.getOrderBy(), pager.getOrderType());
        if (LogicUtil.isNotEmpty(orderBy)) {
            sb.append(orderBy);
        }
        sb.append(" LIMIT ");
        sb.append(pager.getPageNumber() < 1 ? 0 : (pager.getPageNumber() - 1) * pager.getPageSize());
        sb.append(",");
        sb.append(pager.getPageNumber() * pager.getPageSize());
        return sb.toString();
    }
}
