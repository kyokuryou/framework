package org.core.utils;

import org.core.logger.RuntimeLogger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 公用工具
 */
public class CommonUtil {
    private static RuntimeLogger logger = new RuntimeLogger(CommonUtil.class);

    private CommonUtil() {
    }

    /**
     * 将指定数组的指定范围复制到一个新数组。该范围的初始索引 (from) 必须位于 0 和 dataArray.length（包括）之间。
     *
     * @param array 将要从其复制一个范围的数组
     * @param from  要复制的范围的初始索引（包括）
     * @param to    要复制的范围的最后索引（不包括）。（此索引可能位于数组范围之外）。
     * @return 包含取自原数组指定范围的新数组，截取或用 false 元素填充以获得所需长度
     */
    public static Object copyArray(Object array, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        int len = Array.getLength(array);
        Class ct = array.getClass().getComponentType();
        Object copy = Array.newInstance(ct, newLength);
        System.arraycopy(array, from, copy, 0, Math.min(len - from, newLength));
        return copy;
    }

    /**
     * 随机获取UUID字符串(无中划线)
     *
     * @return UUID字符串
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("[-]", "");
    }

    /**
     * 获得当前时间字符串,并在后面追加extLen长度的随机数字
     *
     * @param extLen 随机数字长度
     * @return 时间字符串
     */
    public static String getDateRandom(int extLen) {
        String str = DateUtil.format(new Date(), "yyyyMMddHHmmssS");
        return str + getRandomString(extLen);
    }

    /**
     * 随机获取字符串
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        if (length <= 0) {
            return "";
        }
        char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
        }
        return sb.toString();
    }

    /**
     * MD5加密
     *
     * @param text 内容
     * @return 加密内容
     */
    public static String md5(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            return toHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 将md转换Hex字符串
     *
     * @param md 要转换的MD
     * @return Hex字符串
     */
    private static String toHexString(byte[] md) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int j = hexDigits.length;
        char[] str = new char[j * 2];
        for (int i = 0; i < j; i++) {
            byte byteo = md[i];
            str[2 * i] = hexDigits[byteo >>> 4 & 0xf];
            str[2 * i + 1] = hexDigits[byteo & 0xf];
        }
        return new String(str);
    }

    /**
     * 根据指定长度 分隔字符串
     *
     * @param str       需要处理的字符串
     * @param separator 分隔符
     * @return 字符串集合
     */
    public static List<String> splitString(String str, String separator) {
        List<String> list = new ArrayList<String>();
        String[] strs = str.split(separator);
        for (String s : strs) {
            if (LogicUtil.isNotEmpty(s)) {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 将List转化为字符串，以分隔符间隔.
     *
     * @param list      需要处理的List.
     * @param separator 分隔符.
     * @return 转化后的字符串
     */
    public static String listToString(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(separator);
            sb.append(str);
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    /**
     * 将List转化为可执行的SQL in 语法
     *
     * @param list 需要处理的List.
     * @return 可执行的SQL in 语法
     */
    public static String listToSQLString(List<?> list) {
        StringBuilder sb = new StringBuilder();
        for (Object str : list) {
            if (LogicUtil.isPrimitive(str)) {
                sb.append(",");
                sb.append("'");
                sb.append(str);
                sb.append("'");
            }
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    /**
     * 字符串合并
     *
     * @param src    源
     * @param desc   目标
     * @param spread 合并后分割符号
     * @return string
     */
    public static String stringMerger(String[] src, String[] desc, String spread) {
        if (LogicUtil.isEmptyArray(src) || LogicUtil.isEmptyArray(desc)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (String dval : desc) {
            sb.append(dval).append(spread);
        }

        for (int i = desc.length; i < src.length; i++) {
            if (!"impl".equals(src[i])) {
                sb.append(src[i]).append(spread);
            }
        }
        return sb.delete(sb.lastIndexOf(spread), sb.length()).toString();

    }

    /**
     * 获得给定文件的后缀名
     *
     * @param fileName 文件名
     * @return 文件后缀名
     */
    public static String getExt(String fileName) {
        if (LogicUtil.isNotEmpty(fileName)) {
            return fileName.substring(fileName.lastIndexOf(".") + 1, fileName
                    .length());
        }
        return null;
    }

    /**
     * 生成短信验证码 随即6位数字
     *
     * @return 短信验证码
     */
    public static String makeMobileCaptcha() {
        String[] arr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Random random = new Random();
        StringBuilder bu = new StringBuilder();
        while (bu.length() < 6) {
            String temp = arr[random.nextInt(10)];
            if (bu.indexOf(temp) == -1) {
                bu.append(temp);
            }
        }
        return bu.toString();
    }

    /**
     * 替换关键字
     *
     * @param msg  字符串
     * @param args 占位符替换数组
     * @return String
     */
    public static String getMsgPlaceholder(String msg, Object... args) {
        if (LogicUtil.isEmpty(msg)) {
            return null;
        }
        for (int i = 0; i < args.length; i++) {
            int cos = msg.indexOf("${" + i + "}");
            if (cos != -1) {
                msg = msg.replace("${" + i + "}", args[i].toString());
            }
        }
        return msg;
    }

    /**
     * String 转换 Long 数组
     *
     * @param strArr 字符串id
     * @param reg    分隔符
     * @return Long 数组
     */
    public static Long[] stringToLongArray(String strArr, String reg) {
        String[] tids = strArr.split(reg);
        Long[] ltids = new Long[tids.length];
        for (int i = 0; i < tids.length; i++) {
            ltids[i] = Long.parseLong(tids[i]);
        }
        return ltids;
    }

    /**
     * 转换字符编码
     *
     * @param str     字符串
     * @param strCode 字符编码
     * @return 转换后的字符串
     */
    public static String toURLCode(String str, String strCode) {
        String str1 = "";
        try {
            str = new String(str.getBytes(), strCode);
        } catch (UnsupportedEncodingException e) {
            logger.out(e);
        }
        for (int i = 0; i < str.length(); ++i) {
            char c1 = str.charAt(i);
            if (c1 > 128) {
                String str2 = Integer.toString(c1);
                str1 = str1 + "&#" + str2 + ";";
            } else {
                str1 = str1 + c1;
            }
        }
        return str1;
    }

    /**
     * 将str转换成strCode编码
     *
     * @param str     字符串
     * @param strCode 字符编码
     * @return 转换后的字符串
     */
    public static String toUnicode(String str, String strCode) {
        String str1 = "";
        try {
            str = new String(str.getBytes(), strCode);
        } catch (UnsupportedEncodingException e) {
            logger.out(e);
        }
        for (int i = 0; i < str.length(); ++i) {
            char c1 = str.charAt(i);
            if (c1 > 128) {
                String str2 = Integer.toHexString(c1);
                str1 = str1 + "&#x" + str2 + ";";
            } else {
                str1 = str1 + c1;
            }
        }
        return str1;
    }

    /**
     * 将str转换成GBK
     *
     * @param str 字符串
     * @return gbk字符串
     */
    public static String toGBK(String str, String code) {
        String str1 = "";
        try {
            str = new String(str.getBytes(), code);
        } catch (UnsupportedEncodingException e) {
            logger.out(e);
        }
        for (int i = 0; i < str.length(); ++i) {
            char c1 = str.charAt(i);
            if (c1 > 128) {
                String str2 = Integer.toHexString(c1);
                str1 = str1 + "&#x" + str2 + ";";
            } else {
                str1 = str1 + c1;
            }
        }
        return str1;
    }

    /**
     * 将s字符串首字母大写
     *
     * @param s 字符串
     * @return 转换后的字符串
     */
    public static String firstUpper(String s) {
        char[] cs = s.toCharArray();
        cs[0] = Character.toUpperCase(cs[0]);
        return new String(cs);
    }

    /**
     * 将s字符串首字母小写
     *
     * @param s 字符串
     * @return 转换后的字符串
     */
    public static String firstLower(String s) {
        char[] cs = s.toCharArray();
        cs[0] = Character.toLowerCase(cs[0]);
        return new String(cs);
    }

    /**
     * 处理成标准JavaBean命名方式
     *
     * @param name 需转换的字段名所表示的字符串(如:name或name_name)
     * @return 字段名
     */
    public static String toJavaField(String name) {
        if (name == null || "".equals(name)) {
            return "";
        }
        String[] fns = name.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fns.length; i++) {
            char[] fn = fns[i].toCharArray();
            if (i > 0) {
                fn[0] = Character.toUpperCase(fn[0]);
            }
            sb.append(fn);
        }
        return sb.toString();
    }

    /**
     * 处理成DB命名方式
     *
     * @param name 需转换的字段名所表示的字符串(如:name、nameName、NameName)
     * @return 字段名
     */
    public static String toDBField(String name) {
        if (name == null || "".equals(name)) {
            return "";
        }
        char[] cs = name.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cs.length; i++) {
            if (i > 0 && cs[i] >= 65 && cs[i] <= 90) {
                sb.append("_");
            }
            sb.append(Character.toUpperCase(cs[i]));
        }
        return sb.toString();
    }

    /**
     * 字节分组
     *
     * @param bytes 字节
     * @param len   每组长度
     * @return 组数
     */
    public static int getGroupCount(byte[] bytes, int len) {
        int blen = bytes.length;
        if (blen < len) {
            return 1;
        }
        if (blen % len == 0) {
            return blen / len;
        } else {
            return blen / len + 1;
        }
    }
}