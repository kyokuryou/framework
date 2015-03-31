package org.core.jdbc.reader;

import org.core.jdbc.support.SQLTypeEnum;
import org.core.logger.RuntimeLogger;
import org.core.support.jdbc.reader.XmlSQLLink;
import org.core.utils.LogicUtil;

import java.io.IOException;
import java.util.Map;

/**
 * 一个简单对sql.xml读取
 */
public class XmlSQLReader extends AbstractSQLReader {
    private static RuntimeLogger logger = new RuntimeLogger(XmlSQLReader.class);
    private XmlSQLLink xsqlLink;

    /**
     * 文件名以文件名读取 如:org/mapper/xx/ClassNameMapper.xml
     *
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        try {
            if (LogicUtil.isNotEmpty(fileName)) {
                xsqlLink = new XmlSQLLink(fileName);
            } else {
                logger.out(fileName + "未找到");
            }
        } catch (IOException e) {
            logger.out(e);
        }
    }

    /**
     * 读取并格式化SQL文
     *
     * @param date validate数据
     * @return SQL
     */
    @Override
    public String read(Map<String, Object> date) {
        return simplifySQL(xsqlLink.getResultSql(date));
    }

    /**
     * 设置SQLType,节点名
     *
     * @param typeStr SQLType
     * @param nodeStr 节点名
     */
    @Override
    public void setParams(SQLTypeEnum typeStr, String nodeStr) {
        xsqlLink.set("sn", nodeStr);
        if (typeStr != null) {
            xsqlLink.set("st", typeStr.name());
        }
    }
}
