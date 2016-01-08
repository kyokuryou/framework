package org.smarty.web.utils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import org.smarty.core.io.RuntimeLogger;
import org.smarty.core.utils.LogicUtil;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * spring mvc工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class SpringWebUtil {
    private static RuntimeLogger logger = new RuntimeLogger(SpringWebUtil.class);
    private static ServletContext servletContext;

    public static void setServletContext(ServletContext servletContext) {
        if (SpringWebUtil.servletContext != null) {
            throw new IllegalStateException("ServletContextHolder already holded 'servletContext'.");
        }
        SpringWebUtil.servletContext = servletContext;
        logger.info("holded servletContext,displayName:" + servletContext.getServletContextName());
    }

    public static ServletContext getServletContext() {
        if (servletContext == null) {
            throw new IllegalStateException("'servletContext' property is null,ServletContextHolder not yet init.");
        }
        return servletContext;
    }

    public static Map<String, Object> getCommonData(Map<String, Object> data) {
        Map<String, Object> commonData = new HashMap<String, Object>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
        ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
        commonData.put("message", resourceBundleModel);
        commonData.put("base", servletContext.getContextPath());
        if (LogicUtil.isNotEmptyMap(data)) {
            commonData.putAll(data);
        }
        return commonData;
    }
}
