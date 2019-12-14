package com.daimeng.test.json;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.daimeng.util.ReflectHelper;

public class JsonTest {

	public static void main(String[] args) {
		Person person1 = new Person();
		person1.setId(10);
		person1.setName("shznng33");
		ArrayList list = new ArrayList<String>();
		list.add("01");
		list.add("02");
		list.add("03");
		list.add("04");
		person1.setList(list);
		
		String json = JSON.toJSONString(person1);
		System.out.println(json);
		//Object person = getObjectForName("com.daimeng.test.json.Person");
		Object person = new Object();
		person = JSON.parseObject(json, Object.class);
		System.out.println(Object.class.getName());
	}
	
	public static Object getObjectForName(String className){
    	Class clazz = null;
        try {
            clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	

}
