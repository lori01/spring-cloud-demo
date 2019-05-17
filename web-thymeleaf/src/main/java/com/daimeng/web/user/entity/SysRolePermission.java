package com.daimeng.web.user.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SysRolePermissionId.class)
public class SysRolePermission implements Serializable {

	@Id@GeneratedValue
    private Integer permissionId;//主键.
	
	@Id@GeneratedValue
    private Integer roleId;//主键.

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
}
