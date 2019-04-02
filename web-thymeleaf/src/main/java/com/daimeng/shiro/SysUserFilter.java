package com.daimeng.shiro;

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
import com.daimeng.web.user.entity.UserEntity;

public class SysUserFilter extends AccessControlFilter {

	@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("------->SysUserFilter.preHandle------>执行<------");
		request.setAttribute("basePath", ((HttpServletRequest)request).getContextPath());
        Subject subject = getSubject(request, response);
        if (subject == null) {
        	System.out.println("------->SysUserFilter.preHandle------>subject空<------");
            return true;
        }
        
        if(subject.getPrincipal()==null){
        	System.out.println("------->SysUserFilter.preHandle------>subject.getPrincipal()空<------");
        	return true;
        }
        
        UserEntity user = (UserEntity) ((HttpServletRequest)request).getSession().getAttribute(Constants.CURRENT_USER1);
        SysUser suser = (SysUser) ((HttpServletRequest)request).getSession().getAttribute(Constants.CURRENT_USER2);
        if(suser != null){
        	System.out.println("------->SysUserFilter.preHandle------>user=" + user.getRealName());
        	boolean hasAuth = true;
        	HttpServletRequest req = (HttpServletRequest)request;
        	System.out.println("-------->getContextPath()--->" + req.getContextPath());
        	System.out.println("-------->getRemoteAddr()--->" + req.getRemoteAddr());
        	System.out.println("-------->getServletPath()--->" + req.getServletPath());
        	System.out.println("-------->getRequestURL()--->" + req.getRequestURL().toString());
        	System.out.println("-------->getRequestURI()--->" + req.getRequestURI());
        	System.out.println("-------->getQueryString()--->" + req.getQueryString());
        	System.out.println("-------->getRemoteUser()--->" + req.getRemoteUser());
        	
        	String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/";
        	System.out.println("-------->basePath--->" + basePath);
        	String realPath =req.getServletContext().getRealPath("/");
        	System.out.println("-------->realPath--->" + realPath);
        	
        	String path = req.getRequestURI();
        	System.out.println(path);
        	
        	/*if(!"/403".equals(path) && !"/".equals(path) && !"/index".equals(path)
        			&& !"daimeng".equals(suser.getLoginName()) && !"zhangfan".equals(suser.getLoginName())){
        		for(SysRole role : suser.getRoleList()){
            		for(SysPermission per : role.getPermissions()){
            			if(path.indexOf(per.getUrl()) > -1){
            				hasAuto = true;
            				break;
            			}
            		}
            	}
        	}
        	else hasAuth = true;*/
        	//idea修改测试git
			//idea修改测试git2
        	
        	if(!hasAuth){
        		((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/403"); 
				return false;
        	}
        }else{
        	System.out.println("------->SysUserFilter.preHandle------>user空空空空<------");
        	//加载用户session
        	
        }
        
		return true;
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		System.out.println("------->SysUserFilter.isAccessAllowed------>执行<------");
		UserEntity sysUser = (UserEntity) request.getAttribute(Constants.CURRENT_USER1);
        if (sysUser == null) {
        	System.out.println("------->SysUserFilter.isAccessAllowed------>user空空空空");
            return true;
        }else return false;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		System.out.println("------->SysUserFilter.onAccessDenied------>执行<------");
		getSubject(request, response).logout();
        saveRequestAndRedirectToLogin(request, response);
        return true;
	}

}
