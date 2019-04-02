package com.daimeng.web.user.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SysUserRole implements Serializable{

	@Id
	@GeneratedValue
	private Integer uid;
	
	@Id
	@GeneratedValue
	private Integer roleId;

	

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	
	
	
}
