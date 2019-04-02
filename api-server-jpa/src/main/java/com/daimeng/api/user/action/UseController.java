package com.daimeng.api.user.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daimeng.api.user.entity.SysUser;
import com.daimeng.api.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UseController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUserList")
	public List<SysUser> getUsers() {
		ArrayList<SysUser> users = userService.getAll();
		return users;
	}
	
	@RequestMapping("/getUserPage/{pageNum}")
	public Page<SysUser> getUserPage(@PathVariable("pageNum") Integer pageNum) {
		Page<SysUser> users = userService.getPage(pageNum);
		return users;
	}
	
	@RequestMapping("/getUser/{loginName}")
	public List<SysUser> getUserByLoginName(@PathVariable("loginName") String loginName) {
		SysUser user = new SysUser();
		user.setLoginName(loginName);
		ArrayList<SysUser> users = userService.getUserByLoginName(user);
		return users;
	}
	
	@RequestMapping("/saveUser")
	public String saveUser(String loginName,String password) {
		SysUser user = new SysUser();
		user.setLoginName(loginName);
		user.setPassword(password);
		try {
			userService.saveUser(user);
			return "新增用户成功";
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}
	
}
