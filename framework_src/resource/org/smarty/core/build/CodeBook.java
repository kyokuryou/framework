package org.smarty.core.config;

/**
 * 常量定义
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/13
 * Update Date: 2013/12/13
 */
public interface CodeBook {
    // config.local.properties
    public String SRC_CONFIG = "config.local.properties.template";
    // config.local.properties-jdbc.config
    public String SRC_CONFIG_JDBC = "config_local/jdbc.config";
    // config.local.properties-jndi.config
    public String SRC_CONFIG_JNDI = "config_local/jndi.config";
    // spring.xml
    public String SRC_SPRING = "spring.xml.template";
    // log4j.properties
    public String SRC_LOG4J = "log4j.properties.template";
    // spring_ws.xml
    public String SRC_WS = "spring_ws.xml.template";
    // struts.xml
    public String SRC_STRUTS2 = "struts.xml.template";
    // spring.xml-jdbc.config
    public String SRC_SPRING_JDBC = "spring_xml/jdbc.config";
    // spring.xml-jndi.config
    public String SRC_SPRING_JNDI = "spring_xml/jndi.config";
    // spring.xml-ws.config
    public String SRC_SPRING_WS = "spring_xml/ws.config";
    // spring.xml-jCaptcha.config
    public String SRC_SPRING_JCAPTCHA = "spring_xml/jCaptcha.config";
    // web.xml
    public String WEB_XML = "web.xml.template";
    // web.xml-errorPage.config
    public String WEB_XML_ERRORPAGE = "web_xml/errorPage.config";
    // web.xml-jCaptcha.config
    public String WEB_XML_JCAPTCHA = "web_xml/jCaptcha.config";
    // web.xml-flex.config
    public String WEB_XML_FLEX = "web_xml/flex.config";
    // web.xml-struts2.config
    public String WEB_XML_STRUTS2 = "web_xml/struts2.config";
    // web.xml-welcome.config
    public String WEB_XML_WELCOME = "web_xml/welcome.config";
    // web.xml-xfire.config
    public String WEB_XML_XFIRE = "web_xml/xfire.config";
    // flex->messaging-config.xml
    public String WEB_FLEX_MESSAGING = "messaging-config.xml.template";
    // flex->remoting-config.xml
    public String WEB_FLEX_REMOTING = "remoting-config.xml.template";
    // flex->services-config.xml
    public String WEB_FLEX_SERVICES = "services-config.xml.template";
    // src配置文件模板目录
    public String SRC_PATH = "config/src/";
    // web配置文件模板目录
    public String WEB_PATH = "config/web/";

    // web版本
    public enum WebVersion {
        V2_4, V2_5, V3_0;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            char[] chs = name().toCharArray();
            sb.append(chs[1]);
            sb.append(".");
            sb.append(chs[3]);
            return sb.toString();
        }
    }

    // 启用web端应用key
    public String ENABLED_WEB = "enabledWeb";
    // 发布目录key
    public String TARGET_BASIC = "targetBasic";
    // java源码目录key
    public String TARGET_SRC = "targetSrc";
    // web源码目录key
    public String TARGET_WEB = "targetWeb";
    // web版本key
    public String WEB_VERSION = "webVersion";
    // 依赖列表key
    public String COMPONENT = "component";
    // 数据库连接key
    public String DB_CONNECTION = "dbconnection";
    // 包信息key
    public String PACKAGE_INFO = "packageinfo";

    // config.local相关文件信息
    public String LOCAL = "local";
    public String LOCAL_KEY = "lcFile";
    public String LOCAL_FILE = "config.local.properties";
    public String LOCAL_DS_JNDI = "jndi";
    public String LOCAL_DS_JDBC = "jdbc";
    public String LOCAL_DS_KEY = "dsFile";

    // flex相关文件信息
    public String FLEX = "flex";
    public String FLEX_KEY = "fFile";
    public String FLEX_SERVICES_FILE = "WEB-INF/flex/services-config.xml";
    public String FLEX_MESSAGING_FILE = "WEB-INF/flex/messaging-config.xml";
    public String FLEX_REMOTING_FILE = "WEB-INF/flex/remoting-config.xml";

    //jCaptcha配置
    public String JCAPTCHA = "jCaptcha";
    public String JCAPTCHA_KEY = "jcFile";

    // log4j相关文件信息
    public String LOG4J = "log4j";
    public String LOG4J_KEY = "l4File";
    public String LOG4J_FILE = "log4j.properties";
    // spring相关文件信息
    public String SPRING = "spring";
    public String SPRING_KEY = "sFile";
    public String SPRING_FILE = "spring.xml";
    public String SPRING_DS_NAME = "dsname";
    public String SPRING_DS_JNDI = "jndi";
    public String SPRING_DS_JDBC = "jdbc";
    public String SPRING_DS_KEY = "dsFile";

    // xfire相关文件信息
    public String XFIRE = "xfire";
    public String XFIRE_KEY = "wsFile";
    public String XFIRE_FILE = "spring_ws.xml";

    // struts2相关文件信息
    public String STRUTS2 = "struts2";
    public String STRUTS2_KEY = "s2File";
    public String STRUTS2_FILE = "struts.xml";

    // web相关文件信息
    public String WEB = "WEB-INF/web.xml";
    public String WEB_VERSION_KEY = "version";
    public String WEB_WELCOME = "welcome";
    public String WEB_WELCOME_KEY = "wcFile";
    public String WEB_ERROR_PAGE = "errorPage";
    public String WEB_ERROR_PAGE_KEY = "epFile";

    public String WEB_XMLNS_KEY = "xmlns";
    public String WEB_XMLNS_XSI_KEY = "xmlnsXsi";
    public String WEB_XSI_SCHEMA_LOCATION_KEY = "xsiSchemaLocation";
    public String WEB_LOCATION_KEY = "location";

    public String WEB_V2_4_XMLNS = "http://java.sun.com/xml/ns/j2ee";
    public String WEB_V2_4_XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public String WEB_V2_4_XSI_SCHEMA_LOCATION = "http://java.sun.com/xml/ns/j2ee";
    public String WEB_V2_4_LOCATION = "http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd";

    public String WEB_V2_5_XMLNS = "http://java.sun.com/xml/ns/javaee";
    public String WEB_V2_5_XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public String WEB_V2_5_XSI_SCHEMA_LOCATION = "http://java.sun.com/xml/ns/javaee";
    public String WEB_V2_5_LOCATION = "http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd";

    public String WEB_V3_0_XMLNS = "http://java.sun.com/xml/ns/javaee";
    public String WEB_V3_0_XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public String WEB_V3_0_XSI_SCHEMA_LOCATION = "http://java.sun.com/xml/ns/javaee";
    public String WEB_V3_0_LOCATION = "http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd";

}
