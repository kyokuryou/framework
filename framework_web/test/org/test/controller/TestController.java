package org.test.controller;

import org.smarty.web.servlet.BaseServlet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/*")
public class TestController extends BaseServlet {

    @RequestMapping(value = "login.do")
    public ModelAndView login() {
        return new ModelAndView("redirect:/test.do");
    }

    @RequestMapping(value = "test.do")
    public ModelAndView test() {
        System.out.println(request.getParameter("userName"));
        System.out.println(request.getParameter("password"));
        return new ModelAndView("forward:/login.do", "error", "用户名或密码错误。");
    }
}
