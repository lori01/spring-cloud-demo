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
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.daimeng.shiro.exception.NullAccountException;
import com.daimeng.shiro.exception.NullCredentialsException;
import com.daimeng.util.CaptchaUtils;
import com.daimeng.util.Constants;
import com.daimeng.util.HttpUtils;
import com.daimeng.web.common.BaseController;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.service.UserService;

@Controller
public class LoginController extends BaseController{
	
	@Value("${shiro.password.algorithmName}")
    private String algorithmName;//设置算法
	
	@Value("${shiro.password.hashIterations}")
    private int hashIterations;//生成Hash值的迭代次数
	
	//APP KEY
	@Value("${Weibo_App_Key}")
    private String weiboAppKey;
	//APP SECRET
	@Value("${Weibo_App_Secret}")
    private String weiboAppSecret;
	//本站回调URL
	@Value("${Weibo_redirect_uri}")
    private String weiboredirecturi;
	//authorize URL
	@Value("${Weibo_authorize_url}")
	private String weiboauthorizeurl;
	//access_token URL
	@Value("${Weibo_access_token_url}")
	private String weiboAccessTokenUrl;
	//获取用户信息URL
	@Value("${Weibo_get_token_info_url}")
	private String weiboGetTokenInfoUrl;
	
	@Autowired
	private UserService userService;
	
	public static final String KEY_CAPTCHA = "KEY_CAPTCHA";
	
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
	
	@RequestMapping("/403")
    public String unauthorizedRole(HttpServletRequest request,Model model){
    	SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        Constants.println("------没有权限-------"+getBashPathWithPort(request));
        return "error/403";
    }
    @RequestMapping("/500")
    public String codeError(HttpServletRequest request,Model model){
    	SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
    	Constants.println("------内部错误-------"+getBashPathWithPort(request));
    	return "error/500";
    }

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
	
	
	@RequestMapping("/weiboLoginReturn")
	public String weiboLoginReturn(HttpServletRequest request,String code) throws Exception{
		//authorize回调，返回code
		if(code != null && !"".equals(code)){
			//使用code去请求access_token
			String ajson = HttpUtils.sendPost(weiboAccessTokenUrl, getToken(weiboAppKey, weiboAppSecret, weiboredirecturi, code));
			Map amap = (Map) JSONObject.parse(ajson);
			if(amap != null && amap.get("access_token") != null){
				String accessToken = (String) amap.get("access_token");
				String username = "";
				String password = "";
				//通过access_token获取微博用户信息
				String tjson = HttpUtils.sendPost(weiboGetTokenInfoUrl, "access_token="+accessToken);
				Map tmap = (Map) JSONObject.parse(tjson);
				if(tmap != null && tmap.get("uid") != null){
					/*{
					       "uid": 1073880650,
					       "appkey": 1352222456,
					       "scope": null,
					       "create_at": 1352267591,
					       "expire_in": 157679471
					 }*/
					Long weiboUid = (Long) tmap.get("uid");
					String weiboUidStr = String.valueOf(weiboUid);
					SysUser quser = new SysUser();
					quser.setWeiboUid(weiboUidStr);
					Page<SysUser> page = userService.findAllBySpecification(quser, 0);
					if(page != null && page.getContent().size() > 0){
						quser = page.getContent().get(0);
						username = quser.getLoginName();
						password = quser.getWeiboUid();
					}else{
						SysUser newuser = new SysUser();
						newuser.setAccessToken(accessToken);
						newuser.setLoginName(weiboUidStr);
						newuser.setWeiboUid(weiboUidStr);
						newuser.setPassword(weiboUidStr);
						newuser.setRealname("微博用户"+weiboUid);
						newuser.setPermission("100002");
						ResponseVo vo = userService.addUser(newuser);
						username = weiboUidStr;
						password = weiboUidStr;
					}
				}else return "login";
				UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password,false);
		        //UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
		        Subject subject = SecurityUtils.getSubject();
		        
		        try {
		            //登录操作
		            subject.login(usernamePasswordToken);
		            return"redirect:" + USER_HOME_PAGE;
		        } catch(Exception e) {
		        	return "login";
		        }
			}
		}
		//access_token回调，返回access_token
		return "login";
	}
	public String getAuth(String key,String uri){
		return "client_id=" +key+ "&response_type=code&redirect_uri=" +uri;
	}
	public String getToken(String key,String secret,String uri,String code){
		return "client_id=" +key+ "&client_secret=" +secret+ "&grant_type=authorization_code&redirect_uri=" +uri+ "&code=" +code;
	}
	@RequestMapping("/doWeiboLogin")
	public String doWeiboLogin(HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) throws Exception{
		String weiboAuthUrl = weiboauthorizeurl + "?" + getAuth(weiboAppKey,weiboredirecturi);
		return"redirect:" + weiboAuthUrl;
	}

    
}
