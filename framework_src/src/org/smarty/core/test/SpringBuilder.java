package org.smarty.core.test;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.smarty.core.io.ModelSerializable;
import org.smarty.core.support.jdbc.SQLSession;
import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.support.jdbc.support.DBType;
import org.smarty.core.utils.PathUtil;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 创建SpringBean文件
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class SpringBuilder {
	private SQLSession sqlSession;
	private String outPath = "";

	/**
	 * 初始化并初始化初始化数据库连接
	 *
	 * @param dataSource 数据库连接
	 */
	public SpringBuilder(DataSource dataSource, String outPath) {
		this(dataSource, DBType.DB2, outPath);
	}

	/**
	 * 初始化并初始化初始化数据库连接
	 *
	 * @param dataSource 数据库连接
	 */
	public SpringBuilder(DataSource dataSource, DBType sqlType, String outPath) {
		if (dataSource != null) {
			sqlSession = new SQLSession(dataSource, sqlType);

		}
		this.outPath = outPath;
	}

	/**
	 * 初始化并创建连接
	 *
	 * @param url      连接
	 * @param userName 用户名
	 * @param pwd      密码
	 */
	public SpringBuilder(String url, String userName, String pwd) {
		sqlSession = new SQLSession(new DriverManagerDataSource(url, userName, pwd), DBType.MySQL);

	}

	public <E extends ModelSerializable> void outSpringXml(SQL sql, Class<E> klass) throws SQLException {
		// List<Element> eList = sqlSession.queryForElementList(sql, klass);
		String pn = klass.getPackage().getName();
		String name = klass.getSimpleName();
		String filePath = outPath + "/" + pn.replaceAll("[.]", "/");
		String fileName = "/" + "spring-" + firstDown(name) + ".xml";

		File file = new File(filePath, fileName);
		if (file.exists())
			file.mkdirs();
		// doRun(file, eList, klass);
	}

	/**
	 * 生成spring.xml文件.
	 *
	 * @param file     文件名
	 * @param beanList 集合(不支持非标准JavaBean和JavaBean内部集合)
	 */
	public void doRun(File file, List<Element> beanList, Class klass) {
		Document document = DocumentHelper.createDocument();
		Element rootElement = getRootElement();
		for (Element bean : beanList) {
			rootElement.add(bean);
		}
		Element listEl = getLinkedList(klass, beanList.toArray(new Element[beanList.size()]));
		rootElement.add(listEl);
		rootElement.add(getImport());
		document.add(rootElement);
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(file));
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建Xml文件
	 *
	 * @param fileName
	 * @return
	 */
	private File getFile(String fileName) {
		return PathUtil.getResourceAsFile(outPath + fileName);
	}

	/**
	 * 创建一个根
	 *
	 * @return
	 */
	private Element getRootElement() {
		Element root = DocumentHelper.createElement("beans");
		root.addAttribute("xmlns ", "http://www.springframework.org/schema/beans");
		root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.addAttribute("xsi:schemaLocation", "http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd");
		return root;
	}

	/**
	 * 创建一个Bean节点
	 *
	 * @return
	 */
	private Element getBeanElement(Class klass, Integer code) {
		String name = klass.getSimpleName();
		Element bean = DocumentHelper.createElement("bean");
		bean.addAttribute("id", firstDown(name + code));
		bean.addAttribute("class", klass.getName());
		return bean;
	}

	public static String firstDown(String s) {
		char[] cs = s.toCharArray();
		cs[0] = Character.toLowerCase(cs[0]);
		return new String(cs);
	}

	/**
	 * 创建List集合
	 *
	 * @param elements
	 * @return
	 */
	private Element getLinkedList(Class klass, Element... elements) {
		Element beanEl = DocumentHelper.createElement("bean");
		beanEl.addAttribute("id", firstDown(klass.getSimpleName()) + "List");
		beanEl.addAttribute("class", "java.util.LinkedList");

		Element argEl = beanEl.addElement("constructor-arg");
		Element listEl = argEl.addElement("list");
		listEl.addAttribute("value-type", klass.getName());
		for (Element el : elements) {
			Attribute beanIdName = el.attribute("id");
			Element refEl = listEl.addElement("ref");
			refEl.addAttribute("bean", beanIdName.getValue());
		}
		return beanEl;
	}

	private Element getImport() {
		Element importEl = DocumentHelper.createElement("import");
		importEl.addAttribute("resource", "classpath*:org/core/test/spring-type.xml");
		return importEl;
	}
}