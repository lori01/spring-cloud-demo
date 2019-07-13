package com.daimeng.web.system.action;

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
import com.daimeng.web.common.BaseController;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.system.service.SystemService;
import com.daimeng.web.user.entity.SysPermission;
import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysUser;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@RequestMapping("/menu/{page}")
	public String menu(Model model,@PathVariable Integer page) {
		page = getPageNum(page);
		Page<SysPermission> list = systemService.findSysPermission(page);
		setPageToModel(model, list, page);
		return "system/menu";
	}
	
	@RequestMapping("/role/{page}")
	public String role(Model model,@PathVariable Integer page) {
		page = getPageNum(page);
		Page<SysRole> list = systemService.findSysRole(page);
		setPageToModel(model, list, page);
		return "system/role";
	}
	
	@RequestMapping(value="/saveSysPermission",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo saveSysPermission(SysPermission permission) {
		ResponseVo vo = new ResponseVo();
		vo = systemService.saveSysPermission(permission);
		if(vo.getStatus() == 100){
			vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
		}
		return vo;
	}
	
	@RequestMapping(value="/saveSysRole",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseVo saveSysRole(SysRole role) {
		ResponseVo vo = new ResponseVo();
		vo = systemService.saveSysRole(role);
		if(vo.getStatus() == 100){
			vo.setDesc(DateUtils.getDateStrFormat(new Date(), "yyyy年MM月dd日 HH:mm"));
		}
		return vo;
	}
	
	
}
