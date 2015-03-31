package config;

import org.smarty.core.build.CodeBook;

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

    /**
     * 获得src目录模版文件
     *
     * @param fileName 文件名
     * @return 文件
     * @throws java.io.IOException
     */
    public static File getSrcFile(String fileName) throws IOException {
        return new File(getResourceAsURL(getFileName(CodeBook.SRC_PATH, fileName)).getFile());
    }

    /**
     * 获得web目录模版文件
     *
     * @param fileName 文件名
     * @return 文件
     * @throws java.io.IOException
     */
    public static File getWebFile(String fileName) throws IOException {
        return new File(getResourceAsURL(getFileName(CodeBook.WEB_PATH, fileName)).getFile());
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
