package com.daimeng.test.xml2map;

import java.util.Date;
import java.util.Map;

import net.sf.json.xml.XMLSerializer;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;

public class Xml2Map {

	public static void main(String[] args) throws DocumentException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><Head><title><![CDATA[标题]]></title><context><![CDATA[内容]]></context><shortContext><![CDATA[简写]]></shortContext><contextType><![CDATA[01]]></contextType><createTm><![CDATA[2019-10-21 14:05:21]]></createTm></Head><Body><array><CommentInfo><id><![CDATA[11]]></id><context><![CDATA[评论11]]></context><createTm><![CDATA[2019-10-21 14:05:21]]></createTm><articleId><![CDATA[2]]></articleId></CommentInfo><CommentInfo><id><![CDATA[22]]></id><context><![CDATA[评论22]]></context><createTm><![CDATA[2019-10-21 14:05:21]]></createTm></CommentInfo></array></Body></service>";
		//创建 XMLSerializer对象
        XMLSerializer xmlSerializer = new XMLSerializer();
        //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）
        String result = xmlSerializer.read(xml).toString();
        //输出json内容
        System.out.println(result);
        Map maps = (Map)JSON.parse(result);  
        System.out.println(((Map)maps.get("Head")).get("contextType"));
        
        Date date = new Date(System.currentTimeMillis());
        System.out.println(JSON.toJSON(date));
        java.sql.Date d2 = new java.sql.Date(System.currentTimeMillis());
        System.out.println(JSON.toJSON(d2));
        String datestr = "Fri Nov 08 18:04:07 GMT+08:00 2019";
	}
	
	

}
