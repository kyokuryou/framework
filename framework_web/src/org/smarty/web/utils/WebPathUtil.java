package org.smarty.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.web.commons.WebBaseConstant;

/**
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public final class WebPathUtil {
    private final static Log logger = LogFactory.getLog(WebPathUtil.class);

    private WebPathUtil() {
    }

    /**
     * 在Servlet资源获取作为一个文件对象
     *
     * @param resource - 资源
     * @return 资源
     */
    public static File getServletAsFile(String resource) {
        ServletContext servletContext = SpringWebUtil.getServletContext();
        File htmlFile = new File(servletContext.getRealPath(resource));
        File fileDirectory = htmlFile.getParentFile();
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        return htmlFile;
    }


    /**
     * 在Servlet中返回一个Stream对象作为一个资源
     *
     * @param resource 资源
     * @return 资源
     * @throws java.io.IOException 如果资源没发现或不能读
     */
    public static OutputStream getServletAsOutStream(String resource) throws IOException {
        return new FileOutputStream(getServletAsFile(resource));
    }

    /**
     * 在Servlet中返回一个Stream对象作为一个资源
     *
     * @param resource 资源
     * @return 资源
     * @throws java.io.IOException 如果资源没发现或不能读
     */
    public static InputStream getServletAsInStream(String resource) throws IOException {
        return new FileInputStream(getServletAsFile(resource));
    }


    /**
     * 从获得ServletContext获得文件输入流
     *
     * @param filePath 文件路径
     * @return 输出流
     */
    public static Reader getServletAsReader(String filePath) {
        try {
            return new InputStreamReader(getServletAsInStream(filePath), WebBaseConstant.DEF_ENCODE);
        } catch (IOException e) {
            logger.warn(e);
        }
        return null;
    }

    /**
     * 从获得ServletContext获得文件输出流
     *
     * @param filePath 文件路径
     * @return 输出流
     */
    public static Writer getServletAsWriter(String filePath) {
        try {
            return new OutputStreamWriter(getServletAsOutStream(filePath), WebBaseConstant.DEF_ENCODE);
        } catch (IOException e) {
            logger.warn(e);
        }
        return null;
    }
}
