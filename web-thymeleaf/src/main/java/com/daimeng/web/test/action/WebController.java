package com.daimeng.web.test.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class WebController {

    @RequestMapping("/hello/{id}")
    public String hello(@PathVariable Integer id, Model model) {
        return "test/hello";
    }
    
    @RequestMapping("/divadd")
    public String divadd(Model model) {
    	return "test/divadd";
    }
}
