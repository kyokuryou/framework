package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.utils.LogicUtil;

import java.util.List;

/**
 * SQLServer工具箱
 */
public class MSSQLHolder extends SQLHolder {

    @Override
    public DBType getSQLType() {
        return DBType.MSSQL;
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

        Integer topEnd = pager.getPageNumber() * pager.getPageSize();
        sb.insert(7, "TOP " + topEnd + " ");

        String orderBy = orderBy(pager.getOrderBy(), pager.getOrderType());
        if (LogicUtil.isNotEmpty(orderBy)) {
            sb.append(orderBy);
        }
        return sb.toString();
    }

    /**
     * 重写!!支持2000数据库
     *
     *
     * @param pager pager
     * @return pager
     */
    @Override
    public <E> Pager convertLimitList(Pager pager, List<E> eList) {
        if (pager == null) {
            return new Pager();
        }
        if (LogicUtil.isNotEmptyCollection(eList)) {
            pager.setList(eList.subList(pager.getPageNumber(), eList.size()));
            return pager;
        }
        return pager;
    }
}
