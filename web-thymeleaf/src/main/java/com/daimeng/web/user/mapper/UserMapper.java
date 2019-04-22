package com.daimeng.web.user.mapper;

import com.daimeng.web.user.vo.UserVO;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;

public interface UserMapper {

	public ArrayList<UserVO> getAll();
	public ArrayList<UserVO> getUserByParameter(UserVO user);
	public void insertUser(UserVO user);
	public void updateUser(UserVO user);
	public Long countUserByLoginName(String loginName);
	public PageInfo<UserVO> getPage(UserVO user);
}
