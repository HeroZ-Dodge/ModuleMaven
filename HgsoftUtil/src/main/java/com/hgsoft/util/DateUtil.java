package com.hgsoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	/**
	 * 字符串转日期，字符串的格式如:"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @Title:strToDate
	 * @param dateStr
	 * @param format
	 * @return
	 * @author yudapei
	 */
	public static Date strToDate(String dateStr, String format) {
		Date date = null;
		SimpleDateFormat sdfSecond = new SimpleDateFormat(format);
		try {
			date = sdfSecond.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 时间转字符串, 如"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @Title:dateToStr
	 * @param datetime
	 * @param format
	 * @return
	 * @author yudapei
	 */
	public static String dateToStr(Date datetime, String format) {
		return new SimpleDateFormat(format, Locale.CHINA).format(datetime);
	}

	/**
	 * 日期转换到秒格式的字符串,"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @Title:dateToSec
	 * @param date
	 * @return
	 * @author yudapei
	 */
	public static String dateToSec(Date date) {
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期到秒，字符串转时间，字符串格式为"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @Title:strToDate
	 * @param dateStr
	 * @return
	 * @author yudapei
	 */
	public static Date secToDate(String dateStr) {
		Date date = null;
		SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdfSecond.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换到天格式的字符串,"yyyy-MM-dd" TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @Title:dateToDay
	 * @param date
	 * @return
	 * @author yudapei
	 */
	public static String dateToDay(Date date) {
		return dateToStr(date, "yyyy-MM-dd");
	}

	/**
	 * 日期转换到分格式的字符串,"yyyy-MM-dd HH:mm" TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @Title:dateToMin
	 * @param date
	 * @return
	 * @author yudapei
	 */
	public static String dateToMin(Date date) {
		return dateToStr(date, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 目标日期是否在离现在的n天里
	 * 
	 * @Title:isMatchDateInDays
	 * @param date
	 * @param daysDuration
	 * @return
	 * 
	 * @author yudapei
	 */
	public static boolean isMatchDateInDays(Date date, int daysDuration) {
		boolean result = true;
		Date nowDate = new Date();
		int diffDays = nowDate.getDate() - date.getDate();
		if (diffDays > daysDuration) {
			result = false;
		}
		return result;
	}

	/**
	 * 当前年（从1900年开始） TODO
	 * 
	 * @Title:getNowYear
	 * @return
	 * 
	 * @author weiliu
	 */
	public static int getNowYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 当前月 TODO
	 * 
	 * @Title:getNowMonth
	 * @return
	 * 
	 * @author weiliu
	 */
	public static int getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 当前处于月份几号 TODO
	 * 
	 * @Title:getNowDateInMonth
	 * @return
	 * 
	 * @author weiliu
	 */
	public static int getNowDateInMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取当月的 天数
	 * */
	public static int getCurrentMonthDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 * */
	public static int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取当前倒退多少天的日期 TODO
	 * 
	 * @Title:getBeforeDaysDate
	 * @param nowDate
	 * @param before
	 * @return
	 * 
	 * @author weiliu
	 */
	public static Date getBeforeDaysDate(Date nowDate, int beforeDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - beforeDays);
		return cal.getTime();
	}

}
