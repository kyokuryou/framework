package org.smarty.core.utils;

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
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.common.BaseConstant;
import org.springframework.util.ClassUtils;

/**
 * 通过ClassLoader,对资源的简单的访问
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class PathUtil {
	private static Log logger = LogFactory.getLog(PathUtil.class);

	/**
	 * 返回类路径上的资源的URL
	 *
	 * @param resource 资源
	 * @return 资源
	 * @throws java.io.FileNotFoundException 如果没发现资源
	 */
	public static URL getResourceAsURL(String resource) throws FileNotFoundException {
		ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		if (classLoader != null) {
			URL url = classLoader.getResource(resource);
			if (null == url)
				url = classLoader.getResource("/" + resource);
			if (null != url)
				return url;
		}
		throw new FileNotFoundException("[Read File failed] file:'" + resource + "' not found");
	}

	/**
	 * 在classpath中返回一个Stream对象作为一个资源
	 *
	 * @param resource 资源
	 * @return 资源
	 * @throws java.io.FileNotFoundException 如果资源没发现或不能读
	 */
	public static InputStream getResourceAsInStream(String resource) throws FileNotFoundException {
		ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		if (classLoader != null) {
			InputStream returnValue = classLoader.getResourceAsStream(resource);
			if (null == returnValue)
				returnValue = classLoader.getResourceAsStream("/" + resource);
			if (null != returnValue)
				return returnValue;
		}
		throw new FileNotFoundException("[Read File failed] file:'" + resource + "' not found");
	}

	/**
	 * 在classpath资源获取作为一个Properties对象
	 *
	 * @param resource 资源
	 * @return 资源
	 */
	public static Properties getResourceAsProperties(String resource) throws FileNotFoundException {
		try {
			Properties props = new Properties();
			InputStream in = getResourceAsInStream(resource);
			props.load(in);
			in.close();
			return props;
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Read Properties File failed] Properties file:'" + resource + "' not found");
	}


	/**
	 * 一个URL作为一个Properties对象获取
	 *
	 * @param urlString - 网址
	 * @return 从URL中的数据与属性对象
	 */
	public static Properties getUrlAsProperties(String urlString) throws FileNotFoundException {
		try {
			Properties props = new Properties();
			InputStream in = getUrlAsInStream(urlString);
			props.load(in);
			in.close();
			return props;
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Read Properties URL failed] Properties URL:'" + urlString + "' not found");
	}

	/**
	 * 在classpath资源获取作为一个Reader对象
	 *
	 * @param resource 资源
	 * @return 资源
	 */
	public static Reader getResourceAsReader(String resource) throws FileNotFoundException {
		try {
			return new InputStreamReader(getResourceAsInStream(resource), BaseConstant.DEF_ENCODE);
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Read File failed] File:'" + resource + "' not found");
	}

	/**
	 * 在classpath资源获取作为一个文件对象
	 *
	 * @param resource - 资源
	 * @return 资源
	 */
	public static File getResourceAsFile(String resource) throws FileNotFoundException {
		try {
			return new File(getResourceAsURL(resource).getFile());
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Read File failed] file:'" + resource + "' not found");
	}

	/**
	 * 一个URL作为一个输入流中获取
	 *
	 * @param urlString - 网址
	 * @return 从URL数据的输入流
	 * @throws java.io.IOException 如果资源没发现或不能读
	 */
	public static InputStream getUrlAsInStream(String urlString) throws IOException {
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			return conn.getInputStream();
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Read URL failed] URL:'" + urlString + "' not found");
	}

	/**
	 * 一个URL作为一个Reader获取
	 *
	 * @param urlString - 网址
	 * @return 从URL中的数据与属性对象
	 */
	public static Reader getUrlAsReader(String urlString) throws FileNotFoundException {
		try {
			return new InputStreamReader(getUrlAsInStream(urlString), BaseConstant.DEF_ENCODE);
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Read URL failed] url:'" + urlString + "' not found");
	}

	/**
	 * 一个URL作为一个输入流中获取
	 *
	 * @param urlString - 网址
	 * @return 从URL数据的输入流
	 * @throws java.io.FileNotFoundException 如果资源没发现或不能读
	 */
	public static OutputStream getUrlAsOutStream(String urlString) throws FileNotFoundException {
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			return conn.getOutputStream();
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Write URL failed] url:'" + urlString + "' can't write");
	}

	/**
	 * 一个URL作为一个Reader获取
	 *
	 * @param urlString - 网址
	 * @return 从URL中的数据与属性对象
	 */
	public static Writer getUrlAsWriter(String urlString) throws FileNotFoundException {
		try {
			return new OutputStreamWriter(getUrlAsOutStream(urlString), BaseConstant.DEF_ENCODE);
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Write URL failed] url:'" + urlString + "' can't write");
	}

	/**
	 * 在classpath中返回一个Stream对象作为一个资源
	 *
	 * @param resource 资源
	 * @return 资源
	 * @throws java.io.IOException 如果资源没发现或不能读
	 */
	public static OutputStream getResourceAsOutStream(String resource) throws FileNotFoundException {
		try {
			return new FileOutputStream(getResourceAsFile(resource));
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Write File failed] File:'" + resource + "' can't write");
	}

	/**
	 * 获得输出流
	 *
	 * @param filePath 文件相对路径
	 * @return 输出流
	 */
	public static Writer getResourceAsWriter(String filePath) throws FileNotFoundException {
		try {
			return new OutputStreamWriter(getResourceAsOutStream(filePath), BaseConstant.DEF_ENCODE);
		} catch (IOException e) {
			logger.warn(e);
		}
		throw new FileNotFoundException("[Write File failed] File:'" + filePath + "' can't write");
	}
}
