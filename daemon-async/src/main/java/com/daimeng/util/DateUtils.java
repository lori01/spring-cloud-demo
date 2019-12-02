package com.daimeng.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	/*
	 * yyyy : 代表年(不去区分大小写) 假设年份为 2017
	 *     "y" , "yyy" , "yyyy" 匹配的都是4位完整的年 如 : "2017"
	 *     "yy" 匹配的是年分的后两位 如 : "15"
	 *     超过4位,会在年份前面加"0"补位 如 "YYYYY"对应"02017"
	 * 
	 * MM : 代表月(只能使用大写) 假设月份为 9
	 *     "M" 对应 "9"
	 *     "MM" 对应 "09"
	 *     "MMM" 对应 "Sep"
	 *     "MMMM" 对应 "Sep"
	 *     超出3位,仍然对应 "September"
	 * 
	 * dd : 代表日(只能使用小写) 假设为13号
	 *     "d" , "dd" 都对应 "13"
	 *     超出2位,会在数字前面加"0"补位. 例如 "dddd" 对应 "0013"
	 * 
	 * hh : 代表时(区分大小写,大写为24进制计时,小写为12进制计时) 假设为15时
	 *     "H" , "HH" 都对应 "15" , 超出2位,会在数字前面加"0"补位. 例如 "HHHH" 对应 "0015"
	 *     "h" 对应 "3"
	 *     "hh" 对应 "03" , 超出2位,会在数字前面加"0"补位. 例如 "hhhh" 对应 "0003"
	 * 
	 * mm : 代表分(只能使用小写) 假设为32分
	 *     "m" , "mm" 都对应 "32" ,  超出2位,会在数字前面加"0"补位. 例如 "mmmm" 对应 "0032"
	 * 
	 * ss : 代表秒(只能使用小写) 假设为15秒
	 *     "s" , "ss" 都对应 "15" , 超出2位,会在数字前面加"0"补位. 例如 "ssss" 对应 "0015"
	 * 
	 * E : 代表星期(只能使用大写) 假设为 Sunday
	 *     "E" , "EE" , "EEE" 都对应 "Sun"
	 *     "EEEE" 对应 "Sunday" , 超出4位 , 仍然对应 "Sunday"
	 * 
	 * a : 代表上午还是下午,如果是上午就对应 "AM" , 如果是下午就对应 "PM"
	 */
	

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String DDMMYYYY = "dd/MM/yyyy";
	public static final String YYYY_MM_DDHH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DDHH_MM_SSS = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String HH_MM_SS = "HH:mm:ss";

	public static void main(String[] args) {
		Date date = new Date();
		Constants.println(getDateStrFormat(date, YYYY_MM_DD));
		Constants.println(getDateStrFormat(date, YYYYMMDD));
		Constants.println(getDateStrFormat(date, DDMMYYYY));
		Constants.println(getDateStrFormat(date, YYYY_MM_DDHH_MM_SS));
		Constants.println(getDateStrFormat(date, YYYY_MM_DDHH_MM_SSS));
		Constants.println(getDateStrFormat(date, HH_MM_SS));
	}
	
	public static String getDateStrFormat(Date date,String formate){
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(date);
	}
	
	public static Date getDateFormat(String datestr,String formate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.parse(datestr);
	}
	
	
}
