package org.smarty.core.support.jdbc.mapper;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.smarty.core.exception.NoSuchReflectException;
import org.smarty.core.io.RuntimeLogger;
import org.smarty.core.utils.BeanUtil;
import org.smarty.core.utils.CommonUtil;
import org.smarty.core.utils.ConvertUtil;
import org.smarty.core.utils.JdbcUtil;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 映射一行数据,以Spring bean形式创建
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class ElementMapperHandler implements RowMapperHandler<Element> {
    private static RuntimeLogger logger = new RuntimeLogger(ElementMapperHandler.class);
    private Class superClass;

    public ElementMapperHandler(Class superClass) {
        this.superClass = superClass;
    }

    /**
     * 映射一行数据
     *
     * @param rs ResultSet
     * @return Element
     * @throws java.sql.SQLException
     */
    public Element rowMapper(ResultSet rs) throws SQLException {
        Element bean = getBeanElement(superClass, rs.getRow());

        ResultSetMetaData rsm = rs.getMetaData();
        int cc = rsm.getColumnCount();
        for (int i = 1; i <= cc; i++) {
            String cn = JdbcUtil.getResultSetColumnName(rsm, i);
            setObject(rs, cn, i, bean);
        }
        return bean;
    }

    private void setObject(ResultSet rs, String cn, int index, Element bean) throws SQLException {
        String key = CommonUtil.toJavaField(cn);
        try {
            Class keyType = BeanUtil.getFieldClass(superClass, key);
            Object value = JdbcUtil.getResultSetValue(rs, index);
            if (value == null) return;
            Element proEl = getBeanProperty(key, value, keyType);
            if (proEl == null) return;
            bean.add(proEl);
        } catch (NoSuchReflectException e) {
            logger.out(e);
        }
    }

    /**
     * 创建一个Bean节点
     *
     * @return
     */
    private Element getBeanElement(Class klass, Integer code) {
        String name = klass.getSimpleName();
        Element bean = DocumentHelper.createElement("bean");
        bean.addAttribute("id", CommonUtil.firstLower(name + code));
        bean.addAttribute("class", klass.getName());
        return bean;
    }

    /**
     * 创建属性
     *
     * @return
     */
    private Element getBeanProperty(String name, Object value, Class valueType) {
        Class klass = value.getClass();
        Element properties = DocumentHelper.createElement("property");
        properties.addAttribute("name", name);

        if (klass.isArray()) {
            Element element = properties.addElement("array");
            element.addAttribute("value-type", ConvertUtil.getClassName(valueType));
            int arrLen = Array.getLength(value);
            for (int i = 0; i < arrLen; i++) {
                Element val = element.addElement("value");
                val.setText(ConvertUtil.toString(Array.get(value, i)));
            }
        } else {
            Element val = properties.addElement("value");
            val.addAttribute("type", ConvertUtil.getClassName(valueType));
            val.setText(ConvertUtil.toString(value));
        }
        return properties;
    }
}