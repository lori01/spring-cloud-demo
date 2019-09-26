package com.daimeng.test.transxml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.daimeng.util.annotation.XmlTransMappingAnnotation;

public class TransXmlUtils {

	public static void main(String[] args) {
		TestVo vo = new TestVo();
		vo.setLocalAddress("中山街345号");
		vo.setSexCd("01");
		vo.setUaerAge(25);
		vo.setUserName("张三");
		vo.setFirst(true);
		vo.setPoint(35.25);
		vo.setStudentNo(2007813015l);
		//System.out.println(obj2xml(vo));
		
		TestHead head = new TestHead();
		head.setAppNo(1);
		head.setTranNo("001");
		head.setTranTime("2019-05-31");

		ArrayList<TestHead> list = new ArrayList<TestHead>();
		TestHead h1 = new TestHead();
		TestHead h2 = new TestHead();
		h1.setAppNo(1);
		h1.setTranNo("001");
		h1.setTranTime("2019-05-31");
		h2.setAppNo(2);
		h2.setTranNo("002");
		h2.setTranTime("2019-06-05");

		list.add(h1);
		list.add(h2);
		vo.setHeadList(list);

		System.out.println(getEcifEsbXml(head, vo));

	}
	
	/**
	 * 
	* @功能描述: 最终报文
	* @方法名称: getEcifEsbXml 
	* @路径 com.daimeng.annotation 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年6月1日 下午1:22:50 
	* @version V1.0   
	* @param sysHead
	* @param appHead
	* @param body
	* @return 
	* @return String
	 */
	public static String getEcifEsbXml(Object head,Object body){
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<service>");
		xml.append("<Head>");
		xml.append(obj2xml(head));
		xml.append("</Head>");
		
		xml.append("<Body>");
		xml.append(obj2xml(body));
		xml.append("</Body>");
		xml.append("</service>");
		return xml.toString();
	}
	/**
	 * 
	* @功能描述: 对象转xml报文
	* @方法名称: obj2xml 
	* @路径 com.daimeng.annotation 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月20日 下午3:09:46 
	* @version V1.0   
	* @param object
	* @return 
	* @return String
	 */
	public static String obj2xml(Object object){
		String xml = "";
		Class<?> clz = object.getClass(); 
		//判断clz是不是使用了EsbXmlAnnotation的注解接口
		boolean hasAnnotation = clz.isAnnotationPresent(com.daimeng.util.annotation.XmlTransMappingAnnotation.class);
		Field[] fields = clz.getDeclaredFields(); 
		for (Field field : fields) {
			String xmlName = field.getName();
			if(hasAnnotation){
				XmlTransMappingAnnotation xmlTransMappingAnnotation = field.getAnnotation(com.daimeng.util.annotation.XmlTransMappingAnnotation.class);
				if(xmlTransMappingAnnotation != null){
					xmlName = xmlTransMappingAnnotation.value();
				}
			}

			try {
				String methodName = field.getName();
				if(!"is".equals(methodName.subSequence(0, 2))){
					methodName = "get" + getMethodName(field.getName());
				}
				Method m = null;  
				if(object.getClass().getMethod(methodName) != null){
					m = (Method) object.getClass().getMethod(methodName);  
				}
				if(m != null){
					//判断是否是java.lang.*里面的基本变量的对象 
					//判断是否是基本变量,基本变量的类型即为int long float boolean,没有包路劲
					if(field.getType().getName().indexOf("java.lang.") > -1 || field.getType().getName().indexOf(".") == -1){
						Object val = m.invoke(object);
						if(val != null){
							xml += "<" + xmlName + ">";
							xml += String.valueOf(val);
							xml += "</" + xmlName + ">";
						}
					}
					else if(field.getType().getName().indexOf("com.daimeng.") > -1){
						Object obj = m.invoke(object);
						if(obj != null){
							xml += "<" + xmlName + ">";
							xml += obj2xml(obj);
							xml += "</" + xmlName + ">";
						}
					}
					else if(field.getType().getName().indexOf("java.util.List") > -1 || field.getType().getName().indexOf("java.util.ArrayList") > -1){
						List list = (List) m.invoke(object);
						if(list != null){
							xml += "<array>";
							for(Object obj : list){
								xml += "<" + xmlName + ">";
								xml += obj2xml(obj);
								xml += "</" + xmlName + ">";
							}
							xml += "</array>";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		}
		return xml;
	}
	
	/**
	 * 
	* @功能描述: 字符串首字母转为大写，为了根据字段名称获取get方法名称
	* @方法名称: getMethodName 
	* @路径 com.daimeng.annotation 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月20日 下午3:09:34 
	* @version V1.0   
	* @param fildeName
	* @return
	* @throws Exception 
	* @return String
	 */
	public static String getMethodName(String fildeName) throws Exception{  
		if (fildeName.charAt(0) >= 'A' && fildeName.charAt(0) <= 'Z') {
			return fildeName;
		}else{
			byte[] items = fildeName.getBytes();  
			items[0] = (byte) ((char) items[0] - 'a' + 'A');  
			return new String(items);  
		}
	} 

}
