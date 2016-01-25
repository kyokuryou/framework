package org.smarty.web.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.ConvertUtil;
import org.smarty.web.utils.WebPathUtil;
import org.springframework.beans.factory.InitializingBean;

/**
 * 生成HTML
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class GenerateHtml implements InitializingBean {
    private static Log logger = LogFactory.getLog(GenerateHtml.class);
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
        File htmlFile = WebPathUtil.getServletAsFile(hf);
        try {
            OutputStream os = new FileOutputStream(htmlFile);
            freemarkerManager.outputTemplate(tn, data, os);
            os.close();
        } catch (IOException e) {
            logger.warn(e);
        }
    }


    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
        this.freemarkerManager = freemarkerManager;
    }
}
