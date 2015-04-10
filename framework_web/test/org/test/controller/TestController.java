package org.test.controller;

import org.smarty.web.http.BaseServlet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController extends BaseServlet {

    @RequestMapping(value = "/login")
    public View login(RedirectAttributes attributes) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", "qul");
        params.put("password", "quliang");
        attributes.addFlashAttribute("ok", "成功");
        return forward("test.jsp");
    }

    @RequestMapping(value = "/test")
    public String test(ModelMap modelMap) {

        Object map = modelMap.get("ok");
        System.out.println(map);
        return "test.jsp";

    }
}
