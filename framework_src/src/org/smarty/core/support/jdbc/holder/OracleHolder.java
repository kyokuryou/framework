package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.utils.LogicUtil;

/**
 * Oracle工具箱
 */
public class OracleHolder extends SQLHolder {

    @Override
    public DBType getSQLType() {
        return DBType.Oracle;
    }

    public String convertLimitSQL(Pager pager) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ( SELECT row_.*, ROWNUM rownum_ FROM ( ");
        sb.append(baseSQL);

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
        int pagerNum = pager.getPageNumber() < 1 ? 1 : pager.getPageNumber();
        sb.append(" ) row_ WHERE ROWNUM <= ").append(pagerNum * pager.getPageSize()).append(" )");
        sb.append(" WHERE rownum_ > ").append(pagerNum - 1);
        return sb.toString();
    }
}
