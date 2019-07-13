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
import com.daimeng.web.user.entity.SysUser;
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
		ResponseVo vo = new ResponseVo();
		if(permission != null){
			SysPermission cur = permissionRepository.findOne(permission.getId());
			if(cur != null){
				cur.setAvailable(permission.getAvailable());
				cur.setName(permission.getName());
				cur.setParentId(permission.getParentId());
				cur.setParentIds(permission.getParentIds());
				cur.setPermission(permission.getPermission());
				cur.setResourceType(permission.getResourceType());
				cur.setUrl(permission.getUrl());
				permissionRepository.save(cur);
				vo.setObj(permission);
			}else{
				permissionRepository.save(permission);
				vo.setObj(permission);
			}
			
			
			vo.setStatus(100);
			vo.setDesc("更新成功!");
			
			return vo;
		}
		vo.setStatus(200);
		vo.setDesc("更新失败!");
		return vo;
	}

	@Override
	public ResponseVo saveSysRole(SysRole role) {
		ResponseVo vo = new ResponseVo();
		if(role != null){
			SysRole cur = roleRepository.findOne(role.getId());
			if(cur != null){
				cur.setAvailable(role.getAvailable());
				cur.setDescription(role.getDescription());
				cur.setRole(role.getRole());
				roleRepository.save(cur);
				vo.setObj(cur);
			}else{
				roleRepository.save(role);
				vo.setObj(role);
			}
			
			
			vo.setStatus(100);
			vo.setDesc("更新成功!");
			return vo;
		}
		vo.setStatus(200);
		vo.setDesc("更新失败!");
		return vo;
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
