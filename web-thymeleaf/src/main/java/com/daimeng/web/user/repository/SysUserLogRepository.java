package com.daimeng.web.user.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.daimeng.web.user.entity.SysUser;
import com.daimeng.web.user.entity.SysUserLog;

public interface SysUserLogRepository extends JpaRepository<SysUserLog, Integer>,JpaSpecificationExecutor<SysUserLog>{

	//@Query("select * from SysUserLog where uid=:uid order by id desc limit (0,1)")
	public SysUserLog findFirstByUidOrderByIdDesc(Integer uid);
	
}
