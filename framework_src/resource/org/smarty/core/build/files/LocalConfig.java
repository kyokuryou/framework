package org.smarty.core.build.files;

import config.FileWrapper;
import org.smarty.core.build.CodeBook;
import org.smarty.core.utils.TemplateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/13
 * Update Date: 2013/12/13
 */
public class LocalConfig extends FileConfig {
    private String localString;

    @Override
    public void buildFile(Properties ps) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>(0);
        File sf = createFile(ps.getProperty(CodeBook.TARGET_SRC), CodeBook.LOCAL_FILE);
        FileOutputStream fos = new FileOutputStream(sf);
        try {
            buildDB(ps, params);
            String local = TemplateUtil.render(getString(), params);
            fos.write(local.getBytes());
        } catch (IOException e) {
            throw outException(CodeBook.TARGET_SRC + "/" + CodeBook.LOCAL_FILE + "创建失败", e);
        } finally {
            fos.flush();
            fos.close();
        }
    }

    private void buildDB(Properties ps, Map<String, Object> params) throws IOException {
        String dbc = ps.getProperty(CodeBook.DB_CONNECTION);
        String[] dbp = dbc.split(">");
        params.put(CodeBook.SPRING_DS_NAME, dbp[0]);
        try {
            if (CodeBook.LOCAL_DS_JNDI.equals(dbp[1])) {
                params.put(CodeBook.LOCAL_DS_KEY, TemplateUtil.render(getFileString(CodeBook.SRC_CONFIG_JNDI), params));
            } else if (CodeBook.LOCAL_DS_JDBC.equals(dbp[1])) {
                params.put(CodeBook.LOCAL_DS_KEY, TemplateUtil.render(getFileString(CodeBook.SRC_CONFIG_JDBC), params));
            }
        } catch (IOException e) {
            throw outException("属性模版DB连接方式模版解析错误", e);
        }
    }

    @Override
    protected File getFile(String fileName) throws IOException {
        try {
            return FileWrapper.getSrcFile(fileName);
        } catch (IOException e) {
            throw outException("无法在src模版目录下找到" + fileName, e);
        }
    }

    @Override
    public String getString() throws IOException {
        if (localString == null || "".equals(localString)) {
            return localString = getFileString(CodeBook.SRC_CONFIG);
        }
        return localString;
    }
}
