package com.daimeng.shiro;

import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.daimeng.util.Constants;
import com.daimeng.web.user.entity.SysPermission;
import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysUser;

public class SysUserFilter extends AccessControlFilter {
	
	private final static String[] whiteList = new String[]{"/403","/404","/401","/500"};

	@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
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
        	//idea修改测试git
			//idea修改测试git2
			//idea修改测试git3
        	
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
