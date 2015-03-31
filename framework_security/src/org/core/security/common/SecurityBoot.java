package org.core.security.common;

import org.core.boot.WebBoot;

/**
 * 启动加载项
 */
public class SecurityBoot extends WebBoot {
    public void runInit() {
        // 创建静态页面
        // templateService.setServletContext(servletContext);
        // templateService.allBuildHtml(null);
    }

    @Override
    public void exit() {
        super.exit();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }
}
