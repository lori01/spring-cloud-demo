package com.daimeng.test.transxml;

import java.util.ArrayList;

import com.daimeng.util.XmlTransMappingAnnotation;

@XmlTransMappingAnnotation
public class TestVo {
	
	@XmlTransMappingAnnotation("user_name")
	private String userName;
	@XmlTransMappingAnnotation("user_age")
	private Integer uaerAge;
	@XmlTransMappingAnnotation("local_address")
	private String localAddress;
	@XmlTransMappingAnnotation("sex_cd")
	private String sexCd;
	@XmlTransMappingAnnotation("student_no")
	private Long studentNo;
	@XmlTransMappingAnnotation("point")
	private Double point;
	@XmlTransMappingAnnotation("is_first")
	private boolean isFirst;
	@XmlTransMappingAnnotation("head_list")
	private ArrayList<TestHead> headList;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUaerAge() {
		return uaerAge;
	}

	public void setUaerAge(Integer uaerAge) {
		this.uaerAge = uaerAge;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public String getSexCd() {
		return sexCd;
	}

	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}

	public Long getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(Long studentNo) {
		this.studentNo = studentNo;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public ArrayList<TestHead> getHeadList() {
		return headList;
	}

	public void setHeadList(ArrayList<TestHead> headList) {
		this.headList = headList;
	}
}
