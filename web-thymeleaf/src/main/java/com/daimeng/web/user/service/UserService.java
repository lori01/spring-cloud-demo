package com.daimeng.web.user.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.UserEntity;

public interface UserService {
	public ArrayList<UserEntity> getUserByParameter(UserEntity user);
	
	public Page<SysUser> getUserPage(Integer pageNum);
	public SysUser findSysUser(int id);
	public ResponseVo updateUserBscInf(SysUser user);
	public ResponseVo updateUserPd(Integer uid,String oldPwd,String newPwd);
	public ResponseVo add(SysUser user);
	
	public Page<SysUser> findAllBySpecification(SysUser info,int page);
}
