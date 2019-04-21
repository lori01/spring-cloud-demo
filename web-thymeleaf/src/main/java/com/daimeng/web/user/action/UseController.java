package com.daimeng.web.user.action;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daimeng.util.Constants;
import com.daimeng.util.DateUtils;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.SysUserLog;
import com.daimeng.web.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UseController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/list/{page}")
	public String list(Model model,@PathVariable Integer page) {
		if(page != null && page >0){
			page--;
		}else page = 0;
		Page<SysUser> users = userService.getUserPage(page);
		model.addAttribute("list",users);
		
		model.addAttribute("cpage",page+1);
		if(users.getTotalPages() > 0){
			model.addAttribute("pages",Integer.valueOf(users.getTotalPages()));
		}else model.addAttribute("pages",1);
		
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		if(cuser != null){
			model.addAttribute("myname",cuser.getRealname());
			model.addAttribute("cuser",cuser);
		}
		return "user/list";
	}
	
	@RequestMapping("/log/{uid}/{page}")
	public String log(Model model,@PathVariable Integer uid,@PathVariable Integer page) {
		if(page != null && page >0){
			page--;
		}else page = 0;
		
		SysUserLog info = new SysUserLog();
		info.setUid(uid);
		Page<SysUserLog> logs = userService.getUserLogPage(info, page);
		model.addAttribute("list",logs);
		
		model.addAttribute("cpage",page+1);
		if(logs.getTotalPages() > 0){
			model.addAttribute("pages",Integer.valueOf(logs.getTotalPages()));
		}else model.addAttribute("pages",1);
		
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		if(cuser != null){
			model.addAttribute("myname",cuser.getRealname());
			model.addAttribute("cuser",cuser);
		}
		return "user/log";
	}

	@RequestMapping(value="/update",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo update(SysUser info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		
		ResponseVo vo = new ResponseVo();
		try {
			vo = userService.updateUserBscInf(info);
			if(vo.getStatus() == 100){
				vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
				SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_USER2, vo.getObj());
			}
			return vo;
		} catch (Exception e) {
			vo.setStatus(200);
			vo.setDesc(e.getMessage());
			return vo;
		}
		
	}
	
	@RequestMapping(value="/updatePwd",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo updatePwd(Integer uid, String oldPwd, String newPwd) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		
		ResponseVo vo = new ResponseVo();
		try {
			vo = userService.updateUserPd(uid, oldPwd, newPwd);
			if(vo.getStatus() == 100){
				vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
			}
			return vo;
		} catch (Exception e) {
			vo.setStatus(200);
			vo.setDesc(e.getMessage());
			return vo;
		}
		
	}
	
	@RequestMapping(value="/add",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo add(SysUser info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER2);
		ResponseVo vo = new ResponseVo();
		try {
			vo = userService.add(info);
			if(vo.getStatus() == 100){
				vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
			}
			return vo;
		} catch (Exception e) {
			vo.setStatus(200);
			vo.setDesc(e.getMessage());
			return vo;
		}
		
	}
	
}
