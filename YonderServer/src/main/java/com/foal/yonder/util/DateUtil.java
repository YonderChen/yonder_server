package com.foal.yonder.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date getTodayByTime(String time) {
		try {
			String date = sdf2.format(new Date());
			return sdf1.parse(date + " " + time + ":00");
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date getDateByTime(Date date, String time) {
		try {
			String date1 = sdf2.format(date);
			return sdf1.parse(date1 + " " + time + ":00");
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date getFormatFromDate(String fromDate) {
		try {
			return sdf1.parse(fromDate + " 00:00:00");
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date getFormatToDate(String toDate) {
		try {
			return sdf1.parse(toDate + " 23:59:59");
		} catch (Exception e) {
			return null;
		}
	}
	
}
