package org.smarty.core.build;

import org.smarty.core.exception.InvokeMethodException;
import org.smarty.core.exception.NoSuchReflectException;
import org.smarty.core.utils.BeanUtil;
import org.smarty.core.utils.DocumentUtil;
import org.smarty.core.utils.TemplateUtil;
import org.dom4j.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件创建者
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/11/12
 * Update Date: 2013/11/12
 */
public class ConfigBuilder {

    public static void main(String[] args) {
        String[] ars = new String[]{
                "true",
                "D:/work/framework/testModule",
                "D:/work/framework/testModule/src",
                "D:/work/framework/testModule/web",
                "V2_4",
                "struts2,xfire,flex"
        };
        main1(ars);
    }

    /**
     * 创建入口
     *
     * @param args -
     *             0->是否启用web端应用;true启动,false禁用
     *             1->发布目录
     *             2->java源码目录
     *             3->web源码目录
     *             4->web版本
     *             5->依赖列表(compressor,flex,image,poi,struts2,xfire,mail,jcaptcha)
     */
    public static void main1(String[] args) {
        if (args == null || args.length < 5) {
            throw new ArrayIndexOutOfBoundsException("参数数量错误");
        }
        ConfigBuilder cb = new ConfigBuilder();
        try {
            cb.ready(args);
//            cb.buildLog4jFile(args);
            cb.buildSpringFile(args);
//            if (Boolean.valueOf(args[0])) {
//                cb.buildWebFile(args);
//                cb.buildFlexFile(args);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建环境变量
     *
     * @param args 参数
     * @return 环境变量
     */
    private Properties setProperties(String[] args) {
        Properties pro = new Properties();
        pro.put("enabledWeb", args[0]);
        pro.put("targetBasic", args[1]);
        pro.put("targetSrc", args[2]);
        pro.put("targetWeb", args[3]);
        pro.put("webVersion", args[4]);
        pro.put("component", args[5]);
        pro.put("dbconnection", args[6]);
        pro.put("packageinfo", args[7]);
        return pro;
    }

    public void buildFlexFile(String[] args) throws IOException {
        if (args[5].contains("flex")) {
            Map<String, File> map = new HashMap<String, File>();
            map.put("config/web/messaging-config_xml.template", getFile(args[3] + "/WEB-INF/flex/messaging-config.xml"));
            map.put("config/web/remoting-config_xml.template", getFile(args[3] + "/WEB-INF/flex/remoting-config.xml"));
            map.put("config/web/services-config_xml.template", getFile(args[3] + "/WEB-INF/flex/services-config.xml"));
            for (Map.Entry<String, File> me : map.entrySet()) {
                String webXml = getFileString(me.getKey());
                Document doc = DocumentUtil.createDocument(webXml);
                DocumentUtil.writerDocument(me.getValue(), doc);
            }
        }
    }

    public void buildLog4jFile(String[] args) throws IOException {
        String log4j = getFileString("config/src/log4j_properties.template");
        FileOutputStream fos = new FileOutputStream(getFile(args[2] + "/log4j.properties"));
        fos.write(log4j.getBytes());
        fos.flush();
        fos.close();
    }

    public void buildSpringFile(String[] args) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        if (Boolean.valueOf(args[0]) && args[5].contains("xfire")) {
            params.put("wsFile", getFileString("config/src/spring_xml/ws.config"));

//            DocOutputStream dos = new DocOutputStream(new DocFile(args[2] + "/spring_ws.xml"));
//            dos.write(new DocInputStream(new DocFile("config/src/spring_ws_xml.template")));

//            Document doc = DocumentUtil.createDocument(getFileString("config/src/spring_ws_xml.template"));
//            DocumentUtil.writerDocument(getFile(args[2] + "/spring_ws.xml"), doc);
        }
//        String springXml = TemplateUtil.render(getFileString("config/src/spring_xml.template"), params);
//        Document doc = DocumentUtil.createDocument(springXml);
//        DocumentUtil.writerDocument(getFile(args[2] + "/spring.xml"), doc);
    }

    public void buildWebFile(String[] args) throws IOException {
        WebXml wx = chooseWebXml(args[4]);
        wx.setSpringFile("spring.xml");
        wx.setLog4jFile("log4j.properties");
        setConfigFile(wx, args[5]);
        String webXml = TemplateUtil.render(getFileString("config/web/web_xml.template"), toMap(wx));
        Document doc = DocumentUtil.createDocument(webXml);
        DocumentUtil.writerDocument(getFile(args[3] + "/WEB-INF/web.xml"), doc);
    }

    private File getFile(String pathFile) {
        File file = new File(pathFile);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file;
    }

    private Map<String, Object> toMap(WebXml wx) {
        Map<String, Object> params = new HashMap<String, Object>();
        Class klass = wx.getClass();
        Field[] fields = BeanUtil.getFields(klass);
        for (Field field : fields) {
            String fn = field.getName();
            Object val = null;
            try {
                val = BeanUtil.invokeGetterMethod(wx, fn);
            } catch (NoSuchReflectException e) {
                e.printStackTrace();
            } catch (InvokeMethodException e) {
                e.printStackTrace();
            }
            if (val == null) {
                params.put(fn, "");
            } else {
                params.put(fn, val);
            }
        }
        return params;
    }

    private void setConfigFile(WebXml wx, String include) throws IOException {
        if (include == null || "".equals(include)) {
            return;
        }
        if (include.contains("struts2")) {
            wx.setStruts2(getFileString("config/web/web_xml/struts2.config"));
        }
        if (include.contains("xfire")) {
            wx.setXfire(getFileString("config/web/web_xml/xfire.config"));
        }
        if (include.contains("flex")) {
            wx.setXfire(getFileString("config/web/web_xml/flex.config"));
        }
        if (include.contains("welcome")) {
            wx.setXfire(getFileString("config/web/web_xml/welcome.config"));
        }
        if (include.contains("errorPage")) {
            wx.setXfire(getFileString("config/web/web_xml/errorPage.config"));
        }
    }

    private String getFileString(String fileName) throws IOException {
        InputStream is = ConfigBuilder.class.getClassLoader().getResourceAsStream(fileName);
        byte[] bytes = new byte[is.available()];
        if (is.read(bytes) == -1) {
            return null;
        }
        return new String(bytes);
    }

    /**
     * 检查路径是否合法
     *
     * @param args 参数
     * @throws Exception
     */
    public void ready(String[] args) throws Exception {
        verifyContents(args[1], "项目");
        verifyContents(args[2], "src");
        if (Boolean.valueOf(args[0])) {
            verifyContents(args[3], "web");
            verifyWebVersion(args[4]);
        }
    }

    /**
     * 验证发布目录是否存在
     *
     * @param contents 目录
     * @throws java.io.IOException 未发现发布目录
     */
    private void verifyContents(String contents, String memo) throws IOException {
        File file = new File(contents);
        if (!file.exists()) {
            throw new IOException(memo + "目录未发现");
        }
    }

    /**
     * 验证web版本
     *
     * @param version 版本号
     * @throws EnumConstantNotPresentException
     *
     */
    private void verifyWebVersion(String version) throws EnumConstantNotPresentException {
        boolean isV = false;
        WebVersion[] wvs = WebVersion.values();
        for (WebVersion wv : wvs) {
            if (wv.name().equals(version)) {
                isV = true;
                break;
            }
        }
        if (!isV) {
            throw new EnumConstantNotPresentException(WebVersion.class, version + "类型未发现");
        }
    }

    private WebXml chooseWebXml(String version) {
        if (version == null || "".equals(version)) {
            throw new NullPointerException("web.xml版本号未设定");
        }
        WebXml wx = new WebXml();
        wx.setVersion(WebVersion.valueOf(version));
        if (WebVersion.V2_4 == wx.getVersion()) {
            wx.setXmlns("http://java.sun.com/xml/ns/j2ee");
            wx.setXmlnsXsi("http://www.w3.org/2001/XMLSchema-instance");
            wx.setXsiSchemaLocation("http://java.sun.com/xml/ns/j2ee");
            wx.setLocation("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        } else if (WebVersion.V2_5 == wx.getVersion()) {
            wx.setXmlns("http://java.sun.com/xml/ns/javaee");
            wx.setXmlnsXsi("http://www.w3.org/2001/XMLSchema-instance");
            wx.setXsiSchemaLocation("http://java.sun.com/xml/ns/javaee");
            wx.setLocation("http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd");
        } else if (WebVersion.V3_0 == wx.getVersion()) {
            wx.setXmlns("http://java.sun.com/xml/ns/javaee");
            wx.setXmlnsXsi("http://www.w3.org/2001/XMLSchema-instance");
            wx.setXsiSchemaLocation("http://java.sun.com/xml/ns/javaee");
            wx.setLocation("http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd");
        }
        return wx;
    }
}
