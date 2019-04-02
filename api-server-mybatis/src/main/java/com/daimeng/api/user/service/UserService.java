package com.daimeng.api.user.service;

import java.util.ArrayList;

import com.daimeng.api.user.entity.UserEntity;
import com.github.pagehelper.PageInfo;

public interface UserService {

	public ArrayList<UserEntity> getAll();
	public PageInfo<UserEntity> getPage(Integer pageNum);
	public ArrayList<UserEntity> getUserByLoginName(UserEntity user);
	public void saveUser(UserEntity user);
}
