package org.core.support;

import org.core.logger.RuntimeLogger;
import org.core.utils.ConvertUtil;
import org.core.utils.PathUtil;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 生成HTML
 */
public class GenerateHtml implements InitializingBean {
    private static RuntimeLogger logger = new RuntimeLogger(GenerateHtml.class);
    private FreemarkerManager freemarkerManager;
    private String cachePath;

    public void afterPropertiesSet() throws Exception {

    }

    /**
     * 生成Html
     *
     * @param tn   文件名
     * @param data 数据
     */
    public void buildHtml(String tn, Map<String, Object> data) {
        String hf = ConvertUtil.reFileSuffix(cachePath + tn, "html");
        File htmlFile = PathUtil.getServletAsFile(hf);
        try {
            OutputStream os = new FileOutputStream(htmlFile);
            freemarkerManager.outputTemplate(tn, data, os);
            os.close();
        } catch (IOException e) {
            logger.out(e);
        }
    }


    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
        this.freemarkerManager = freemarkerManager;
    }
}
