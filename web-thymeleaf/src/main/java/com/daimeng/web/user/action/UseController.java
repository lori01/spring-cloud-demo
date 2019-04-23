package com.daimeng.web.user.action;

import java.util.Date;
import java.util.List;

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
import com.daimeng.web.common.BaseController;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.SysUserLog;
import com.daimeng.web.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UseController extends BaseController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/list/{page}")
	public String list(Model model,@PathVariable Integer page) {
		page = getPageNum(page);
		Page<SysUser> users = userService.getUserPage(page);
		model.addAttribute("list",users);
		
		setPageToModel(model, users, page);
		setCurrentUser(model);
		
		return "user/list";
	}
	
	@RequestMapping("/log/{uid}/{page}")
	public String log(Model model,@PathVariable Integer uid,@PathVariable Integer page) {
		page = getPageNum(page);
		SysUserLog info = new SysUserLog();
		info.setUid(uid);
		Page<SysUserLog> logs = userService.getUserLogPage(info, page);
		
		setPageToModel(model, logs, page);
		setCurrentUser(model);
		
		return "user/log";
	}

	@RequestMapping(value="/update",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo update(SysUser info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		
		ResponseVo vo = new ResponseVo();
		vo = userService.updateUserBscInf(info);
		if(vo.getStatus() == 100){
			vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
			SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_USER, vo.getObj());
		}
		return vo;
		
	}
	
	@RequestMapping(value="/updatePwd",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo updatePwd(Integer uid, String oldPwd, String newPwd) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		
		ResponseVo vo = new ResponseVo();
		vo = userService.updateUserPd(uid, oldPwd, newPwd);
		if(vo.getStatus() == 100){
			vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
		}
		return vo;
		
	}
	
	@RequestMapping(value="/add",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo add(SysUser info) {
		SysUser cuser = (SysUser)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		ResponseVo vo = new ResponseVo();
		vo = userService.addUser(info);
		if(vo.getStatus() == 100){
			vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
		}
		return vo;
		
	}
	
	@RequestMapping(value="/rolelist",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<SysRole> rolelist() {
		try {
			List<SysRole> list = userService.findAllRole();
			return list;
		} catch (Exception e) {
			return null;
		}
		
	}
	
}
