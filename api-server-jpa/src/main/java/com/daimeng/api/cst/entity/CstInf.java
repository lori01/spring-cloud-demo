package com.daimeng.api.cst.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class CstInf implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;
	
	//Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列
	
	@Id
	private String cstId;
	
	@Column(nullable = false, unique = true)
	private String cstNm;
	
	@Column(nullable = false, unique = true)
	private String cstType;
	
	
	public String getCstId() {
		return cstId;
	}
	public void setCstId(String cstId) {
		this.cstId = cstId;
	}
	public String getCstNm() {
		return cstNm;
	}
	public void setCstNm(String cstNm) {
		this.cstNm = cstNm;
	}
	public String getCstType() {
		return cstType;
	}
	public void setCstType(String cstType) {
		this.cstType = cstType;
	}
	
	
}
