package org.core.support.jdbc.reader;

import org.core.exception.CacheNameNotExistException;
import org.core.logger.RuntimeLogger;
import org.core.support.cache.CacheMessage;
import org.core.utils.CommonUtil;
import org.core.utils.DocumentUtil;
import org.core.utils.LogicUtil;
import org.core.utils.PathUtil;
import org.core.utils.RegexUtil;
import org.core.utils.SmartyUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取sql模板文件, 处理动态变量, 解析表达式和命令, 返回处理后的sql
 */
public class XmlSQLLink implements ISQLLink {
    private final RuntimeLogger logger = new RuntimeLogger(XmlSQLLink.class);
    private Map<String, Object> vars;
    private URL url;

    /**
     * filename 必须是绝对路径使用 '/../../...' 格式
     *
     * @throws java.io.IOException
     */
    public XmlSQLLink(String file) throws IOException {
        this(PathUtil.getResourceAsURL(file));
    }

    /**
     * filename 必须是绝对路径使用 '/../../...' 格式
     *
     * @throws java.io.IOException
     */
    public XmlSQLLink(URL url) throws IOException {
        if (url == null) {
            throw new IOException("找不到文件");
        }
        this.url = url;
        vars = new HashMap<String, Object>();
    }


    /**
     * 打开Document
     *
     * @return Document
     */
    private Document openRead() {
        SAXReader saxReader = new SAXReader();
        try {
            return saxReader.read(url);
        } catch (DocumentException e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 返回SQL
     *
     * @param data 置换表达式数据
     * @return SQL
     */
    public String getResultSql(Map<String, Object> data) {
        return SmartyUtil.render(getSQL(), data);
    }

    /**
     * 获得SQL
     *
     * @return SQL
     */
    private String getSQL() {
        if (!vars.containsKey("sn")) {
            return "";
        }

        String cacheKey = CommonUtil.md5(getXPath(url.getFile()));

        String sql = null;
        try {
            sql = (String) CacheMessage.getCache(cacheKey);
        } catch (CacheNameNotExistException e) {

            Document document = openRead();
            if (document != null) {
                sql = readSQL(document);
            }
            CacheMessage.putCache(cacheKey, sql);
        }
        return sql;
    }

    /**
     * 读取XML文件中的SQL文,如果,SQLType在XML中未指定,将读取直接父级的文本域
     *
     * @param document document
     * @return SQL
     */
    private String readSQL(Document document) {
        String sql = "";
        String xpath = getXPath("");

        if (vars.containsKey("st")) {
            sql = DocumentUtil.readElement(document, xpath);
        }

        if (LogicUtil.isEmpty(sql)) {
            sql = DocumentUtil.readParentElement(document, xpath);
        }
        if (LogicUtil.isNotEmpty(sql)) {
            return RegexUtil.convertSQL(sql);
        }
        return "";
    }

    /**
     * 构建XPath
     *
     * @param prefix 前缀
     * @return XPath
     */
    public String getXPath(String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append("/sqls/");
        sb.append(vars.get("sn"));
        sb.append("/");
        sb.append(vars.get("st"));
        return sb.toString();
    }

    /**
     * 设置参数
     *
     * @param name  - 被替换的变量名
     * @param value - 替换后的值
     */
    public void set(String name, Object value) {
        if (LogicUtil.isEmpty(name)) {
            return;
        }
        if ("sn".equals(name)) {
            vars.put(name, value.toString());
        } else if ("st".equals(name) && value != null) {
            vars.put(name, value.toString());
        }
    }

    public void lockFile(long protectKey) {
        // this.protectKey = protectKey;
    }
}
