package com.daimeng.web.user.mapper;

import com.daimeng.web.user.entity.UserEntity;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;

public interface UserMapper {

	public ArrayList<UserEntity> getAll();
	public ArrayList<UserEntity> getUserByParameter(UserEntity user);
	public void insertUser(UserEntity user);
	public void updateUser(UserEntity user);
	public Long countUserByLoginName(String loginName);
	public PageInfo<UserEntity> getPage(UserEntity user);
}
