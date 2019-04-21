package com.daimeng.web.user.entity;

import java.io.Serializable;
import java.util.Date;

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
	@Column(nullable = false, unique = true)
	private String permission;
	@Column(nullable = false, unique = true)
	private String salt;
	@Column(nullable = false, unique = true)
	private String img;
	
	//@Column(columnDefinition="enum('male','female')")
	@Column(nullable = false, unique = true)
    private String sexCd;
	
	/*@ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<SysRole> roleList;// 一个用户具有多个角色*/	
	
	//不映射数据库
	@Transient
	private Date lastActionTm;
	
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

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	/*public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}*/

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

	public Date getLastActionTm() {
		return lastActionTm;
	}

	public void setLastActionTm(Date lastActionTm) {
		this.lastActionTm = lastActionTm;
	}

	
	
	
	
}
