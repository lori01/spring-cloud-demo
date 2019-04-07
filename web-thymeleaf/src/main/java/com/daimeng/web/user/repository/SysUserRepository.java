package com.daimeng.web.user.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.daimeng.web.user.entity.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Integer>,JpaSpecificationExecutor<SysUser>{

	//public PageInfo<UserEntity> getPage(Integer pageNum);
	public ArrayList<SysUser> findByLoginName(SysUser user);
	
}
