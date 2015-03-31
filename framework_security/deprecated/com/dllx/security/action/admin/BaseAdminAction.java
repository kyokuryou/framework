package com.dllx.security.action.admin;

import com.dllx.utils.LogicUtil;
import com.dllx.web.BaseAction;

/**
 * 后台管理基础action
 */
public class BaseAdminAction extends BaseAction {

    /**
     * 重写!验证验证码
     *
     * @return 通过/不通过
     */
    @Override
    public boolean validateCaptcha() {
        String error = getParameter("error");
        return LogicUtil.isEmpty(error) || !error.toUpperCase().endsWith("CAPTCHA");
    }
}
