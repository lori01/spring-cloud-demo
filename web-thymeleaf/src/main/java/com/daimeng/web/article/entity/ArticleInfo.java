package com.daimeng.web.article.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.daimeng.web.user.entity.SysUser;

@Entity
public class ArticleInfo  implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String title;
	
	@Column(nullable = false, unique = true)
	private String context;
	
	@Column(nullable = false, unique = true)
	private String shortContext;
	
	@Column(nullable = false, unique = true)
	private Date createTm;
	
	@Column(nullable = false, unique = true)
	private Date updateTm;
	
	@Column(nullable = false, unique = true,insertable=false, updatable=false)
	private Integer createUid;
	
	@Column(nullable = false, unique = true,insertable=false, updatable=false)
	private Integer updateUid;
	
	@ManyToOne(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
	@JoinColumn(name="createUid")
	private SysUser createUser;
	
	@ManyToOne(fetch= FetchType.LAZY)//
	@JoinColumn(name="updateUid")
	private SysUser updateUser;
	
	//@Column(columnDefinition="enum('0','1')")
	@Column(nullable = false, unique = true)
    private Integer statusCd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public Date getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}

	public Integer getCreateUid() {
		return createUid;
	}

	public void setCreateUid(Integer createUid) {
		this.createUid = createUid;
	}

	public Integer getUpdateUid() {
		return updateUid;
	}

	public void setUpdateUid(Integer updateUid) {
		this.updateUid = updateUid;
	}

	public SysUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(SysUser createUser) {
		this.createUser = createUser;
	}

	public SysUser getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(SysUser updateUser) {
		this.updateUser = updateUser;
	}

	public String getShortContext() {
		return shortContext;
	}

	public void setShortContext(String shortContext) {
		this.shortContext = shortContext;
	}

	public Integer getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(Integer statusCd) {
		this.statusCd = statusCd;
	}

	
	
	
	
}
