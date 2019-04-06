package com.daimeng.web.user.entity;

import java.io.Serializable;


public class UserEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String loginName;
	private String password;
	private String realName;
	private String email;
	private String phone;
	private String permission;
	private String salt;
	private String img;
	private String sexCd;
	
	public UserEntity(String loginName, String password, String realName) {
		super();
		this.loginName = loginName;
		this.password = password;
		this.realName = realName;
	}
	
	public UserEntity() {
		super();
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSexCd() {
		return sexCd;
	}

	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}
	
	
}
