package com.daimeng.api.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class SysUser implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false, unique = true)
	private String loginName;
	@Column(nullable = false, unique = true)
	private String password;
	@Column(nullable = false, unique = true)
	private String realname;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private String phone;
	
	public SysUser(String loginName, String password, String realname) {
		super();
		this.loginName = loginName;
		this.password = password;
		this.realname = realname;
	}
	
	public SysUser() {
		super();
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealbame(String realname) {
		this.realname = realname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
