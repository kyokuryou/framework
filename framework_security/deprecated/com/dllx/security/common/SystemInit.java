package com.dllx.security.common;

import com.dllx.common.TemplateSupport;
import com.dllx.web.WebContextInit;

import javax.annotation.Resource;

/**
 * 启动加载项
 */
public class SystemInit extends WebContextInit {
    @Resource
    private TemplateSupport templateSupport;
    public void runInit() {
        // 创建静态页面
        // templateService.setServletContext(servletContext);
        // templateService.allBuildHtml(null);
    }

    @Override
    public void exit() {
        super.exit();
    }
}
