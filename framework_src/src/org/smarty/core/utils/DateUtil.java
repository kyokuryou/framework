package org.smarty.core.utils;

import org.smarty.core.common.BaseConstant;
import org.smarty.core.io.RuntimeLogger;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class DateUtil {
    private static RuntimeLogger logger = new RuntimeLogger(DateUtil.class);

    private DateUtil() {
    }

    /**
     * 获得日历
     *
     * @return calendar
     */
    public static Calendar getCalendar() {
        return GregorianCalendar.getInstance(BaseConstant.DEF_TIME_ZONE);
    }

    /**
     * 获得指定时间的日历
     *
     * @return calendar
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = GregorianCalendar.getInstance(BaseConstant.DEF_TIME_ZONE);
        cal.setTime(date);
        return cal;
    }

    /**
     * 比较两个 Date 对象表示的时间值
     *
     * @param date1 第一个
     * @param date2 第二个
     * @return 如果date1 的时间与date2 的时间等于，则返回 0 值；
     * 如果date1 的时间在date2 的时间之前，则返回小于 0 的值；
     * 如果date1 的时间在date2 的时间之后，则返回大于 0 的值。
     */
    public static int compare(Date date1, Date date2) {
        return getCalendar(date1).compareTo(getCalendar(date2));
    }

    /**
     * 给定的日历字段添加或减去指定的时间量。
     *
     * @param date   date
     * @param field  日历字段。
     * @param amount 增量用正数，否则用负数
     * @return date
     */
    public static Date add(Date date, int field, int amount) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 获得日历字段指定的时间量
     *
     * @param date  date
     * @param field 日历字段。
     * @return 时间量
     */
    public static Integer get(Date date, int field) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(field);
    }

    /**
     * 获得当前时间
     *
     * @return date
     */
    public static Date getToday() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获得时间格式化实例
     *
     * @param format 格式
     * @return 实例
     */
    public static DateFormat getFormat(String format) {
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(BaseConstant.DEF_TIME_ZONE);
        return df;
    }

    /**
     * 格式化时间对象
     *
     * @param date date
     * @return 格式化后的字符串
     */
    public static String format(Date date) {
        return format(date, BaseConstant.DEF_DATETIME_FORMAT);
    }


    /**
     * 格式化时间对象
     *
     * @param date   date
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
        return getFormat(format).format(date);
    }

    /**
     * 将source时间字符串转换成Date类型
     *
     * @param source 表示时间的字符串
     * @return 转换后的Date类型
     */
    public static Date toDate(String source) {
        return toDate(source, BaseConstant.DEF_DATETIME_FORMAT);
    }

    /**
     * 将source时间字符串转换成Date类型
     *
     * @param source 表示时间的字符串
     * @param format 格式化字符串表示
     * @return 转换后的Date类型
     */
    public static Date toDate(String source, String format) {
        try {
            return getFormat(format).parse(source);
        } catch (ParseException e) {
            logger.out(e);
        }
        return null;
    }


    /**
     * 将source时间字符串转换成Timestamp类型
     *
     * @param source 表示时间的字符串
     * @return 转换后的Timestamp类型
     */
    public static Timestamp toTimestamp(String source) {
        return toTimestamp(source, BaseConstant.DEF_DATETIME_FORMAT);
    }

    /**
     * 将source时间字符串转换成Timestamp类型
     *
     * @param source 表示时间的字符串
     * @param format 格式化字符串表示
     * @return 转换后的Timestamp类型
     */
    public static Timestamp toTimestamp(String source, String format) {
        return new Timestamp(toDate(source, format).getTime());
    }

    /**
     * 将source时间字符串转换成Time类型
     *
     * @param source 表示时间的字符串
     * @return 转换后的Time类型
     */
    public static Time toTime(String source) {
        return toTime(source, BaseConstant.DEF_DATETIME_FORMAT);
    }

    /**
     * 将source时间字符串转换成Time类型
     *
     * @param source 表示时间的字符串
     * @param format 格式化字符串表示
     * @return 转换后的Time类型
     */
    public static Time toTime(String source, String format) {
        return new Time(toDate(source, format).getTime());
    }

    /**
     * 获得年
     *
     * @param date Date对象
     * @return 该Date对象表示年份
     */
    public static Integer getYear(Date date) {
        return get(date, Calendar.YEAR);
    }

    /**
     * 获得月
     *
     * @param date Date对象
     * @return 该Date对象表示月份
     */
    public static Integer getMonth(Date date) {
        return get(date, Calendar.MONTH);
    }

    /**
     * 获得日
     *
     * @param date Date对象
     * @return 该Date对象表示日
     */
    public static Integer getDay(Date date) {
        return get(date, Calendar.DATE);
    }

    /**
     * 获得时
     *
     * @param date Date对象
     * @return 该Date对象表示小时
     */
    public static Integer getHour(Date date) {
        return get(date, Calendar.HOUR);
    }

    /**
     * 获得分钟
     *
     * @param date Date对象
     * @return 该Date对象表示分钟
     */
    public static Integer getMinute(Date date) {
        return get(date, Calendar.MINUTE);
    }

    /**
     * 获得秒数
     *
     * @param date Date对象
     * @return 该Date对象表示秒数
     */
    public static Integer getSecond(Date date) {
        return get(date, Calendar.SECOND);
    }

    /**
     * 添加或减去年数
     *
     * @param date   Date对象
     * @param amount 增量用正数，否则用负数
     * @return date
     */
    public static Date addYear(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 添加或减去月数
     *
     * @param date   Date对象
     * @param amount 增量用正数，否则用负数
     * @return date
     */
    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 添加或减去天数
     *
     * @param date   Date对象
     * @param amount 增量用正数，否则用负数
     * @return date
     */
    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }

    /**
     * 添加或减去小时
     *
     * @param date   Date对象
     * @param amount 增量用正数，否则用负数
     * @return date
     */
    public static Date addHour(Date date, int amount) {
        return add(date, Calendar.HOUR, amount);
    }

    /**
     * 添加或减去分钟
     *
     * @param date   Date对象
     * @param amount 增量用正数，否则用负数
     * @return date
     */
    public static Date addMinute(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 添加或减去分钟
     *
     * @param date   Date对象
     * @param amount 增量用正数，否则用负数
     * @return date
     */
    public static Date addSecond(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }
}
