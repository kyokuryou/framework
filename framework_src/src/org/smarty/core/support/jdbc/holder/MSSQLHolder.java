package org.smarty.core.support.jdbc.holder;

import java.util.List;
import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.utils.ObjectUtil;

/**
 * SQLServer工具箱
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class MSSQLHolder extends SQLHolder {

	public DBType getSQLType() {
		return DBType.MSSQL;
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

		Integer topEnd = pager.getPageNumber() * pager.getPageSize();
		sb.insert(7, "TOP " + topEnd + " ");

		String orderBy = orderBy(pager.getOrderBy(), pager.getOrderType());
		if (!ObjectUtil.isEmpty(orderBy)) {
			sb.append(orderBy);
		}
		return sb.toString();
	}

	/**
	 * 重写!!支持2000数据库
	 *
	 * @param pager pager
	 * @return pager
	 */
	public <E> Pager convertLimitList(Pager pager, List<E> eList) {
		if (pager == null) {
			return new Pager();
		}
		if (!ObjectUtil.isEmpty(eList)) {
			pager.setList(eList.subList(pager.getPageNumber(), eList.size()));
			return pager;
		}
		return pager;
	}
}
