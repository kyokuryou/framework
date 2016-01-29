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

	public String convertLimitSQL(Pager pager, int totalCount) {
		String sql = getSQLString(pager.getParams());
		StringBuilder sb = new StringBuilder(sql);

		// 计算总页数
		int pageCount = 0;
		if (totalCount % pager.getPageSize() == 0) {
			pageCount = totalCount / pager.getPageSize();
		} else if (totalCount % pager.getPageSize() > 0) {
			pageCount = totalCount / pager.getPageSize() + 1;
		}
		pager.setPageCount(pageCount);
		pager.setTotalCount(totalCount);

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
