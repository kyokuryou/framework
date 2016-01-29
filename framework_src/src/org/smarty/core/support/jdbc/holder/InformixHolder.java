package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;

/**
 * Informix工具箱(未实现)
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class InformixHolder extends SQLHolder {
	public DBType getSQLType() {
		return DBType.Informix;
	}

	@Override
	public String convertLimitSQL(Pager pager, int totalCount) {
		return null;
	}
}
