package org.smarty.core.support.jdbc.holder;

import org.smarty.core.bean.Pager;
import org.smarty.core.support.jdbc.support.DBType;

/**
 * PostgreSQL工具箱(未实现)
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class PostgreHolder extends SQLHolder {

	public DBType getSQLType() {
		return DBType.PostgreSQL;
	}

	@Override
	public String convertLimitSQL(Pager pager, int totalCount) {
		return null;
	}
}
