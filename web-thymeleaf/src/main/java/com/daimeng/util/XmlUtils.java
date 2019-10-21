package com.daimeng.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import net.sf.json.xml.XMLSerializer;

public class XmlUtils {

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><Head><title><![CDATA[标题]]></title><context><![CDATA[内容]]></context><shortContext><![CDATA[简写]]></shortContext><contextType><![CDATA[01]]></contextType><createTm><![CDATA[2019-10-21 14:05:21]]></createTm></Head><Body><array><CommentInfo><id><![CDATA[11]]></id><context><![CDATA[评论11]]></context><createTm><![CDATA[2019-10-21 14:05:21]]></createTm><articleId><![CDATA[2]]></articleId></CommentInfo><CommentInfo><id><![CDATA[22]]></id><context><![CDATA[评论22]]></context><createTm><![CDATA[2019-10-21 14:05:21]]></createTm></CommentInfo></array></Body></service>";
		System.out.println(xml2Json(xml));
		Map m = xml2Map(xml);
		System.out.println(m.get("Head"));
		System.out.println(m.get("Body"));
	}
	public static String xml2Json(String xml){
		XMLSerializer xmlSerializer = new XMLSerializer();
        //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）
        String json = xmlSerializer.read(xml).toString();
        return json;
	}
	public static Map xml2Map(String xml){
		String json = xml2Json(xml);
		Map map = (Map)JSON.parse(json);
		return map;
	}
}
