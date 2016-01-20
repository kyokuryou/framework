package org.smarty.web.utils;

import java.util.Locale;
import javax.servlet.ServletContext;
import org.smarty.core.io.RuntimeLogger;
import org.springframework.context.MessageSource;

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
    private static MessageSource messageSource;

    public static void setServletContext(ServletContext servletContext) {
        if (SpringWebUtil.servletContext != null) {
            throw new IllegalStateException("ServletContextHolder already holded 'servletContext'.");
        }
        SpringWebUtil.servletContext = servletContext;
        logger.info("holded servletContext,displayName:" + servletContext.getServletContextName());
    }

    public static void setMessageSource(MessageSource messageSource) {
        if (SpringWebUtil.messageSource != null) {
            throw new IllegalStateException("ServletContextHolder already holded 'messageSource'.");
        }
        SpringWebUtil.messageSource = messageSource;
        logger.info("holded messageSource");
    }

    public static ServletContext getServletContext() {
        if (servletContext == null) {
            throw new IllegalStateException("'servletContext' property is null,ServletContextHolder not yet init.");
        }
        return servletContext;
    }

    public static String getMessage(String key, Object... values) {
        return messageSource.getMessage(key, values, Locale.getDefault());
    }

    public static String getMessage(String key, Locale locale, Object... values) {
        return messageSource.getMessage(key, values, locale);
    }
}
