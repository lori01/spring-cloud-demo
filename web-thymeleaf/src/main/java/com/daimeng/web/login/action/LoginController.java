package com.daimeng.web.login.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daimeng.shiro.exception.NullAccountException;
import com.daimeng.shiro.exception.NullCredentialsException;
import com.daimeng.util.CaptchaUtils;
import com.daimeng.util.Constants;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.service.UserService;

@Controller
public class LoginController {
	
	@Value("${shiro.password.algorithmName}")
    private String algorithmName;//设置算法
	@Value("${shiro.password.hashIterations}")
    private int hashIterations;//生成Hash值的迭代次数
	
	@Autowired
	private UserService userService;
	
	private static String USER_HOME_PAGE = "/article/list/1";
	private static String GUEST_HOME_PAGE = "/index/list/1";
	private static String LOGIN_HOME_PAGE = "/login";

	@RequestMapping({"/","/favicon","/index"})
    public String main(Model model){
		if(SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER) != null){
			return"redirect:" + USER_HOME_PAGE;
		}else{
			return"redirect:" + GUEST_HOME_PAGE;
		}
        
    }
	
	@RequestMapping({"/logins"})
    public String logins(Model model){
		if(SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER) != null){
			return"redirect:" + USER_HOME_PAGE;
		}else{
			return"redirect:" + LOGIN_HOME_PAGE;
		}
        
    }
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) throws Exception{
		return "login";
	}
	
	@RequestMapping("/dologin")
    public String dologin(HttpServletRequest request, Model model, Map<String, Object> map, RedirectAttributes redirectAttributes,
    		String username, String password,boolean rememberMe,String captcha) throws Exception{
        Constants.println("HomeController.login()");
        
        //校验验证码
        //session中的验证码
        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute(KEY_CAPTCHA);
        if (null == captcha || !captcha.equalsIgnoreCase(sessionCaptcha)) {
            //model.addAttribute("msg","验证码错误！");
            map.put("msg", "kaptchaValidateFailed -- > 验证码错误");
            redirectAttributes.addFlashAttribute("message", "kaptchaValidateFailed -- > 验证码错误");
            return "redirect:" + LOGIN_HOME_PAGE;
        }
        
        //对密码进行加密 此处无需加密，因为reaml会加密
        /*SysUser user = new SysUser();
        user.setLoginName(username);
        Page<SysUser> userList = userService.findAllBySpecification(user, 0);
        if(userList == null || userList.getContent().size() == 0){
        	throw new UnknownAccountException();
        }else if(userList.getContent().size() > 1){
        	throw new UnknownAccountException();
        }
        Constants.println("----->>userInfo="+userList.getSize());
        user = userList.getContent().get(0);
        Constants.println("----->>loginname="+user.getLoginName());
        Constants.println("----->>password="+user.getPassword());
        Constants.println("----->>salt="+user.getSalt());
        password = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(username + user.getSalt()),hashIterations).toHex();*/
        
        //如果有点击  记住我 
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password,rememberMe);
        //UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        
        try {
            //登录操作
            subject.login(usernamePasswordToken);
            return"redirect:" + USER_HOME_PAGE;
        } catch(Exception e) {
        	//登录失败从request中获取shiro处理的异常信息 shiroLoginFailure:就是shiro异常类的全类名
            String msg = "";
            /*String exception = (String) request.getAttribute("shiroLoginFailure");
            Constants.println("exception=" + exception);
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
                
            }*/

            if(e instanceof UnknownAccountException){
            	Constants.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            }
            else if(e instanceof IncorrectCredentialsException){
            	Constants.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            }
            else if(e instanceof NullAccountException){
            	Constants.println("NullAccountException -- > 账号不能为空");
                msg = "NullAccountException -- > 账号不能为空";
            }
            else if(e instanceof NullCredentialsException){
            	Constants.println("NullCredentialsException -- > 密码不能为空");
                msg = "NullCredentialsException -- > 密码不能为空";
            }
            else if(e instanceof LockedAccountException){
                model.addAttribute("msg","账号已被锁定,请联系管理员！");
            }
            map.put("msg", msg);
            redirectAttributes.addFlashAttribute("message", map.get("msg"));
        }
        
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        //String exception = (String) request.getAttribute("shiroLoginFailure");
        
        // 此方法不处理登录成功,由shiro进行处理
        return "redirect:" + LOGIN_HOME_PAGE;
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
    
    public static final String KEY_CAPTCHA = "KEY_CAPTCHA";

    @RequestMapping("/Captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        // 不缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {

            HttpSession session = request.getSession();

            CaptchaUtils tool = new CaptchaUtils();
            StringBuffer code = new StringBuffer();
            BufferedImage image = tool.genRandomCodeImage(code);
            session.removeAttribute(KEY_CAPTCHA);
            session.setAttribute(KEY_CAPTCHA, code.toString());

            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
