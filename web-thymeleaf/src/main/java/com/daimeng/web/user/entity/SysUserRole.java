package com.daimeng.web.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SysUserRoleId.class)
public class SysUserRole implements Serializable{

	@Id
	@Column(nullable = false, unique = true, insertable=false, updatable=false)
	private Integer uid;
	
	@Id
	@Column(nullable = false, unique = true, insertable=false, updatable=false)
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
