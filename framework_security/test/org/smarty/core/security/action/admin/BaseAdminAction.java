package org.smarty.core.security.action.admin;

import org.smarty.core.utils.LogicUtil;
import org.smarty.core.support.web.BaseAction;

/**
 * 后台管理基础action
 */
public class BaseAdminAction extends BaseAction {

    /**
     * 重写!验证验证码
     *
     * @return 通过/不通过
     */
    public boolean validateCaptcha() {
        String error = getParameter("error");
        return LogicUtil.isEmpty(error) || !error.toUpperCase().endsWith("CAPTCHA");
    }
}