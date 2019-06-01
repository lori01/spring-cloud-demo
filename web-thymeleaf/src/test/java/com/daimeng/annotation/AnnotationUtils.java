package com.daimeng.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationUtils {

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
		xml.append("<transaction>");
		xml.append("<sys_head>");
		xml.append(obj2xml(head));
		xml.append("</sys_head>");
		
		xml.append("<body>");
		xml.append(obj2xml(body));
		xml.append("</body>");
		xml.append("</transaction>");
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
		boolean hasAnnotation = clz.isAnnotationPresent(com.daimeng.annotation.XmlAnnotation.class);
		Field[] fields = clz.getDeclaredFields(); 
		for (Field field : fields) {
			String xmlName = field.getName();
			if(hasAnnotation){
				XmlAnnotation xmlAnnotation = field.getAnnotation(com.daimeng.annotation.XmlAnnotation.class);
				if(xmlAnnotation != null){
					xmlName = xmlAnnotation.value();
				}
			}
			xml += "<" + xmlName + ">";
			try {
				String methodName = field.getName();
				if(!"is".equals(methodName.subSequence(0, 2))){
					methodName = "get" + getMethodName(field.getName());
				}
				Method m = (Method) object.getClass().getMethod(methodName);  
				if(m != null){
					//判断是否是java.lang.*里面的基本变量的对象 
					//判断是否是基本变量,基本变量的类型即为int long float boolean,没有包路劲
					if(field.getType().getName().indexOf("java.lang.") > -1 || field.getType().getName().indexOf(".") == -1){
						Object val = m.invoke(object);
						if(val != null){
							xml += String.valueOf(val);
						}
					}
					else if(field.getType().getName().indexOf("com.daimeng.") > -1){
						Object obj = m.invoke(object);
						if(obj != null){
							xml += obj2xml(obj);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			xml += "</" + xmlName + ">";
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
