package com.daimeng.web.login.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class MainsiteErrorController implements ErrorController {
	@RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 401){
            return "error/401";
        }else if(statusCode == 404){
            return "error/404";
        }else if(statusCode == 403){
            return "error/403";
        }else{
            return "error/500";
        }

    }

	@Override
    public String getErrorPath() {
        return "/error";
    }
}
