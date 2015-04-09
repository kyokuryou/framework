package org.smarty.core.support.jdbc.reader;

import java.security.AccessControlException;
import java.util.Map;


/**
 * 读取的文件中可以有变量,命令; 命令参数中用表达式
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public interface ISQLLink {

    /**
     * 文件使用的编码
     */
    String SQL_FILE_CODE = "UTF-8";

    /**
     * 设置sql文件中的${..}变量的值,变量名必须符合Java属性命名规范
     *
     * @param name  - 被替换的变量名
     * @param value - 替换后的值
     */
    public void set(String name, Object value);

    /**
     * 返回拼装好的sql,其中的变量使用set()提前设置好
     * <b>如果变量没有改变, 不要频繁调用该方法, 而是缓存结果</b>
     *
     * @throws java.security.AccessControlException - 如果文件校验错误抛出改异常
     */
    public String getResultSql(Map<String, Object> data) throws AccessControlException;

    /**
     * 设置文件锁定参数, 如果文件经过校验算法后与该值不同, getResultSql()会抛出异常
     *
     * @param protectKey - 文件校验码
     */
    public void lockFile(long protectKey);
}
