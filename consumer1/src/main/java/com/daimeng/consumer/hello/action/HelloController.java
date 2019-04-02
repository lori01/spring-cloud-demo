package com.daimeng.consumer.hello.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daimeng.consumer.hello.remote.HelloRemote;

@RestController
public class HelloController {

	@Autowired
    HelloRemote helloRemote;
	
    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return helloRemote.hello(name + " from consumer 1");
    }
    
}
