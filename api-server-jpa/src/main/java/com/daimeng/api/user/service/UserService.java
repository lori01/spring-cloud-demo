package com.daimeng.api.user.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.daimeng.api.user.entity.SysUser;

public interface UserService {

	public ArrayList<SysUser> getAll();
	public Page<SysUser> getPage(Integer pageNum);
	public ArrayList<SysUser> getUserByLoginName(SysUser user);
	public void saveUser(SysUser user);
}
