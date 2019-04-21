package com.daimeng.web.common;

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
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
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
}
