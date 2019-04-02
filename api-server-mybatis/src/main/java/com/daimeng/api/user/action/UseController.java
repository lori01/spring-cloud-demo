package com.daimeng.api.user.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daimeng.api.user.entity.UserEntity;
import com.daimeng.api.user.service.UserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/user")
public class UseController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUserList")
	public List<UserEntity> getUsers() {
		ArrayList<UserEntity> users = userService.getAll();
		return users;
	}
	
	@RequestMapping("/getUserPage/{pageNum}")
	public PageInfo<UserEntity> getUserPage(@PathVariable("pageNum") Integer pageNum) {
		PageInfo<UserEntity> users = userService.getPage(pageNum);
		return users;
	}
	
	@RequestMapping("/getUser/{loginName}")
	public List<UserEntity> getUserByLoginName(@PathVariable("loginName") String loginName) {
		UserEntity user = new UserEntity();
		user.setLoginName(loginName);
		ArrayList<UserEntity> users = userService.getUserByLoginName(user);
		return users;
	}
	
	@RequestMapping("/saveUser")
	public String saveUser(String loginName,String password) {
		UserEntity user = new UserEntity();
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
