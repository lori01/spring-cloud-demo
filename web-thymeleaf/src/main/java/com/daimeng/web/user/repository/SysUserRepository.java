package com.daimeng.web.user.repository;

import com.daimeng.web.user.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface SysUserRepository extends JpaRepository<SysUser, Integer>{

	//public PageInfo<UserEntity> getPage(Integer pageNum);
	public ArrayList<SysUser> findByLoginName(SysUser user);
	
}
