package org.smarty.core.build.files;

import config.FileWrapper;
import org.smarty.core.build.CodeBook;
import org.smarty.core.utils.DocumentUtil;
import org.smarty.core.utils.TemplateUtil;
import org.dom4j.Document;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/12
 * Update Date: 2013/12/12
 */
public class SpringConfig extends FileConfig {
    private String springString;

    @Override
    public void buildFile(Properties ps) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>(0);
        params.put(CodeBook.PACKAGE_INFO, ps.get(CodeBook.PACKAGE_INFO));
        try {
            buildWsFile(ps, params);
            buildDB(ps, params);
            buildJCaptcha(ps,params);
            String sxs = TemplateUtil.render(getString(), params);
            Document doc = DocumentUtil.createDocument(sxs);
            File sf = createFile(ps.getProperty(CodeBook.TARGET_SRC), CodeBook.SPRING_FILE);
            DocumentUtil.writerDocument(sf, doc);
        } catch (IOException e) {
            throw outException(CodeBook.TARGET_SRC + "/" + CodeBook.SPRING_FILE + "创建失败", e);
        }
    }

    private void buildJCaptcha(Properties ps, Map<String, Object> params) throws IOException{
        Boolean ew = Boolean.valueOf(ps.getProperty(CodeBook.ENABLED_WEB));
        String com = ps.getProperty(CodeBook.COMPONENT);
        if (ew && com.contains(CodeBook.JCAPTCHA)) {
            params.put(CodeBook.JCAPTCHA_KEY, getFileString(CodeBook.SRC_SPRING_JCAPTCHA));
        }else{
            params.put(CodeBook.JCAPTCHA_KEY, "");
        }
    }

    private void buildDB(Properties ps, Map<String, Object> params) throws IOException {
        String dbc = ps.getProperty(CodeBook.DB_CONNECTION);
        String[] dbp = dbc.split(">");
        params.put(CodeBook.SPRING_DS_NAME, dbp[0]);
        try {
            if (CodeBook.SPRING_DS_JNDI.equals(dbp[1])) {
                params.put(CodeBook.SPRING_DS_KEY, TemplateUtil.render(getFileString(CodeBook.SRC_SPRING_JNDI), params));
            } else if (CodeBook.SPRING_DS_JDBC.equals(dbp[1])) {
                params.put(CodeBook.SPRING_DS_KEY, TemplateUtil.render(getFileString(CodeBook.SRC_SPRING_JDBC), params));
            }
        } catch (IOException e) {
            throw outException(CodeBook.SRC_SPRING_JNDI + "模版解析错误", e);
        }
    }

    private void buildWsFile(Properties ps, Map<String, Object> params) throws IOException {
        Boolean ew = Boolean.valueOf(ps.getProperty(CodeBook.ENABLED_WEB));
        String com = ps.getProperty(CodeBook.COMPONENT);
        if (ew && com.contains(CodeBook.XFIRE)) {
            WsConfig wc = new WsConfig();
            wc.buildFile(ps);
            params.put(CodeBook.XFIRE_KEY, getFileString(CodeBook.SRC_SPRING_WS));
        }else{
            params.put(CodeBook.XFIRE_KEY, "");
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
        if (springString == null || "".equals(springString)) {
            return springString = getFileString(CodeBook.SRC_SPRING);
        }
        return springString;
    }
}
