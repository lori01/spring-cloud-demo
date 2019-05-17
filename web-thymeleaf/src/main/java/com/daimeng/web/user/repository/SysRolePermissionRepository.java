package com.daimeng.web.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.daimeng.web.user.entity.SysRole;
import com.daimeng.web.user.entity.SysRolePermission;
import com.daimeng.web.user.entity.SysRolePermissionId;

public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, SysRolePermissionId>,JpaSpecificationExecutor<SysRole>{

}
