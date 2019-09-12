package com.daimeng.test.obj2excel;

import java.util.ArrayList;

public class Company {
	
	private int id;
	private String no;
	private String name;
	private String address;
	private int year;
	
	private Person ceo;
	private Person cto;
	private Person coo;
	private Person cfo;

	private ArrayList<Bus> busList;
	private ArrayList<Person> staffList;
	private ArrayList<Person> studentList;
	
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public ArrayList<Bus> getBusList() {
		return busList;
	}
	public void setBusList(ArrayList<Bus> busList) {
		this.busList = busList;
	}
	public ArrayList<Person> getStaffList() {
		return staffList;
	}
	public void setStaffList(ArrayList<Person> staffList) {
		this.staffList = staffList;
	}
	public ArrayList<Person> getStudentList() {
		return studentList;
	}
	public void setStudentList(ArrayList<Person> studentList) {
		this.studentList = studentList;
	}
	public Person getCeo() {
		return ceo;
	}
	public void setCeo(Person ceo) {
		this.ceo = ceo;
	}
	public Person getCto() {
		return cto;
	}
	public void setCto(Person cto) {
		this.cto = cto;
	}
	public Person getCoo() {
		return coo;
	}
	public void setCoo(Person coo) {
		this.coo = coo;
	}
	public Person getCfo() {
		return cfo;
	}
	public void setCfo(Person cfo) {
		this.cfo = cfo;
	}
	
	
	
}
