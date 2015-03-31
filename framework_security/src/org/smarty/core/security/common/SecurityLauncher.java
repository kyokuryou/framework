package org.smarty.core.security.common;

import org.smarty.web.commons.WebLauncher;

import java.util.Set;

/**
 * 启动加载项
 */
public class SecurityLauncher extends WebLauncher {

    @Override
    protected Set<ClassLoader> getLauncher() {
        Set<ClassLoader> cls = super.getLauncher();
        cls.add(SecurityLauncher.class.getClassLoader());
        return cls;
    }
}
