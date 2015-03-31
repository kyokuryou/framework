package org.smarty.core.build.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/12
 * Update Date: 2013/12/12
 */
public abstract class FileConfig {

    protected String getFileString(String fileName) throws IOException {
        InputStream is = new FileInputStream(getFile(fileName));
        try {
            byte[] bytes = new byte[is.available()];
            if (is.read(bytes) == -1) {
                return null;
            }
            return new String(bytes);
        } catch (FileNotFoundException e) {
            throw outException(fileName + "模版文件不存在", e);
        } catch (IOException e) {
            throw outException("模版文件读取失败", e);
        } finally {
            is.close();
        }
    }

    public File createFile(String path, String file) throws IOException {
        File f = new File(path, file);
        if(!f.exists()){
            f.createNewFile();
        }
        return f;
    }

    public abstract void buildFile(Properties ps) throws IOException;

    protected abstract File getFile(String fileName) throws IOException;

    public abstract String getString() throws IOException;

    protected IOException outException(String memo, Exception e) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("错误:").append(memo).append("\r\n");
        sb.append(e.getMessage());
        System.out.println(sb.toString());
        throw new IOException(e);
    }
}
