package org.smarty.web.utils;

import org.smarty.core.logger.RuntimeLogger;

import javax.servlet.ServletContext;
import javax.swing.*;
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
import java.nio.charset.Charset;

/**
 * Created by kyokuryou on 15-3-31.
 */
public class WebPathUtil {
    private static RuntimeLogger logger = new RuntimeLogger(WebPathUtil.class);
    /**
     * 在调用getResourceAsReader使用的字符集
     * null表示使用系统默认
     */
    private static Charset charset = Charset.forName("UTF-8");


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
        Reader reader;
        try {
            if (charset == null) {
                reader = new InputStreamReader(getServletAsInStream(filePath));
            } else {
                reader = new InputStreamReader(getServletAsInStream(filePath), charset);
            }
            return reader;
        } catch (IOException e) {
            logger.out(e);
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

        Writer writer;
        try {
            if (charset == null) {
                writer = new OutputStreamWriter(getServletAsOutStream(filePath));
            } else {
                writer = new OutputStreamWriter(getServletAsOutStream(filePath), charset);
            }
            return writer;
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }


    /**
     * 返回编码格式
     *
     * @return Charset对象
     */
    public static Charset getCharset() {
        return charset;
    }

    /**
     * 设置编码格式
     *
     * @param charset Charset对象
     */
    public static void setCharset(Charset charset) {
        WebPathUtil.charset = charset;
    }

}
