package org.core.build.files;

import config.FileWrapper;
import org.core.build.CodeBook;
import org.core.utils.DocumentUtil;
import org.dom4j.Document;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/12
 * Update Date: 2013/12/12
 */
public class WsConfig extends FileConfig {
    private String wsString;

    @Override
    public void buildFile(Properties ps) throws IOException {
        Boolean ew = Boolean.valueOf(ps.getProperty(CodeBook.ENABLED_WEB));
        String com = ps.getProperty(CodeBook.COMPONENT);
        if (ew && com.contains(CodeBook.XFIRE)) {
            try {
                Document doc = DocumentUtil.createDocument(getString());
                DocumentUtil.writerDocument(createFile(ps.getProperty(CodeBook.TARGET_SRC), CodeBook.XFIRE_FILE), doc);
            } catch (IOException e) {
                throw outException(CodeBook.TARGET_SRC + "/" + CodeBook.XFIRE_FILE + "创建失败", e);
            }
        }
    }

    @Override
    protected File getFile(String fileName) throws IOException {
        try {
            return FileWrapper.getSrcFile(fileName);
        } catch (IOException e) {
            throw outException("无法在src模板目录下找到" + fileName, e);
        }
    }

    @Override
    public String getString() throws IOException {
        if (wsString == null || "".equals(wsString)) {
            return wsString = getFileString(CodeBook.SRC_WS);
        }
        return wsString;
    }
}
