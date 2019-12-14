package com.daimeng.test.json;

import java.util.ArrayList;

public class Person {

	private Integer id;
	private String name;
	private ArrayList<String> list;
	Person(){
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getList() {
		return list;
	}
	public void setList(ArrayList<String> list) {
		this.list = list;
	}
}
