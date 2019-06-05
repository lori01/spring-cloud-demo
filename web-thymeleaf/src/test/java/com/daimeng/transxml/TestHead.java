package com.daimeng.transxml;

@XmlAnnotation
public class TestHead {
	
	//@XmlAnnotation("tran_no")
	private String tranNo;
	//@XmlAnnotation("app_no")
	private Integer appNo;
	//@XmlAnnotation("tran_time")
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
