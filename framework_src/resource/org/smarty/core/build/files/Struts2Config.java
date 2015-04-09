package org.smarty.core.build.files;

import config.FileWrapper;
import org.smarty.core.build.CodeBook;
import org.smarty.core.utils.DocumentUtil;
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
public class Struts2Config extends FileConfig {
    private String struts2String;

    public void buildFile(Properties ps) throws IOException {
        Boolean ew = Boolean.valueOf(ps.getProperty(CodeBook.ENABLED_WEB));
        String com = ps.getProperty(CodeBook.COMPONENT);
        if (ew && com.contains(CodeBook.STRUTS2)) {
            try {
                Document doc = DocumentUtil.createDocument(getString());
                File sf = createFile(ps.getProperty(CodeBook.TARGET_SRC), CodeBook.STRUTS2_FILE);
                DocumentUtil.writerDocument(sf, doc);
            } catch (IOException e) {
                throw outException(CodeBook.TARGET_SRC + "/" + CodeBook.STRUTS2_FILE + "创建失败", e);
            }
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
        if (struts2String == null || "".equals(struts2String)) {
            return struts2String = getFileString(CodeBook.SRC_STRUTS2);
        }
        return struts2String;
    }
}
