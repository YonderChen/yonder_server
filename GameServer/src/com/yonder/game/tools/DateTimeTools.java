package com.yonder.game.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

public class DateTimeTools {
	public static final String FORMAT_SHORT = "yyyyMMddHHmmss";
	public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DEFAULT_TIME = "0000-00-00 00:00:00";
	
	/**
	 * 永久有效开始时间
	 */
	public static final long BEGIN_DATE = 0L;
	/**
	 * 永久有效结束时间
	 */
	public static final long PERMANENT_END_DATE = 4070880000L; 
    /**
     * 获取当前时间，单位：秒
     * @return 
     */
    public static long getNowUnixTime(){    	
        return System.currentTimeMillis()/1000 ;
    }
    
	public static long getHour() {
		return System.currentTimeMillis() / (1000 * 3600);
	}

	public static long getDay() {
		return getDay(System.currentTimeMillis());
	}

	public static long getDay(long utcTimeInSeconds) {
		return (utcTimeInSeconds + 1000 * 3600 * 8) / (1000 * 3600 * 24);
	}

	/**
	 * 获取月份 
	 * @return 1月：1；12月：12
	 */
	public static int getMonth() {
		Calendar cal = getCalendar();
		return cal.get(Calendar.MONTH) + 1;
	}
	/**
	 * 获取当前年月 
	 * @return 2015年1月：201501；2015年12月：201512
	 */
	public static int getYearMonth() {
		Calendar cal = getCalendar();
		return cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1;
	}
	/**
	 * 获取当前月份前几个月的年月
	 * @param preMonth 当前月份之前的月数
	 * @return
	 */
	public static int getYearMonthAfter(int monthAfterNow) {
		Calendar cal = getCalendar();
		int monthTemp = cal.get(Calendar.MONTH) + monthAfterNow;
		int month = monthTemp % 12;
		int year = cal.get(Calendar.YEAR);
		if (month < 0) {
			month = month + 12;
		}
		if (monthTemp < 0 && month != 0) {
			year = year - 1;
		}
		year = year + monthTemp / 12;
		return year * 100 + month + 1;
	}

	public static TimeZone getTimeZone(){
    	int timeZoneVal = 8;//GameParamService.getIntValue(GameParam.Param.TimeZone);
    	if(timeZoneVal>0){
    		return TimeZone.getTimeZone("GMT+"+timeZoneVal);
    	}else{
    		return TimeZone.getTimeZone("GMT-"+Math.abs(timeZoneVal));
    	}
    }
    
    public static Calendar getCalendar(){
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

	public static long getArenaRewardTimeInMillis() {
		Calendar firstTime = getCalendar();
		firstTime.set(Calendar.HOUR_OF_DAY, 22);//GameConfigValuesService.getIntValue(GameConfigValues.Keys.ArenaRewardTime));
		firstTime.set(Calendar.MINUTE, 0);
		firstTime.set(Calendar.SECOND, 0);
		if (DateTimeTools.getCalendar().after(firstTime)) {
			// 第一次执行时间比当前时间早,则天数应该加1,否则任务会被马上执行
			firstTime.add(Calendar.DATE, 1);
		}
		return firstTime.getTimeInMillis() / 1000;
	}
	
	public static Date getDateTime(String datetime){
		long time = getUnixTime(datetime, DEFAULT_TIME);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time * 1000);
		return cal.getTime();
	}
	
	public static Long getUnixTime(String datetime,String defaultDatetime){
		if(StringUtils.isEmpty(datetime)){
			datetime = defaultDatetime;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULL);
		try {
			Date t1 = sdf.parse(datetime);
			return t1.getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException("日期转unixtime出错，date string="+datetime);
		}
	}
	
	public static String getFromUnixTime(long unixtime){
		if(unixtime < 0){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULL);
		
		return sdf.format(new Date(unixtime*1000));
	}
	
	public static long getLastDayBeginUnixTime(int day){
		Calendar calendar = getCalendar();
		calendar.add(Calendar.DATE, -day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis() / 1000;
	}
	
	public static long getLastDayEndUnixTime(int day){
		Calendar calendar = getCalendar();
		calendar.add(Calendar.DATE, -day);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTimeInMillis() / 1000;
	}

	public static int getDayOfWeek() {
		return getCalendar().get(Calendar.DAY_OF_WEEK);
	}
}
