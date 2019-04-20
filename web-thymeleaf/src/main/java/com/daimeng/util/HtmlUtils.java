package com.daimeng.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
	/**
	 * 
	* @功能描述: 替换回车
	* @方法名称: replaceEnter 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月20日 下午2:49:58 
	* @version V1.0   
	* @param myString
	* @return 
	* @return String
	 */
	public static String replaceEnter(String myString){  
		if(myString != null && !"".equals(myString)){
			String newString=myString;  
	        Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");  
	        Matcher m = CRLF.matcher(myString);  
	        if (m.find()) {  
	          newString = m.replaceAll("<br>");  
	        }  
	        return newString;  
		}else return myString;
        
    }  
	/**
	 * 
	* @功能描述: 金额字符串千分位分割
	* @方法名称: fmtMicrometer 
	* @路径 com.daimeng.util 
	* @作者 daimeng@tansun.com.cn
	* @创建时间 2019年4月20日 下午2:50:13 
	* @version V1.0   
	* @param text
	* @return 
	* @return String
	 */
	public static String fmtMicrometer(String text) {
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}
	
}
