package com.daimeng.shiro;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daimeng.util.Constants;
import com.daimeng.util.UAgentInfo;
import com.daimeng.web.user.entity.SysPermission;
import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysUser;

public class SysUserFilter extends AccessControlFilter {
	
	private static final Logger log = LoggerFactory.getLogger(SysUserFilter.class);
	
	private final static String[] whiteList = new String[]{"/403","/404","/401","/500"};
	
	@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		String userAgent = hrequest.getHeader("User-Agent");
		String httpAccept = hrequest.getHeader("Accept");
		UAgentInfo detector = new UAgentInfo(userAgent, httpAccept);
		if (detector.detectMobileQuick()) {
			//移动端浏览器
			//response.sendRedirect("http://www.baidu.com");
			//((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/m"); 
			//return false;
			Constants.println("------->SysUserFilter.user-agent------>移动端浏览器<------");
		} else {
			//PC浏览器
			//response.sendRedirect("http://cn.bing.com/");
			Constants.println("------->SysUserFilter.user-agent------>PC浏览器<------");
		}
			
		Constants.println("------->SysUserFilter.preHandle------>执行<------");
		request.setAttribute("basePath", ((HttpServletRequest)request).getContextPath());
        Subject subject = getSubject(request, response);
        if (subject == null) {
        	Constants.println("------->SysUserFilter.preHandle------>subject空<------");
            return true;
        }
        
        if(subject.getPrincipal()==null){
        	Constants.println("------->SysUserFilter.preHandle------>subject.getPrincipal()空<------");
        	return true;
        }
        
        SysUser suser = (SysUser) ((HttpServletRequest)request).getSession().getAttribute(Constants.CURRENT_USER);
        if(suser != null){
        	Constants.println("------->SysUserFilter.preHandle------>user=" + suser.getRealname());
        	boolean hasAuth = false;
        	HttpServletRequest req = (HttpServletRequest)request;
        	Constants.println("-------->getContextPath()--->" + req.getContextPath());
        	Constants.println("-------->getRemoteAddr()--->" + req.getRemoteAddr());
        	Constants.println("-------->getServletPath()--->" + req.getServletPath());
        	Constants.println("-------->getRequestURL()--->" + req.getRequestURL().toString());
        	Constants.println("-------->getRequestURI()--->" + req.getRequestURI());
        	Constants.println("-------->getQueryString()--->" + req.getQueryString());
        	Constants.println("-------->getRemoteUser()--->" + req.getRemoteUser());
        	/*-------->getContextPath()--->
        	-------->getRemoteAddr()--->0:0:0:0:0:0:0:1
        	-------->getServletPath()--->/article/list/{img}
        	-------->getRequestURL()--->http://localhost/article/list/%7Bimg%7D
        	-------->getRequestURI()--->/article/list/%7Bimg%7D
        	-------->getQueryString()--->null
        	-------->getRemoteUser()--->daimeng
        	-------->basePath--->http://localhost:80/
        	-------->realPath--->C:\Users\Sephy\AppData\Local\Temp\tomcat-docbase.6424093694088627621.80\
        	/article/list/%7Bimg%7D*/
        	
        	String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/";
        	Constants.println("-------->basePath--->" + basePath);
        	String realPath =req.getServletContext().getRealPath("/");
        	Constants.println("-------->realPath--->" + realPath);
        	
        	String path = req.getRequestURI();
        	Constants.println(path);
        	
        	if(!inWhiteList(path) && suser.getRole().getId() != 100001){
        		SysRole role = suser.getRole();
        		if(role != null){
        			if(role.getPermissions() != null && role.getPermissions().size() > 0){
        				for(SysPermission per : role.getPermissions()){
                			if(path.indexOf(per.getUrl()) > -1){
                				hasAuth = true;
                				break;
                			}
                		}	
        			}
            	}
        	}
        	else hasAuth = true;
        	
        	if(!hasAuth){
        		((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/403"); 
				return false;
        	}
        }else{
        	Constants.println("------->SysUserFilter.preHandle------>user空空空空<------");
        	//加载用户session
        	
        }
        
		return true;
	}
	
	private boolean inWhiteList(String url){
		for(String white : whiteList){
			if(url.indexOf(white) > -1){
				return true;
			}
		}
		return false;
	}
	
	private void login(ServletRequest request, ServletResponse response){
		// 获取当前用户
		Subject currentUser = getSubject(request, response);
		// 操作会话
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "aValue");
		String value = (String) session.getAttribute("someKey");
		if (value.equals("aValue")) {
		    log.info("Retrieved the correct value! [" + value + "]");
		}

		// 执行登录
		if (!currentUser.isAuthenticated()) {
		    UsernamePasswordToken token = new UsernamePasswordToken("username", "password");
		    token.setRememberMe(true);
		    try {
		        currentUser.login(token);
		    } catch (UnknownAccountException uae) {
		        log.info("There is no user with username of " + token.getPrincipal());
		    } catch (IncorrectCredentialsException ice) {
		        log.info("Password for account " + token.getPrincipal() + " was incorrect!");
		    } catch (LockedAccountException lae) {
		        log.info("The account for username " + token.getPrincipal() + " is locked. "
		                + "Please contact your administrator to unlock it.");
		    } catch (AuthenticationException ae) {
		        // unexpected condition? error?
		    }
		}

		// 输出用户信息
		log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

		// 检查角色
		if (currentUser.hasRole("schwartz")) {
		    log.info("May the Schwartz be with you!");
		} else {
		    log.info("Hello, mere mortal.");
		}

		// 检查权限
		if (currentUser.isPermitted("lightsaber:weild")) {
		    log.info("You may use a lightsaber ring. Use it wisely.");
		} else {
		    log.info("Sorry, lightsaber rings are for schwartz masters only.");
		}

		// 结束，执行注销
		currentUser.logout();
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		Constants.println("------->SysUserFilter.isAccessAllowed------>执行<------");
		SysUser sysUser = (SysUser) ((HttpServletRequest)request).getSession().getAttribute(Constants.CURRENT_USER);
        if (sysUser == null) {
        	Constants.println("------->SysUserFilter.isAccessAllowed------>user空空空空");
            return true;
        }else return false;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		Constants.println("------->SysUserFilter.onAccessDenied------>执行<------");
		getSubject(request, response).logout();
        saveRequestAndRedirectToLogin(request, response);
        return true;
	}

}
