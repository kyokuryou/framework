package org.smarty.web.commons;

import org.smarty.core.launcher.AbsLauncher;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.web.utils.SpringWebUtil;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Set;

/**
 * web启动器
 */
public class WebLauncher extends AbsLauncher implements ServletContextAware {
    private static RuntimeLogger logger = new RuntimeLogger(WebLauncher.class);

    @Override
    public final void setServletContext(ServletContext servletContext) {
        SpringWebUtil.setServletContext(servletContext);
    }

    @Override
    protected Set<ClassLoader> getLauncher() {
        Set<ClassLoader> cls = super.getLauncher();
        cls.add(WebLauncher.class.getClassLoader());
        return cls;
    }
}
