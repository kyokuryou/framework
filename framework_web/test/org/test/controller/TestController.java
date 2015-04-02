package org.test.controller;

import org.smarty.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/*")
public class TestController extends BaseController {

    @RequestMapping(value = "login.do")
    public ModelAndView login() {
        return new ModelAndView("forward:/test.do");
    }

    @RequestMapping(value = "test.do")
    public ModelAndView test() {
        System.out.println(request.getParameter("userName"));
        System.out.println(request.getParameter("password"));
        return new ModelAndView("login", "error", "用户名或密码错误。");
    }
}
