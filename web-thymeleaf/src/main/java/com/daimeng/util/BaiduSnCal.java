package com.daimeng.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BaiduSnCal {
	
	public static void main(String[] args) throws UnsupportedEncodingException,
			NoSuchAlgorithmException {
		String ak = "tlrgpbkZkwnZnNPeBc77wNVwplSDKxYq";
		String sk = "TgNe6C1Wqs0el6kZbhAeu1Bz09w9q4Im";
		getSnForIp(ak, sk, "127.0.0.1");
		// 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。

		/*Map paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("address", "百度大厦");
		paramsMap.put("output", "json");
		paramsMap.put("ak", ak);

		// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
		String paramsStr = BaiduSnCal.toQueryString(paramsMap);

		// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
		String wholeStr = new String("/geocoder/v2/?" + paramsStr + "yoursk");

		// 对上面wholeStr再作utf8编码
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

		// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
		System.out.println(BaiduSnCal.MD5(tempStr));*/
	}
	
	public static String getSnForIp(String ak,String sk, String ip) throws UnsupportedEncodingException,
	NoSuchAlgorithmException{
		Map paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("ak", ak);
		paramsMap.put("ip", ip);
		//paramsMap.put("output", "json");

		// 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
		String paramsStr = BaiduSnCal.toQueryString(paramsMap);

		// 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
		String wholeStr = new String("/location/ip?" + paramsStr + sk);
		System.out.println("wholeStr="+wholeStr);
		// 对上面wholeStr再作utf8编码
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		System.out.println("tempStr="+tempStr);
		// 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
		String sn = BaiduSnCal.MD5(tempStr);
		System.out.println("sn="+sn);
		
		return sn;
	}

	// 对Map内所有value作utf8编码，拼接返回结果
	public static String toQueryString(Map<?, ?> data)
			throws UnsupportedEncodingException {
		StringBuffer queryString = new StringBuffer();
		for (Entry<?, ?> pair : data.entrySet()) {
			queryString.append(pair.getKey() + "=");
			queryString.append(URLEncoder.encode((String) pair.getValue(),
					"UTF-8") + "&");
		}
		if (queryString.length() > 0) {
			queryString.deleteCharAt(queryString.length() - 1);
		}
		return queryString.toString();
	}

	// 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}
}
