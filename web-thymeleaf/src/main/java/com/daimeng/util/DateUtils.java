package com.daimeng.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getDateStrFormat(Date date,String formate){
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(date);
	}
	
	public static Date getDateFormat(String datestr,String formate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.parse(datestr);
	}
}
