package com.daimeng.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import net.sf.json.xml.XMLSerializer;

public class XmlUtils {

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
