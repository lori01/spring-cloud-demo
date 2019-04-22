package com.daimeng.web.test.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class WebController {

    @RequestMapping(value = { "", "/hello/{id}" })
    public String hello(@PathVariable Integer id, Model model) {
        return "test/hello";
    }
}
