package com.daimeng.api.user.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daimeng.api.user.entity.SysUser;
import com.github.pagehelper.PageInfo;

public interface SysUserRepository extends JpaRepository<SysUser, Integer>{

	//public PageInfo<UserEntity> getPage(Integer pageNum);
	public ArrayList<SysUser> findByLoginName(SysUser user);
	
}
