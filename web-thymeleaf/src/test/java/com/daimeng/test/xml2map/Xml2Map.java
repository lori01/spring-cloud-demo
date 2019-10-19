package com.daimeng.test.xml2map;

import java.util.Map;

import net.sf.json.xml.XMLSerializer;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;

public class Xml2Map {

	public static void main(String[] args) throws DocumentException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><Head><title><![CDATA[标题]]></title><context><![CDATA[内容]]></context><contextType><![CDATA[01]]></contextType></Head><Body></Body></service>";
		//创建 XMLSerializer对象
        XMLSerializer xmlSerializer = new XMLSerializer();
        //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）
        String result = xmlSerializer.read(xml).toString();
        //输出json内容
        System.out.println(result);
        Map maps = (Map)JSON.parse(result);  
        System.out.println(((Map)maps.get("Head")).get("contextType"));
	}
	
	

}
