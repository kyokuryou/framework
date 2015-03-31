package org.smarty.core.test;

import org.smarty.core.utils.DateUtil;

import java.beans.PropertyEditorSupport;

public class TypePropertyEditor extends PropertyEditorSupport {

    private String format = DateUtil.DEFAULT_FORMAT;
    private DateType type = DateType.DATE;

    public enum DateType {
        DATE, TIMESTAMP, TIME
    }

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
}
