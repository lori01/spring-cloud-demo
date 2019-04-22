package com.daimeng.web.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.daimeng.web.user.entity.SysUserRole;
import com.daimeng.web.user.entity.SysUserRoleId;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, SysUserRoleId>,JpaSpecificationExecutor<SysUserRole>{

}
