package com.daimeng.web.system.service;

import org.springframework.data.domain.Page;

import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.user.entity.SysPermission;
import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysRolePermission;
import com.daimeng.web.user.entity.SysUserRole;

public interface SystemService {

	public Page<SysPermission> findSysPermission(Integer page);
	public Page<SysRole> findSysRole(Integer page);
	
	public ResponseVo saveSysPermission(SysPermission permission);
	public ResponseVo saveSysRole(SysRole role);
	public ResponseVo saveSysRolePermission(SysRolePermission rolePermission);
	public ResponseVo saveSysUserRole(SysUserRole userRole);
	
}
