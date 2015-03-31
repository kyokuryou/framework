package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件索引
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/12
 * Update Date: 2013/12/12
 */
public final class FileWrapper {
    // config.local.properties
    public static final String SRC_CONFIG = "config.local.properties.template";
    // spring.xml
    public static final String SRC_SPRING = "spring.xml.template";
    // log4j.properties
    public static final String SRC_LOG4J = "log4j.properties.template";
    // spring_ws.xml
    public static final String SRC_WS = "spring_ws.xml.template";
    // struts.xml
    public static final String SRC_STRUTS2 = "struts.xml.template";
    // spring.xml 子配置
    public static final String[] SRC_SPRING_CHILD = {
            "spring_xml/jdbc.config",
            "spring_xml/jndi.config",
            "spring_xml/ws.config"
    };
    // web.xml
    public static final String WEB_XML = "web.xml.template";
    // web.xml 子配置
    public static final String[] WEB_XML_CHILD = {
            "web_xml/errorPage.config",
            "web_xml/flex.config",
            "web_xml/struts2.config",
            "web_xml/welcome.config",
            "web_xml/xfire.config"
    };
    // flex系列配置文件
    public static final String[] WEB_FLEX = {
            "messaging-config.xml.template",
            "remoting-config.xml.template",
            "services-config.xml.template"
    };
    // src配置文件模板目录
    private static final String SRC_PATH = "config/src/";
    // web配置文件模板目录
    private static final String WEB_PATH = "config/web/";

    public static void main(String[] args) throws IOException {
        System.out.println(getSrcFile(SRC_CONFIG));
        System.out.println(getSrcFile(SRC_SPRING));
        System.out.println(getSrcFile(SRC_LOG4J));
        System.out.println(getSrcFile(SRC_WS));
        System.out.println(getSrcFile(SRC_STRUTS2));
        File[] fs = getSrcFiles(SRC_SPRING_CHILD);
        for (File f : fs) {
            System.out.println(f);
        }

        System.out.println(getWebFile(WEB_XML));
        File[] fs1 = getWebFiles(WEB_FLEX);
        for (File f1 : fs1) {
            System.out.println(f1);
        }
        File[] fs2 = getWebFiles(WEB_XML_CHILD);
        for (File f2 : fs2) {
            System.out.println(f2);
        }
    }

    /**
     * 获得src目录模版文件
     *
     * @param fileName 文件名
     * @return 文件
     * @throws java.io.IOException
     */
    public static File getSrcFile(String fileName) throws IOException {
        return new File(getResourceAsURL(getFileName(SRC_PATH, fileName)).getFile());
    }

    /**
     * 获得src目录模版文件列表
     *
     * @param fileNames 文件列表
     * @return 文件列表
     * @throws java.io.IOException
     */
    public static File[] getSrcFiles(String[] fileNames) throws IOException {
        File[] files = new File[fileNames.length];
        for (int i = 0, len = fileNames.length; i < len; i++) {
            files[i] = getSrcFile(fileNames[i]);
        }
        return files;
    }

    /**
     * 获得web目录模版文件
     *
     * @param fileName 文件名
     * @return 文件
     * @throws java.io.IOException
     */
    public static File getWebFile(String fileName) throws IOException {
        return new File(getResourceAsURL(getFileName(WEB_PATH, fileName)).getFile());
    }

    /**
     * 获得web目录模版文件列表
     *
     * @param fileNames 文件列表
     * @return 文件列表
     * @throws java.io.IOException
     */
    public static File[] getWebFiles(String[] fileNames) throws IOException {
        File[] files = new File[fileNames.length];
        for (int i = 0, len = fileNames.length; i < len; i++) {
            files[i] = getWebFile(fileNames[i]);
        }
        return files;
    }

    /**
     * 处理文件目录及文件名
     *
     * @param path     路径
     * @param fileName 文件名
     * @return 目录+文件
     */
    private static String getFileName(String path, String fileName) {
        StringBuilder sb = new StringBuilder(path);
        char[] cs = fileName.toCharArray();
        if (cs[0] == '/') {
            sb.append(new String(cs, 1, cs.length - 1));
        } else {
            sb.append(cs);
        }
        return sb.toString();
    }

    /**
     * 返回类路径上的资源的URL
     *
     * @param resource 资源
     * @return 资源
     * @throws java.io.IOException 如果没发现资源
     */
    private static URL getResourceAsURL(String resource) throws IOException {
        ClassLoader[] classLoader = getClassLoaders();
        URL url;
        for (ClassLoader cl : classLoader) {
            if (null != cl) {
                url = cl.getResource(resource);
                if (null == url)
                    url = cl.getResource("/" + resource);
                if (null != url)
                    return url;
            }
        }
        throw new FileNotFoundException(resource + "文件不存在");
    }

    /**
     * 获得默认的ClassLoader
     *
     * @return classLoader数组
     */
    public static ClassLoader[] getClassLoaders() {
        List<ClassLoader> loaders = new ArrayList<ClassLoader>();
        // 系统级别
        loaders.add(ClassLoader.getSystemClassLoader());
        loaders.add(Thread.currentThread().getContextClassLoader());
        loaders.add(FileWrapper.class.getClassLoader());
        return loaders.toArray(new ClassLoader[loaders.size()]);
    }
}
