package org.test.controller;

import org.smarty.web.http.BaseServlet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController extends BaseServlet {

    @RequestMapping(value = "/login")
    public String login() {
        request.setAttribute("userName", "qul");
        request.setAttribute("password","quliang");
        return VIEW;
    }

    @RequestMapping(value = "/test")
    public String test(ModelMap modelMap) {

        Object map = modelMap.get("ok");
        System.out.println(map);
        return "test_view.jsp";

    }
}
