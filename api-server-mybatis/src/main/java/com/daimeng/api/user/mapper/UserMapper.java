package com.daimeng.api.user.mapper;

import java.util.ArrayList;

import com.daimeng.api.user.entity.UserEntity;

public interface UserMapper {

	public ArrayList<UserEntity> getAll();
	public ArrayList<UserEntity> getUserByLoginName(UserEntity user);
	public void saveUser(UserEntity user);
	public Long countUserByLoginName(String loginName);
}
