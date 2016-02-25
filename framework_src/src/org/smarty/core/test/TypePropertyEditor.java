package org.smarty.core.test;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
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
		try {
			switch (type) {
				case DATE:
					setValue(DateUtil.toDate(text, format));
					break;
				case TIMESTAMP:
					setValue(DateUtil.toTimestamp(text, format));
					break;
				case TIME:
					setValue(DateUtil.toTime(text, format));
					break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
