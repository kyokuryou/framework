package org.smarty.core.utils;

import org.smarty.core.launcher.LauncherWrapper;
import org.smarty.core.logger.RuntimeLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * 通过ClassLoader,对资源的简单的访问
 */
public class PathUtil {
    private static RuntimeLogger logger = new RuntimeLogger(PathUtil.class);

    /**
     * 在调用getResourceAsReader使用的字符集
     * null表示使用系统默认
     */
    private static Charset charset = Charset.forName("UTF-8");

    /**
     * 返回类路径上的资源的URL
     *
     * @param loader   用于获取资源的classloader
     * @param resource 资源
     * @return 资源
     * @throws java.io.IOException 如果没发现资源
     */
    public static URL getResourceAsURL(String resource, ClassLoader loader) throws IOException {
        ClassLoader[] classLoader = LauncherWrapper.getClassLoaders(loader);
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
     * 返回类路径上的资源的URL
     *
     * @param resource 资源
     * @return 资源
     */
    public static URL getResourceAsURL(String resource) {
        try {
            return getResourceAsURL(resource, null);
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 在classpath中返回一个Stream对象作为一个资源
     *
     * @param loader   用于获取资源的classloader
     * @param resource 资源
     * @return 资源
     * @throws java.io.IOException 如果资源没发现或不能读
     */
    public static InputStream getResourceAsInStream(String resource, ClassLoader loader) throws IOException {
        ClassLoader[] classLoader = LauncherWrapper.getClassLoaders(loader);
        for (ClassLoader cl : classLoader) {
            if (null != cl) {
                InputStream returnValue = cl.getResourceAsStream(resource);
                if (null == returnValue)
                    returnValue = cl.getResourceAsStream("/" + resource);
                if (null != returnValue)
                    return returnValue;
            }
        }
        throw new FileNotFoundException(resource + "文件不存在");
    }


    /**
     * 在classpath中返回一个Stream对象作为一个资源
     *
     * @param resource 资源
     * @return 资源
     */
    public static InputStream getResourceAsInStream(String resource) {
        try {
            return getResourceAsInStream(resource, null);
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 在classpath资源获取作为一个Properties对象
     *
     * @param loader   用于获取资源的classloader
     * @param resource 资源
     * @return 资源
     */
    public static Properties getResourceAsProperties(String resource, ClassLoader loader) {
        try {
            Properties props = new Properties();
            InputStream in = getResourceAsInStream(resource, loader);
            props.load(in);
            in.close();
            return props;
        } catch (IOException e) {
            logger.out("资源没发现或不能读", e);
        }
        return null;
    }

    /**
     * 在classpath中返回一个Properties对象作为一个资源
     *
     * @param resource 资源
     * @return 资源
     */
    public static Properties getResourceAsProperties(String resource) {
        return getResourceAsProperties(resource, null);
    }

    /**
     * 在classpath资源获取作为一个Reader对象
     *
     * @param loader   用于获取资源的classloader
     * @param resource 资源
     * @return 资源
     */
    public static Reader getResourceAsReader(String resource, ClassLoader loader) {
        try {
            Reader reader;
            if (charset == null) {
                reader = new InputStreamReader(getResourceAsInStream(resource, loader));
            } else {
                reader = new InputStreamReader(getResourceAsInStream(resource, loader), charset);
            }
            return reader;
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 在classpath资源获取作为一个Reader对象
     *
     * @param resource 资源
     * @return 资源
     */
    public static Reader getResourceAsReader(String resource) {
        return getResourceAsReader(resource, null);
    }

    /**
     * 在classpath资源获取作为一个文件对象
     *
     * @param loader   - 用于获取资源的classloader
     * @param resource - 资源
     * @return 资源
     */
    public static File getResourceAsFile(String resource, ClassLoader loader) {
        try {
            return new File(getResourceAsURL(resource, loader).getFile());
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 在classpath资源获取作为一个文件对象
     *
     * @param resource 资源
     * @return 资源
     */
    public static File getResourceAsFile(String resource) {
        return getResourceAsFile(resource, null);
    }

    /**
     * 一个URL作为一个输入流中获取
     *
     * @param urlString - 网址
     * @return 从URL数据的输入流
     * @throws java.io.IOException 如果资源没发现或不能读
     */
    public static InputStream getUrlAsInStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    /**
     * 一个URL作为一个Reader获取
     *
     * @param urlString - 网址
     * @return 从URL中的数据与属性对象
     */
    public static Reader getUrlAsReader(String urlString) {
        try {
            Reader reader;
            if (charset == null) {
                reader = new InputStreamReader(getUrlAsInStream(urlString));
            } else {
                reader = new InputStreamReader(getUrlAsInStream(urlString), charset);
            }
            return reader;
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 一个URL作为一个Properties对象获取
     *
     * @param urlString - 网址
     * @return 从URL中的数据与属性对象
     */
    public static Properties getUrlAsProperties(String urlString) {
        try {
            Properties props = new Properties();
            InputStream in = getUrlAsInStream(urlString);
            props.load(in);
            in.close();
            return props;
        } catch (IOException e) {
            logger.out("资源没发现或不能读", e);
        }
        return null;
    }


    /**
     * 一个URL作为一个输入流中获取
     *
     * @param urlString - 网址
     * @return 从URL数据的输入流
     * @throws java.io.IOException 如果资源没发现或不能读
     */
    public static OutputStream getUrlAsOutStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getOutputStream();
    }

    /**
     * 一个URL作为一个Reader获取
     *
     * @param urlString - 网址
     * @return 从URL中的数据与属性对象
     */
    public static Writer getUrlAsWriter(String urlString) {
        try {
            Writer writer;
            if (charset == null) {
                writer = new OutputStreamWriter(getUrlAsOutStream(urlString));
            } else {
                writer = new OutputStreamWriter(getUrlAsOutStream(urlString), charset);
            }
            return writer;
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 在classpath中返回一个Stream对象作为一个资源
     *
     * @param loader   用于获取资源的classloader
     * @param resource 资源
     * @return 资源
     * @throws java.io.IOException 如果资源没发现或不能读
     */
    public static OutputStream getResourceAsOutStream(String resource, ClassLoader loader) throws IOException {
        return new FileOutputStream(getResourceAsFile(resource, loader));
    }


    /**
     * 在classpath中返回一个Stream对象作为一个资源
     *
     * @param resource 资源
     * @return 资源
     */
    public static OutputStream getResourceAsOutStream(String resource) {
        try {
            return getResourceAsOutStream(resource, null);
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 获得输出流
     *
     * @param filePath 文件相对路径
     * @return 输出流
     */
    public static Writer getResourceAsWriter(String filePath) {
        Writer writer;
        try {
            if (charset == null) {
                writer = new OutputStreamWriter(getResourceAsOutStream(filePath, null));
            } else {
                writer = new OutputStreamWriter(getResourceAsOutStream(filePath, null), charset);
            }
            return writer;
        } catch (IOException e) {
            logger.out(e);
        }
        return null;
    }

    /**
     * 在classpath资源获取作为一个Reader对象
     *
     * @param loader   用于获取资源的classloader
     * @param resource 资源
     * @return 资源
     */
    public static Writer getResourceAsWriter(String resource, ClassLoader loader) {
        try {
            Writer writer;
            if (charset == null) {
                writer = new OutputStreamWriter(getResourceAsOutStream(resource, loader));
            } else {
                writer = new OutputStreamWriter(getResourceAsOutStream(resource, loader), charset);
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
        PathUtil.charset = charset;
    }
}
