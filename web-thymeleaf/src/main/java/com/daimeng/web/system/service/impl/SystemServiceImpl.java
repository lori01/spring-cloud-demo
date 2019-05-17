package com.daimeng.web.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daimeng.util.Constants;
import com.daimeng.web.common.ResponseVo;
import com.daimeng.web.system.service.SystemService;
import com.daimeng.web.user.entity.SysPermission;
import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysRolePermission;
import com.daimeng.web.user.entity.SysUserRole;
import com.daimeng.web.user.repository.SysPermissionRepository;
import com.daimeng.web.user.repository.SysRolePermissionRepository;
import com.daimeng.web.user.repository.SysRoleRepository;
import com.daimeng.web.user.repository.SysUserRoleRepository;

@Service
public class SystemServiceImpl implements SystemService{

	@Autowired
	private SysRoleRepository roleRepository;
	@Autowired
	private SysUserRoleRepository userRoleRepository;
	@Autowired
	private SysPermissionRepository permissionRepository;
	@Autowired
	private SysRolePermissionRepository rolePermissionRepository;
	
	@Override
	public Page<SysPermission> findSysPermission(Integer page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Page<SysPermission> list = permissionRepository.findAll(pageable);
		return list;
	}

	@Override
	public Page<SysRole> findSysRole(Integer page) {
		Pageable pageable = new PageRequest(page, Constants.PAGE_SIZE_10);
		Page<SysRole> list = roleRepository.findAll(pageable);
		return list;
	}

	@Override
	public ResponseVo saveSysPermission(SysPermission permission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseVo saveSysRole(SysRole role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseVo saveSysRolePermission(SysRolePermission rolePermission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseVo saveSysUserRole(SysUserRole userRole) {
		// TODO Auto-generated method stub
		return null;
	}

}
