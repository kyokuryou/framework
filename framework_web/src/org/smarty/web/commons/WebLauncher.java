package org.smarty.web.commons;

import org.smarty.core.launcher.AbsLauncher;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.web.utils.SpringMVCUtil;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * web启动器
 */
public class WebLauncher extends AbsLauncher implements ServletContextAware {
    private static RuntimeLogger logger = new RuntimeLogger(WebLauncher.class);
    @Override
    public void setServletContext(ServletContext servletContext) {
        SpringMVCUtil.setServletContext(servletContext);
    }

    @Override
    protected ClassLoader[] getClassLoaders() {
        return new ClassLoader[]{
                WebLauncher.class.getClassLoader()
        };
    }
}
