package com.daimeng.annotation;

@XmlAnnotation
public class TestVo {
	
	@XmlAnnotation(xml="user_name")
	private String userName;
	@XmlAnnotation(xml="user_age")
	private Integer uaerAge;
	@XmlAnnotation(xml="local_address")
	private String localAddress;
	@XmlAnnotation(xml="sex_cd")
	private String sexCd;
	@XmlAnnotation(xml="student_no")
	private Long studentNo;
	@XmlAnnotation(xml="point")
	private Double point;
	@XmlAnnotation(xml="is_first")
	private boolean isFirst;

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
	
	
}
