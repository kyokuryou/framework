package org.smarty.web.commons;

import org.smarty.core.launcher.AbsLauncher;
import org.smarty.core.io.RuntimeLogger;
import org.smarty.web.utils.SpringWebUtil;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Set;

/**
 * web启动器
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class WebLauncher extends AbsLauncher implements ServletContextAware {
    private static RuntimeLogger logger = new RuntimeLogger(WebLauncher.class);

    public final void setServletContext(ServletContext servletContext) {
        SpringWebUtil.setServletContext(servletContext);
    }

    protected Set<ClassLoader> getLauncher() {
        Set<ClassLoader> cls = super.getLauncher();
        cls.add(WebLauncher.class.getClassLoader());
        return cls;
    }
}
