package com.daimeng.web.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

//@Embeddable
public class SysRolePermissionId implements Serializable{
	private Integer permissionId;
	private Integer roleId;//主键.
	
	public SysRolePermissionId() {
		super();
	}
	public SysRolePermissionId(Integer permissionId,Integer roleId) {
		super();
		this.permissionId = permissionId;
		this.roleId = roleId;
	}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((permissionId == null) ? 0 : permissionId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysRolePermissionId other = (SysRolePermissionId) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (permissionId == null) {
			if (other.permissionId != null)
				return false;
		} else if (!permissionId.equals(other.permissionId))
			return false;
		return true;
	}
	
	
}
