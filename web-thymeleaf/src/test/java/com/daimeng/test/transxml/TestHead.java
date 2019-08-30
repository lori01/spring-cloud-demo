package com.daimeng.test.transxml;

import com.daimeng.util.XmlTransMappingAnnotation;

@XmlTransMappingAnnotation
public class TestHead {
	
	//@XmlTransMappingAnnotation("tran_no")
	private String tranNo;
	//@XmlTransMappingAnnotation("app_no")
	private Integer appNo;
	//@XmlTransMappingAnnotation("tran_time")
	private String tranTime;
	public String getTranNo() {
		return tranNo;
	}
	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}
	public Integer getAppNo() {
		return appNo;
	}
	public void setAppNo(Integer appNo) {
		this.appNo = appNo;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	

	
	
	
}
