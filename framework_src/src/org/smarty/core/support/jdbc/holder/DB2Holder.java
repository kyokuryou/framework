package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.utils.LogicUtil;

import java.util.Map;

/**
 * DB2工具箱
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class DB2Holder extends SQLHolder {

    public DBType getSQLType() {
        return DBType.DB2;
    }

    public String convertLimitSQL(Pager pager) {
        String sql = getSQLString(pager.getParams());
        StringBuilder sb = new StringBuilder("SELECT * FROM (SELECT ROW_.*,ROWNUMBER() OVER(");
        sb.append(") AS ROWID FROM ( ");
        sb.append(sql);

        // 计算总页数
        int pageCount = 0;
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
        // sb.append(" ) ROW_ FETCH FIRST ").append(pagerNum *
        // pager.getPageSize()).append( " ROWS ONLY) TMP WHERE ");
        sb.append(" ) ROW_ ) TMP WHERE TMP.ROWID <= ").append(pagerNum * pager.getPageSize());
        sb.append(" AND TMP.ROWID > ").append((pagerNum - 1) * pager.getPageSize());
        return sb.toString();
    }
}
