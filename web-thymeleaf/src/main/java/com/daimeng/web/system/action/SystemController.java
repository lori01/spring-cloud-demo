package com.daimeng.web.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daimeng.web.common.BaseController;
import com.daimeng.web.system.service.SystemService;
import com.daimeng.web.user.entity.SysPermission;
import com.daimeng.web.user.entity.SysRole;

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
	
	
}
