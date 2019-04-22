package com.daimeng.web.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.daimeng.web.user.entity.SysRole;

public interface SysRoleRepository extends JpaRepository<SysRole, Integer>,JpaSpecificationExecutor<SysRole>{

}
