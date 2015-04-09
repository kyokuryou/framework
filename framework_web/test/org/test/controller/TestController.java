package org.test.controller;

import org.smarty.core.utils.SpringUtil;
import org.smarty.web.http.BaseServlet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TestController extends BaseServlet {

    @RequestMapping(value = "/login")
    public ModelAndView login() {
        return new ModelAndView();
    }

    @RequestMapping(value = "/test")
    public ModelAndView test() {
        InternalResourceViewResolver irv  = SpringUtil.getBean("internalResourceViewResolver",InternalResourceViewResolver.class);
        System.out.println(request.getParameter("userName"));
        System.out.println(request.getParameter("password"));
        // return new ModelAndView("redirect:test.jsp", "error", "用户名或密码错误。");
        return new ModelAndView(new RedirectView("/login.do"));
    }
}
