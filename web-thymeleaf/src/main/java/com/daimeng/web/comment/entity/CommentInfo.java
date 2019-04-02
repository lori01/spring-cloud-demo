package com.daimeng.web.comment.entity;

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

import com.daimeng.web.article.entity.ArticleInfo;
import com.daimeng.web.user.entity.SysUser;

@Entity
public class CommentInfo implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private Integer layer;
	
	@Column(nullable = false, unique = true)
	private Integer parentCommentId;
	
	@Column(nullable = false, unique = true)
	private String context;
	
	@Column(nullable = false, unique = true)
	private Integer statusCd;
	
	@Column(nullable = false, unique = true)
	private Date createTm;
	
	@Column(nullable = false, unique = true)
	private Date updateTm;
	
	@Column(nullable = false, unique = true,insertable=false, updatable=false)
	private int createUid;
	
	@Column(nullable = false, unique = true,insertable=false, updatable=false)
	private int updateUid;
	
	@ManyToOne(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
	@JoinColumn(name="createUid")
	private SysUser createUser;
	
	@ManyToOne(fetch= FetchType.LAZY)//
	@JoinColumn(name="updateUid")
	private SysUser updateUser;
	
	@Column(nullable = false, unique = true,insertable=false, updatable=false)
	private Integer articleId;
	
	@ManyToOne(fetch= FetchType.LAZY)//
	@JoinColumn(name="articleId")
	private ArticleInfo articleInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public int getCreateUid() {
		return createUid;
	}

	public void setCreateUid(int createUid) {
		this.createUid = createUid;
	}

	public int getUpdateUid() {
		return updateUid;
	}

	public void setUpdateUid(int updateUid) {
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

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public ArticleInfo getArticleInfo() {
		return articleInfo;
	}

	public void setArticleInfo(ArticleInfo articleInfo) {
		this.articleInfo = articleInfo;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Integer getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Integer parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

	public Integer getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(Integer statusCd) {
		this.statusCd = statusCd;
	}
	
	
	
}
