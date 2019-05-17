package com.daimeng.web.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.daimeng.web.user.entity.SysPermission;
import com.daimeng.web.user.entity.SysRole;

public interface SysPermissionRepository extends JpaRepository<SysPermission, Integer>,JpaSpecificationExecutor<SysRole>{

}
