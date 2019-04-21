package com.daimeng.web.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.daimeng.web.article.entity.ArticleInfo;

@Entity
public class SysUserLog implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false, unique = true,insertable=false, updatable=false)
	private Integer uid;
	@Column(nullable = false, unique = true)
	private String url;
	@Column(nullable = false, unique = true)
	private String parameter;
	@Column(nullable = false, unique = true)
	private String comment;
	@Column(nullable = false, unique = true)
	private Date createTm;
	@Column(nullable = false, unique = true)
	private Long executeTime;
	
	@ManyToOne(fetch= FetchType.LAZY)//
	@JoinColumn(name="uid")
	private SysUser sysUser;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreateTm() {
		return createTm;
	}
	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}
	public Long getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	
	
}
