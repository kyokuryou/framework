package org.smarty.core.config.files;

import config.FileWrapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.smarty.core.config.CodeBook;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/12
 * Update Date: 2013/12/12
 */
public class Log4jConfig extends FileConfig {
    private String log4jString;

    public void buildFile(Properties ps) throws IOException {
        File sf = createFile(ps.getProperty(CodeBook.TARGET_SRC), CodeBook.LOG4J_FILE);
        FileOutputStream fos = new FileOutputStream(sf);
        try {
            String log4j = getString();
            fos.write(log4j.getBytes());
        } catch (IOException e) {
            throw outException(CodeBook.TARGET_SRC + "/" + CodeBook.LOG4J_FILE + "创建失败", e);
        } finally {
            fos.flush();
            fos.close();
        }
    }

    protected File getFile(String fileName) throws IOException {
        try {
            return FileWrapper.getSrcFile(fileName);
        } catch (IOException e) {
            throw outException("无法在src模版目录下找到" + fileName, e);
        }
    }

    public String getString() throws IOException {
        if (log4jString == null || "".equals(log4jString)) {
            return log4jString = getFileString(CodeBook.SRC_LOG4J);
        }
        return log4jString;
    }
}
