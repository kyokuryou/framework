package org.smarty.core.test;

import java.beans.PropertyEditorSupport;
import org.smarty.core.common.BaseConstant;
import org.smarty.core.utils.DateUtil;

/**
 * 类型转换
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class TypePropertyEditor extends PropertyEditorSupport {

	private String format = BaseConstant.DEF_DATETIME_FORMAT;
	private DateType type = DateType.DATE;

	public void setAsText(String text) throws IllegalArgumentException {
		Object value = null;
		switch (type) {
			case DATE:
				value = DateUtil.toDate(text, format);
				break;
			case TIMESTAMP:
				value = DateUtil.toTimestamp(text, format);
				break;
			case TIME:
				value = DateUtil.toTime(text, format);
				break;
		}
		setValue(value);
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setType(DateType type) {
		this.type = type;
	}

	public enum DateType {
		DATE, TIMESTAMP, TIME
	}
}
