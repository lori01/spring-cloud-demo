package com.daimeng.api.cst.entity;

import java.io.Serializable;

public class CstEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private String cstId;
	private String cstNm;
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
