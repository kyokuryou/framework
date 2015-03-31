package com.dllx.security.action.admin;

import com.dllx.security.bean.Admin;
import com.dllx.security.service.admin.AdminService;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 后台Action类 - 后台管理、管理员
 */

@Controller
@ParentPackage("admin")
public class AdminAction extends BaseAdminAction {

    private static final long serialVersionUID = -5383463207248344967L;
    // Spring security 最后登录异常Session名称
    public static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";

    @Resource
    private AdminService adminService;

    // 登录页面
    public String login() {
        if (!validateCaptcha()) {
            addActionError("验证码错误,请重新输入!");
            return "login";
        }
        Exception springSecurityLastException = (Exception) getSession(SPRING_SECURITY_LAST_EXCEPTION);
        if (springSecurityLastException != null) {
            if (springSecurityLastException instanceof BadCredentialsException) {
                Map<String,Object> map = getSession();
                String  loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
                Admin admin = adminService.getAdminByUserName(loginUsername);
                if (admin == null) {
                    addActionError("您的用户名或密码错误!");
                }
            } else {
                addActionError("出现未知错误,无法登录!");
            }
            getSession().remove(SPRING_SECURITY_LAST_EXCEPTION);
        }
        return "login";
    }

    // 后台主页面
    public String main() {
        System.out.println("123213213");
        return "main";
    }
}