package com.daimeng.web.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.daimeng.util.Constants;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.SysUserLog;

public class BaseController {

	public void setPageToModel(Model model,Page list,Integer page){
		model.addAttribute("list",list);
		model.addAttribute("cpage",page+1);
		if(list.getTotalPages() > 0){
			model.addAttribute("pages",Integer.valueOf(list.getTotalPages()));
		}else model.addAttribute("pages",1);
	}
	
	public void setCurrentUser(Model model){
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		if(cuser != null){
			model.addAttribute("myname",cuser.getRealname());
			model.addAttribute("cuser",cuser);
		}
	}
	
	public Integer getPageNum(Integer page){
		if(page != null && page >0){
			page--;
		}else page = 0;
		return page;
	}
	
	public ResponseVo createSuccess(String desc){
		ResponseVo vo = new ResponseVo();
		vo.setStatus(100);
		vo.setDesc(desc);
		return vo;
	}
	public ResponseVo createSuccess(String desc,Object obj){
		ResponseVo vo = new ResponseVo();
		vo.setStatus(100);
		vo.setDesc(desc);
		vo.setObj(obj);
		return vo;
	}
	public ResponseVo createError(String desc){
		ResponseVo vo = new ResponseVo();
		vo.setStatus(200);
		vo.setDesc(desc);
		return vo;
	}
	public ResponseVo createError(String desc,Object obj){
		ResponseVo vo = new ResponseVo();
		vo.setStatus(200);
		vo.setDesc(desc);
		vo.setObj(obj);
		return vo;
	}
	
	public String getBashPathWithPort(HttpServletRequest request){
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		return basePath;
	}
	public String getBashPathWithoutPort(HttpServletRequest request){
		String basePath = request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
		return basePath;
	}
}
