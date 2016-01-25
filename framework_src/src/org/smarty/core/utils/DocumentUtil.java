package org.smarty.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.smarty.core.common.BaseConstant;
import org.xml.sax.SAXException;

/**
 * xml工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class DocumentUtil {
    private static Log logger = LogFactory.getLog(DocumentUtil.class);
    private final static String xsd = "";

    private DocumentUtil() {
    }

    public static Document createDocument(String srcXml) {
        try {
            return DocumentHelper.parseText(srcXml);
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 获得Document
     *
     * @param file 文件名
     * @return Document
     */
    public static Document getDocument(File file) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(file);
        } catch (Exception e) {
            logger.warn(e);
        }
        return document;
    }

    /**
     * 将Document输出至file.xml文件
     *
     * @param file     文件名
     * @param document Document对象
     */
    public static void writerDocument(File file, Document document) {
        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new XMLWriter(new FileOutputStream(file), DocumentUtil.getOutputFormat());
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            DocumentUtil.closeWriter(xmlWriter);
        }
    }

    /**
     * 在xml的指定位置插入一个元素
     *
     * @param srcXml  原xml
     * @param nodeXml 元素xml
     * @param xpath   要插入元素父节点的位置
     * @return 原xml插入节点后的完整xml文档
     */
    public static String addElement(String srcXml, String nodeXml, String xpath) {
        try {
            Document docSrc = DocumentHelper.parseText(srcXml);
            Document docNode = DocumentHelper.parseText(nodeXml);
            Element parentElement = (Element) docSrc.getRootElement().selectSingleNode(xpath);
            parentElement.add(docNode.getRootElement());
            return docSrc.asXML();
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 删除xml文档中指定ID的元素
     *
     * @param srcXml    原xml
     * @param xmlNodeId 元素ID属性值
     * @return 删除元素后的xml文档
     */
    public static String removeElementById(String srcXml, String xmlNodeId) {

        try {
            Document docSrc = DocumentHelper.parseText(srcXml);
            Element removeElement = docSrc.getRootElement().elementByID(xmlNodeId);
            removeElement.detach(); // 直接删除自己
            return docSrc.asXML();
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 删除xml文档中以xpath为直接父节点的ID属性为空的子节点，ID属性为空包括值为空、空串、或者ID属性不存在。
     *
     * @param srcXml 原xml文档
     * @param xpath  要删除空节点的所在父节点的xpath
     * @return 删除空节点后的xml文档
     */
    public static String removeNullIdElement(String srcXml, String xpath) {
        try {
            Document srcDoc = DocumentHelper.parseText(srcXml);
            removeNullIdElement(srcDoc, xpath);
            return srcDoc.asXML();
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 删除xml文档中以xpath为直接父节点的ID属性为空的子节点，ID属性为空包括值为空、空串、或者ID属性不存在。
     *
     * @param srcDoc 原xml的Document对象
     * @param xpath  要删除空节点的所在父节点的xpath
     * @return 删除空节点后的xml文档
     */
    @SuppressWarnings("unchecked")
    public static Document removeNullIdElement(Document srcDoc, String xpath) {
        Node parentNode = srcDoc.getRootElement().selectSingleNode(xpath);
        if (!(parentNode instanceof Element)) {
            logger.warn("所传入的xpath不是Elementpath，删除空节点失败！");
            return null;
        } else {
            Iterator<Element> it = ((Element) parentNode).elementIterator();
            while (it.hasNext()) {
                Element element = it.next();
                if (element.attribute("ID") == null) {
                    element.detach();
                } else {
                    if (LogicUtil.isEmpty(element.attribute("ID").getValue())) {
                        element.detach();
                    }
                }
            }
        }
        return srcDoc;
    }

    /**
     * 删除xml文档中指定xpath路径下所有直接子节点为空的节点
     *
     * @param srcXml    原xml文档
     * @param xpathList xpaht列表
     * @return 删除空节点后的xml文档
     */
    public static String removeAllNullIdElement(String srcXml, ArrayList<String> xpathList) {
        try {
            Document srcDoc = DocumentHelper.parseText(srcXml);
            for (String xpath : xpathList) {
                removeNullIdElement(srcDoc, xpath);
            }
            return srcDoc.asXML();
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 更新xml文档中指定ID的元素,ID保持不变
     *
     * @param srcXml     原xml
     * @param newNodeXml 新xml节点
     * @param xmlNodeId  更新元素ID属性值
     * @return 更新元素后的xml文档
     */
    public static String updateElementById(String srcXml, String newNodeXml, String xmlNodeId) {
        try {
            Document docSrc = DocumentHelper.parseText(srcXml);
            Document newDocNode = DocumentHelper.parseText(newNodeXml);
            // 获取要更新的目标节点
            Element updatedNode = docSrc.elementByID(xmlNodeId);
            // 获取更新目标节点的父节点
            Element parentUpNode = updatedNode.getParent();
            // 删除掉要更新的节点
            parentUpNode.remove(updatedNode);

            // 获取新节点的根节点（作为写入节点）
            Element newRoot = newDocNode.getRootElement();
            // 处理新节点的ID属性值和BS子元素的值
            if (newRoot.attribute("ID") == null) {
                newRoot.addAttribute("ID", xmlNodeId);
            } else {
                newRoot.attribute("ID").setValue(xmlNodeId);
            }
            // 在原文档中更新位置写入新节点
            parentUpNode.add(newRoot);
            return docSrc.asXML();
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;

    }

    /**
     * 更新xml文档中指定ID的元素,并检查ID和BS，加以设置
     *
     * @param srcXml     原xml
     * @param newNodeXml 新xml节点
     * @param xmlNodeId  更新元素ID属性值
     * @return 更新元素后的xml文档
     */
    public static String updateElementByIdAddIdBs(String srcXml, String newNodeXml, String xmlNodeId) {
        try {
            Document docSrc = DocumentHelper.parseText(srcXml);
            Document newDocNode = DocumentHelper.parseText(newNodeXml);
            // 获取要更新的目标节点
            Element updatedNode = docSrc.elementByID(xmlNodeId);
            // 获取更新目标节点的父节点
            Element parentUpNode = updatedNode.getParent();
            // 删除掉要更新的节点
            parentUpNode.remove(updatedNode);

            // 获取新节点的根节点（作为写入节点）
            Element newRoot = newDocNode.getRootElement();
            // 处理新节点的ID属性值和BS子元素的值
            if (newRoot.attribute("ID") == null) {
                newRoot.addAttribute("ID", xmlNodeId);
            } else {
                newRoot.attribute("ID").setValue(xmlNodeId);
            }
            if (newRoot.element("BS") == null) {
                newRoot.addElement("BS", xmlNodeId);
            } else {
                newRoot.element("BS").setText(xmlNodeId);
            }
            // 在原文档中更新位置写入新节点
            parentUpNode.add(newRoot);
            return docSrc.asXML();
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 为xml元素设置ID属性
     *
     * @param xmlElement 原xml元素
     * @return 设置id后的xml串
     */
    public static String addIdAttribute(String xmlElement) {
        try {
            Document srcDoc = DocumentHelper.parseText(xmlElement);
            Element root = srcDoc.getRootElement();
            // Long nextValue = SequenceUtils.getSequeceNextValue();
            Long nextValue = new Random().nextLong();
            root.addAttribute("ID", nextValue.toString());
            return root.asXML();
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 为xml元素设置ID属性,并将此属性写入一个指定子节点文本值域
     *
     * @param xmlElement 原xml元素
     * @param nodeName   （直接）子节点的名称，或相对当前节点的xpath路径
     * @return 设置id和子节点后的xml串
     */
    public static String addIdAndWriteNode(String xmlElement, String nodeName) {
        try {
            Document srcDoc = DocumentHelper.parseText(xmlElement);
            Element root = srcDoc.getRootElement();
            // Long nextValue = SequenceUtils.getSequeceNextValue();
            Long nextValue = new Random().nextLong();
            root.addAttribute("ID", nextValue.toString());
            Node bsElement = root.selectSingleNode(nodeName);
            if (bsElement instanceof Element) {
                bsElement.setText(nextValue.toString());
            } else {
                root.addElement(nodeName).setText(nextValue.toString());
            }
            return root.asXML();
        } catch (DocumentException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 读取指定xpath路径下节点文本值域
     *
     * @param srcDoc Document对象
     * @param xpath  节点的位置
     * @return xpath路径下节点文本值域
     */
    public static String readElement(Document srcDoc, String xpath) {
        Node node = srcDoc.getRootElement().selectSingleNode(xpath);
        if (!(node instanceof Element)) {
            return "";
        }
        return node.getText();
    }

    /**
     * 读取指定xpath路径下直接父级节点文本值域
     *
     * @param srcDoc Document对象
     * @param xpath  节点的位置
     * @return xpath路径下直接父级节点文本值域
     */
    public static String readParentElement(Document srcDoc, String xpath) {
        String x = xpath.substring(0, xpath.lastIndexOf("/"));
        Node parentNode = srcDoc.getRootElement().selectSingleNode(x);
        if (!(parentNode instanceof Element)) {
            return "";
        }
        return parentNode.getText();
    }

    /**
     * 定义文档的格式
     *
     * @return OutputFormat
     */
    public static OutputFormat getOutputFormat() {
        OutputFormat warnputFormat = OutputFormat.createPrettyPrint();// 设置XML文档输出格式
        warnputFormat.setEncoding(BaseConstant.DEF_ENCODE.name());// 设置XML文档的编码类型
        warnputFormat.setIndent(true);// 设置是否缩进
        warnputFormat.setIndent("	");// 以TAB方式实现缩进
        warnputFormat.setNewlines(true);// 设置是否换行
        return warnputFormat;
    }

    /**
     * 关闭字符流(输出)
     *
     * @param xmlWriter 输出流
     */
    public static void closeWriter(XMLWriter xmlWriter) {
        if (xmlWriter != null) {
            try {
                xmlWriter.close();
            } catch (IOException e) {
                logger.warn(e);
            }
        }
    }

    /**
     * 关闭字符流(输入)
     *
     * @param inputStream 输入流
     */
    public static void closeRead(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.warn(e);
            }
        }
    }

    /**
     * 创建Document验证器
     *
     * @return Document验证器
     * @throws org.xml.sax.SAXException
     */
    public static Validator getValidator() throws SAXException {
        File schemaLocation = null;
        Schema schema = null;
        SchemaFactory factory = SchemaFactory.newInstance(BaseConstant.DEF_XML_SCHEMA);
        if (LogicUtil.isNotEmpty(xsd)) {
            schemaLocation = new File(xsd);
        }

        if (schemaLocation != null && schemaLocation.isFile()) {
            schema = factory.newSchema(schemaLocation);
        } else {
            schema = factory.newSchema();
        }
        return schema.newValidator();

    }

}