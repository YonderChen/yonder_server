/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjie.legend.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 日期时间工具类 与客户端保持统一，所有时间计算只精确到秒
 * 
 * @author jackyli515
 */
public class DateTimeTools {
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMM");
	private static SimpleDateFormat sdf4 = new SimpleDateFormat("s m H d M ? yyyy");
	
	public static void setSdfTimeZone() {
		sdf1.setTimeZone(getTimeZone());
		sdf2.setTimeZone(getTimeZone());
		sdf3.setTimeZone(getTimeZone());
		sdf4.setTimeZone(getTimeZone());
	}
	
	/**
	 * 永久有效结束时间
	 */
	public static final long PERMANENT_END_DATE = 4070880000L; 
	
	/**
	 * 获取当前时间，单位：秒
	 * 
	 * @return
	 */
	public static long getNowUnixTime() {
		return System.currentTimeMillis() / 1000;
	}
	
	public static long getNowUnixTimeMilli() {
		return System.currentTimeMillis();
	}

	public static long getDay() {
		return getDay(getNowUnixTime());
	}
	
	public static long getWeek() {
		long curDay = getDay(getNowUnixTime());
		int week = (int)((curDay - 3) / 7);
		return week;
	}

	private static long getDay(long utcTimeInSeconds) {
		return (utcTimeInSeconds +  3600 * 8) / (3600 * 24);
	}
	
	public static TimeZone getTimeZone() {
		int timeZoneVal = 8;
		if (timeZoneVal > 0) {
			return TimeZone.getTimeZone("GMT+" + timeZoneVal);
		} else {
			return TimeZone.getTimeZone("GMT-" + Math.abs(timeZoneVal));
		}
	}

	public static Calendar getCalendar() {
		Calendar nowCal = Calendar.getInstance(getTimeZone());
		return nowCal;
	}

	/**
	 * 获取当天开始UNIX时间戳
	 * @return
	 */
	public static long getTodayBegin(){
		Calendar calendar = getCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis() / 1000;
	}
	
	public static long getTodayEnd(){
		Calendar calendar = getCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTimeInMillis() / 1000;
	}
	
	/**
	 * 将秒数转成yyyy-MM-dd HH:mm:ss字符串
	 * @param unixtime
	 * @return
	 */
	public static String formatUnixTimeToYMdHms(long unixtime){
		return sdf1.format(unixtime * 1000);
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss字符串转成秒数
	 * @param datetime
	 * @return
	 */
	public static long parseYMdHmsToUnixTime(String datetime) {
		if(StringUtils.isEmpty(datetime)){
			return 0;
		}
		try {
			Date date = sdf1.parse(datetime);
			return date.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm字符串转成秒数
	 * @param datetime
	 * @return
	 */
	public static long parseYMdHmToUnixTime(String datetime) {
		if(StringUtils.isEmpty(datetime)){
			return 0;
		}
		try {
			Date date = sdf2.parse(datetime);
			return date.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static String formatUnixtimeToJob(long unixtime) {
		return sdf4.format(unixtime * 1000);
	}
	
	public static String formatTimeMillisToJob(long timeMillis) {
		return sdf4.format(timeMillis);
	}
	
	public static String getCurMonthStr() {
		Calendar calendar = getCalendar();
		return sdf3.format(calendar.getTime());
	}
	
	public static String getPreMonthStr(int delta) {
		Calendar calendar = getCalendar();
		calendar.add(Calendar.MONTH, delta * -1);
		return sdf3.format(calendar.getTime());
	}
	
	public static int getHourFromUnixTime(long unixtime){
		Calendar c = getCalendar();
		c.setTimeInMillis(unixtime * 1000);
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
     * 获取当前设定的时区内，当前时间 （24小时制）
     * 
     * @return
     */
    public static int getHoursOfDay() {
        Calendar nowCal = getCalendar();
        return nowCal.get(Calendar.HOUR_OF_DAY);
    }
    public static long getPointHourTime(int hour){
        Calendar calendar = getCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }
	
    public static int getDayOfWeek() {
		Calendar calendar = getCalendar();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		dayOfWeek--;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}
}
