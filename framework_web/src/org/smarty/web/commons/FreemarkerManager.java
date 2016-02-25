package org.smarty.web.commons;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.ObjectUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Freemarker
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class FreemarkerManager implements InitializingBean {
    private static Log logger = LogFactory.getLog(FreemarkerManager.class);
    private Configuration configuration;

    public FreemarkerManager() {
    }

    public void afterPropertiesSet() throws Exception {

    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        if (freeMarkerConfigurer == null) {
            return;
        }
        this.configuration = freeMarkerConfigurer.getConfiguration();
    }

    public void outputTemplate(String name, Map<String, Object> data, OutputStream os) throws IOException {
        if (ObjectUtil.isEmpty(name) || os == null) {
            return;
        }
        try {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            Template template = configuration.getTemplate(name);
            template.process(data, sw);
            sw.flush();
        } catch (TemplateException e) {
            logger.warn(e);
        } catch (IOException e) {
            logger.warn(e);
            throw e;
        }
    }
}