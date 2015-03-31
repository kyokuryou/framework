package org.core.utils;

import org.core.logger.RuntimeLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 */
public class RegexUtil {
    private static RuntimeLogger logger = new RuntimeLogger(RegexUtil.class);
    /**
     * 制表符,换行符,换页符,回车符
     */
    private static final String SQLTNFR = "(?:--)|([\\t\\n\\f\\r])|(\\s{2,})";

    private static final String SQLPUNCT = "^[:\'\"][a-zA-Z\\p{Blank}]+$";
    /**
     * sql关键字
     */
    private final static String sqlKeywords = new StringBuilder("(")
            .append("(\\b)")
            .append("(create|drop|")
            .append("select|update|delete|insert|call|into|set|as|distinct|values|")
            .append("count|sum|max|min|avg|")
            .append("from|inner|left|right|outer|cross|straight|natural|join|on|")
            .append("where|")
            .append("and|or|like|not|is|between|in|")
            .append("group|by|")
            .append("having|")
            .append("order|")
            .append("limit|")
            .append("char|substr|declare|master|execute)")
            .append("(\\p{Blank})")
            .append(")")
            .toString();
    /**
     * 固定电话
     */
    private static final String telephone = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";
    /**
     * 手机
     */
    private static final String moblie = "^13[0-9]{9}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}$";
    /**
     * 电子邮箱
     */
    private static final String email = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    /**
     * 验证固定电话格式
     *
     * @param telephoneString
     * @return
     */
    public static boolean getTelephone(String telephoneString) {
        Pattern pattern = Pattern.compile(telephone);
        Matcher matcher = pattern.matcher(telephoneString);
        return matcher.find();
    }

    /**
     * 验证手机格式
     *
     * @param mobileString
     * @return
     */
    public static boolean getMoblie(String mobileString) {
        Pattern pattern = Pattern.compile(moblie);
        Matcher matcher = pattern.matcher(mobileString);
        return matcher.find();
    }

    /**
     * 验证邮箱格式
     *
     * @param emailString
     * @return
     */
    public static boolean getEmail(String emailString) {
        Pattern pattern = Pattern.compile(email);
        Matcher matcher = pattern.matcher(emailString);
        return matcher.find();
    }
    /**
     * 清除掉所有特殊字符 包括空格
     */
    private final static String stingSpecial = "[`~_ !@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * 定义HTML标签的正则表达式
     */
    private final static String regEx_html = "<[^>]+>";
    /**
     * 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
     */
    private final static String regScript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
    /**
     * 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
     */
    private final static String regStyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

    private RegexUtil() {
    }

    /**
     * 格式化SQL,SQL关键字大写转换
     *
     * @param sql SQL文
     * @return 处理完SQL文
     */
    public static String convertSQL(String sql) {
        sql = sql.replaceAll(SQLTNFR, " ").trim();
        Pattern pattern = Pattern.compile(sqlKeywords, Pattern.CASE_INSENSITIVE);
        StringBuilder res = new StringBuilder(sql);
        Matcher matchr = pattern.matcher(sql);
        while (matchr.find()) {
            int mstart = matchr.start();
            String mstr = matchr.group(1);
            int mend = mstr.length() + mstart;
            int substart = (mstart <= 0) ? mstart : mstart - 1;

            if (!isFirstPunct(res.substring(substart, mend))) {
                res.replace(mstart, mend, mstr.toUpperCase());
            }
        }
        return res.toString();
    }

    /**
     * 去除字符串两端空白字符(\t\r\0\n\f)
     *
     * @param value 字符串
     * @return 处理后的字符串
     */
    public static String trim(String value) {
        if (LogicUtil.isEmpty(value)) return value;
        int st = 0;
        int len = value.length();
        char[] val = value.toCharArray();
        // 字符串前端空白字符结束位置
        while (LogicUtil.isSpace(val[st])) {
            st++;
        }
        // 字符串后端空白字符起始位置
        while (LogicUtil.isSpace(val[len - 1])) {
            len--;
        }
        // 忽略两端空白字符切割字符串
        return (len > st) ? value.substring(st, len) : value;
    }

    /**
     * 检查是否包含（：‘“）
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isFirstPunct(String str) {
        if (LogicUtil.isEmpty(str)) {
            return false;
        }

        Pattern p = Pattern.compile(SQLPUNCT);
        Matcher m = p.matcher(str);
        return m.matches();

    }

    /**
     * 清除掉字符串中所有特殊字符 包括空格
     *
     * @param str 待处理的字符串
     * @return 返回处理后的结果字符串
     */
    public static String stringFilter(String str) {
        Pattern p = Pattern.compile(stingSpecial);
        Matcher m = p.matcher(str);// 找到所有匹配的字符
        String result = m.replaceAll("").trim();// 将所有匹配的字符都替换为空字符 并消除两端的空格
        return result.replace("-", "");
    }

    /**
     * HTML代码过滤出文本
     *
     * @param inputString 含html标签的字符串
     * @return 文本字符串
     */
    public static String parseHtmlToText(String inputString) {
        String htmlStr = inputString;
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        try {
            p_script = Pattern.compile(regScript, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regStyle, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            logger.out(e);
        }
        return textStr;
    }
}
