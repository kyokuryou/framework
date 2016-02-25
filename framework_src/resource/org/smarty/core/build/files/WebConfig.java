package org.smarty.core.config.files;

import config.FileWrapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.dom4j.Document;
import org.smarty.core.config.CodeBook;
import org.smarty.core.config.CodeBook.WebVersion;
import org.smarty.core.utils.DocumentUtil;
import org.smarty.core.utils.TemplateUtil;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/12
 * Update Date: 2013/12/12
 */
public class WebConfig extends FileConfig {
    private String webString;

    public void buildFile(Properties ps) throws IOException {
        Boolean ew = Boolean.valueOf(ps.getProperty(CodeBook.ENABLED_WEB));
        if (ew) {
            Map<String, Object> params = new HashMap<String, Object>(0);
            chooseWebXml(ps.getProperty(CodeBook.WEB_VERSION), params);
            params.put(CodeBook.SPRING_KEY, CodeBook.SPRING_FILE);
            params.put(CodeBook.LOG4J_KEY, CodeBook.LOG4J_FILE);
            setConfigFile(ps.getProperty(CodeBook.COMPONENT), params);
            try {
                String webXml = TemplateUtil.render(getString(), params);
                Document doc = DocumentUtil.createDocument(webXml);
                DocumentUtil.writerDocument(createFile(ps.getProperty(CodeBook.TARGET_WEB), CodeBook.WEB), doc);
            } catch (IOException e) {
                throw outException(CodeBook.TARGET_WEB + "/" + CodeBook.WEB + "创建失败", e);
            }
        }
    }

    protected File getFile(String fileName) throws IOException {
        try {
            return FileWrapper.getWebFile(fileName);
        } catch (IOException e) {
            throw outException("无法在web模版目录下找到" + fileName, e);
        }
    }

    public String getString() throws IOException {
        if (webString == null || "".equals(webString)) {
            return webString = getFileString(CodeBook.WEB_XML);
        }
        return webString;
    }

    private void setConfigFile(String include, Map<String, Object> params) throws IOException {
        if (include == null || "".equals(include)) {
            return;
        }
        if (include.contains(CodeBook.STRUTS2)) {
            params.put(CodeBook.STRUTS2_KEY, getFileString(CodeBook.WEB_XML_STRUTS2));
        } else {
            params.put(CodeBook.STRUTS2_KEY, "");
        }
        if (include.contains(CodeBook.XFIRE)) {
            params.put(CodeBook.XFIRE_KEY, getFileString(CodeBook.WEB_XML_XFIRE));
        } else {
            params.put(CodeBook.XFIRE_KEY, "");
        }
        if (include.contains(CodeBook.FLEX)) {
            params.put(CodeBook.FLEX_KEY, getFileString(CodeBook.WEB_XML_FLEX));
        } else {
            params.put(CodeBook.FLEX_KEY, "");
        }
        if (include.contains(CodeBook.WEB_WELCOME)) {
            params.put(CodeBook.WEB_WELCOME_KEY, getFileString(CodeBook.WEB_XML_WELCOME));
        } else {
            params.put(CodeBook.WEB_WELCOME_KEY, "");
        }
        if (include.contains(CodeBook.WEB_ERROR_PAGE)) {
            params.put(CodeBook.WEB_ERROR_PAGE_KEY, getFileString(CodeBook.WEB_XML_ERRORPAGE));
        } else {
            params.put(CodeBook.WEB_ERROR_PAGE_KEY, "");
        }
        if (include.contains(CodeBook.JCAPTCHA)) {
            params.put(CodeBook.JCAPTCHA_KEY, getFileString(CodeBook.WEB_XML_JCAPTCHA));
        } else {
            params.put(CodeBook.JCAPTCHA_KEY, "");
        }
    }

    private void chooseWebXml(String version, Map<String, Object> params) {
        if (version == null || "".equals(version)) {
            throw new NullPointerException("web.xml版本号未设定");
        }
        WebVersion wv = WebVersion.valueOf(version);
        if (wv == null) {
            throw new NullPointerException("该版本号不支持");
        }
        params.put(CodeBook.WEB_VERSION_KEY, wv.toString());
        if (WebVersion.V2_4 == wv) {
            params.put(CodeBook.WEB_XMLNS_KEY, CodeBook.WEB_V2_4_XMLNS);
            params.put(CodeBook.WEB_XMLNS_XSI_KEY, CodeBook.WEB_V2_4_XMLNS_XSI);
            params.put(CodeBook.WEB_XSI_SCHEMA_LOCATION_KEY, CodeBook.WEB_V2_4_XSI_SCHEMA_LOCATION);
            params.put(CodeBook.WEB_LOCATION_KEY, CodeBook.WEB_V2_4_LOCATION);
        } else if (WebVersion.V2_5 == wv) {
            params.put(CodeBook.WEB_XMLNS_KEY, CodeBook.WEB_V2_5_XMLNS);
            params.put(CodeBook.WEB_XMLNS_XSI_KEY, CodeBook.WEB_V2_5_XMLNS_XSI);
            params.put(CodeBook.WEB_XSI_SCHEMA_LOCATION_KEY, CodeBook.WEB_V2_5_XSI_SCHEMA_LOCATION);
            params.put(CodeBook.WEB_LOCATION_KEY, CodeBook.WEB_V2_5_LOCATION);
        } else if (WebVersion.V3_0 == wv) {
            params.put(CodeBook.WEB_XMLNS_KEY, CodeBook.WEB_V3_0_XMLNS);
            params.put(CodeBook.WEB_XMLNS_XSI_KEY, CodeBook.WEB_V3_0_XMLNS_XSI);
            params.put(CodeBook.WEB_XSI_SCHEMA_LOCATION_KEY, CodeBook.WEB_V3_0_XSI_SCHEMA_LOCATION);
            params.put(CodeBook.WEB_LOCATION_KEY, CodeBook.WEB_V3_0_LOCATION);
        }
    }
}
