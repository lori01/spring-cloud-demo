package com.daimeng.test.obj2excel;

import java.util.ArrayList;

public class Bus {

	private int id;
	private String no;
	private int sit;
	private Person curDeriver;
	private ArrayList<Person> driverList;
	private ArrayList<Person> customerList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public int getSit() {
		return sit;
	}
	public void setSit(int sit) {
		this.sit = sit;
	}
	public Person getCurDeriver() {
		return curDeriver;
	}
	public void setCurDeriver(Person curDeriver) {
		this.curDeriver = curDeriver;
	}
	public ArrayList<Person> getDriverList() {
		return driverList;
	}
	public void setDriverList(ArrayList<Person> driverList) {
		this.driverList = driverList;
	}
	public ArrayList<Person> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(ArrayList<Person> customerList) {
		this.customerList = customerList;
	}
	
	
	
}
