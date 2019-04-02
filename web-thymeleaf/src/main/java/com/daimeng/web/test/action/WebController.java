package com.daimeng.web.test.action;

import com.daimeng.web.test.data.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class WebController {

    @RequestMapping(value = { "", "/hello/{id}" })
    public String hello(@PathVariable Integer id, Model model) {
        model.addAttribute("muser",new UserVo(id,"张三vo","中国广州vo"));
        List<UserVo> userList = new ArrayList<UserVo>();
        for (int i = 0; i <10; i++) {
            userList.add(new UserVo(i,"张三"+i,"中国广州"+i));
        }

        model.addAttribute("users", userList);
        return "test/hello";
    }
}
