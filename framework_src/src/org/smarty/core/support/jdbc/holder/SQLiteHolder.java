package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.utils.LogicUtil;

/**
 * SQLite实现
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class SQLiteHolder extends SQLHolder {

    public DBType getSQLType() {
        return DBType.SQLite;
    }

    public String convertLimitSQL(Pager pager) {
        StringBuilder sb = new StringBuilder(baseSQL);

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
        sb.append(pager.getPageNumber() < 1 ? 0 : (pager.getPageNumber() - 1));
        sb.append(",");
        sb.append(pager.getPageSize());
        return sb.toString();
    }
}
