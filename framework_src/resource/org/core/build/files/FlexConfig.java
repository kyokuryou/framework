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
public class FlexConfig extends FileConfig {
    @Override
    public void buildFile(Properties ps) throws IOException {
        Boolean ew = Boolean.valueOf(ps.getProperty(CodeBook.ENABLED_WEB));
        String com = ps.getProperty(CodeBook.COMPONENT);
        if (ew && com.contains(CodeBook.FLEX)) {
            try {
                buildServices(ps.getProperty(CodeBook.TARGET_WEB));
                buildRemoting(ps.getProperty(CodeBook.TARGET_WEB));
                buildMessaging(ps.getProperty(CodeBook.TARGET_WEB));
            } catch (IOException e) {
                throw outException("flex相关文件创建失败", e);
            }
        }
    }

    public void buildServices(String targetWeb) throws IOException {
        try {
            Document doc = DocumentUtil.createDocument(getFileString(CodeBook.WEB_FLEX_SERVICES));
            File sf = createFile(targetWeb, CodeBook.FLEX_SERVICES_FILE);
            DocumentUtil.writerDocument(sf, doc);
        } catch (IOException e) {
            throw outException(targetWeb + "/" + CodeBook.FLEX_SERVICES_FILE + "创建失败", e);
        }
    }

    public void buildRemoting(String targetWeb) throws IOException {
        try {
            Document doc = DocumentUtil.createDocument(getFileString(CodeBook.WEB_FLEX_REMOTING));
            File sf = createFile(targetWeb, CodeBook.FLEX_REMOTING_FILE);
            DocumentUtil.writerDocument(sf, doc);
        } catch (IOException e) {
            throw outException(targetWeb + "/" + CodeBook.FLEX_REMOTING_FILE + "创建失败", e);
        }
    }

    private void buildMessaging(String targetWeb) throws IOException {
        try {
            Document doc = DocumentUtil.createDocument(getFileString(CodeBook.WEB_FLEX_MESSAGING));
            File sf = createFile(targetWeb, CodeBook.FLEX_MESSAGING_FILE);
            DocumentUtil.writerDocument(sf, doc);
        } catch (IOException e) {
            throw outException(targetWeb + "/" + CodeBook.FLEX_MESSAGING_FILE + "创建失败", e);
        }
    }

    @Override
    protected File getFile(String fileName) throws IOException {
        try {
            return FileWrapper.getWebFile(fileName);
        } catch (IOException e) {
            throw outException("无法在web模版目录下找到" + fileName, e);
        }
    }

    @Override
    public String getString() throws IOException {
        throw outException("该方法flex不支持",new UnsupportedOperationException());
    }
}
