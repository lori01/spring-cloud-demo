package com.daimeng.web.login.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daimeng.shiro.exception.NullAccountException;
import com.daimeng.shiro.exception.NullCredentialsException;
import com.daimeng.util.Constants;
import com.daimeng.web.user.entity.SysUser;

@Controller
public class LoginController {

	@RequestMapping({"/","/favicon","/index"})
    public String main(Model model){
		if(SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER) != null){
			return"redirect:/article/list/1";
		}else{
			return"redirect:/index/list/1";
		}
        
    }
	
	@RequestMapping({"/logins"})
    public String logins(Model model){
		if(SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER) != null){
			return"redirect:/article/list/1";
		}else{
			return"redirect:/login";
		}
        
    }
	@RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
        Constants.println("HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        Constants.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                Constants.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                Constants.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                Constants.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else if (NullAccountException.class.getName().equals(exception)) {
                Constants.println("NullAccountException -- > 账号不能为空");
                msg = "NullAccountException -- > 账号不能为空";
            } else if (NullCredentialsException.class.getName().equals(exception)) {
                Constants.println("NullCredentialsException -- > 密码不能为空");
                msg = "NullCredentialsException -- > 密码不能为空";
            } else {
                msg = "else >> "+exception;
                Constants.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(Model model){
    	SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        Constants.println("------没有权限-------");
        return "error/403";
    }
    @RequestMapping("/500")
    public String codeError(Model model){
    	SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
    	Constants.println("------内部错误-------");
    	return "error/500";
    }
}
