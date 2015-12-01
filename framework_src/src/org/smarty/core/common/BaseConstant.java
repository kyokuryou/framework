package org.smarty.core.common;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.TimeZone;

/**
 * @author qul
 * @since LVGG1.1
 */
public interface BaseConstant {
    PrintStream DEF_OUT = System.out;
    Charset DEF_CHARSET = Charset.forName("UTF-8");
    String DEF_ENCODING = "UTF-8";
    String DEF_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String DEF_DATE_FORMAT = "yyyy-MM-dd";
    String DEF_TIME_FORMAT = "HH:mm:ss";
    TimeZone DEF_TIME_ZONE = TimeZone.getTimeZone("GMT");

    String DEF_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    // 缓存
    String CACHE_SYSTEM = "system";
    String CACHE_TEMPORARY = "temporary";

    char LETTERS_LF = '\n';

    char LETTERS_CR = '\r';

    char LETTERS_QUOTE = '"';

    char LETTERS_COMMA = ',';

    char LETTERS_SPACE = ' ';

    char LETTERS_TAB = '\t';

    char LETTERS_POUND = '#';

    char LETTERS_BACKSLASH = '\\';

    char LETTERS_NULL = '\0';

    char LETTERS_BACKSPACE = '\b';

    char LETTERS_FORM_FEED = '\f';

    char LETTERS_ESCAPE = '\u001B'; // ASCII/ANSI escape

    char LETTERS_VERTICAL_TAB = '\u000B';

    char LETTERS_ALERT = '\u0007';

    int SOCKET_MAX_STREAM = 1024;
    // 默认除法运算精度
    int DEF_DIV_SCALE = 10;
}
