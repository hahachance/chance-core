package com.qz.core.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author chance
 * @date 2017年10月24日上午9:42:05
 */
public class DateUtil {

	/**
	 * 时间正则表达式yyyy-MM-dd
	 */
	private final static Pattern DATE_PATTERN = Pattern.compile("\\d{4}\\-((0[1-9])|(1[0-2]))\\-((0[1-9])|([1-2]\\d)|(3[0-1]))");
	/**
	 * 时间正则表达式yyyy-MM-dd HH：mm:ss
	 */
	private final static Pattern FULL_DATE_PATTERN = Pattern.compile("\\d{4}\\-((0[1-9])|(1[0-2]))\\-((0[1-9])|([1-2]\\d)|(3[0-1]))\\s([0-1]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)");

	public final static String DAY_STYLE = "yyyy-MM-dd";
	public final static String DAY_TIME_STYLE = "yyyy-MM-dd HH:mm:ss";

	private final static long SECONDS_NUM = 1000;
	private final static long MINUTES_NUM = 1000 * 60;
	private final static long HOURS_NUM = 1000 * 60 * 60;
	private final static long DAYS_NUM = 1000 * 60 * 60 * 24;


	private final static int UN_DEFINED = -1;
	private final static int DEFAULT = 0;
	public final static int SECONDS = 1;
	public final static int MINUTES = 2;
	public final static int HOURS = 3;
	public final static int DAYS = 4;



	/**
	 * 判断是否是时间格式字符串
	 * @param strDate 日期形式的字符串，yyyy-MM-dd格式
	 * @return
	 */
	public static boolean isDate(String strDate){
		return null == strDate || "".equals(strDate) ? false : DATE_PATTERN.matcher(strDate).matches();
	}
	
	/**
	 * 判断是否是时间格式字符串
	 * @param strDate 日期形式的字符串，yyyy-MM-dd HH：mm:ss格式
	 * @return
	 */
	public static boolean isFullDate(String strDate){
		return null == strDate || "".equals(strDate) ? false : FULL_DATE_PATTERN.matcher(strDate).matches();
	}
	
	/**
	 * 日期格式化
	 * @param date 日期
	 * @return 返回yyyy-MM-dd格式日期
	 */
	public static String formatDate(Date date){
		return null == date ? "" : format(date, DAY_STYLE);
	}
	
	/**
	 * 日期格式化
	 * @param date 日期
	 * @param format 日期格式
	 * @return 返回格式化日期字符串
	 */
	public static String format(Date date, String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 日期格式化
	 * @param date 日期
	 * @return 返回yyyy-MM-dd HH:mm:ss格式日期
	 */
	public static String formatFullDate(Date date){
		return null == date ? "" : format(date, DAY_TIME_STYLE);
	}
	
	/**
	 * 日期解析
	 * @param date 日期
	 * @param format 日期格式
	 * @return 
	 */
	public static Date parse(String date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 用于两个时间的比较
	 * @param d1
	 * @param d2
	 * @return -1, 0, or 1 as this Date is numerically less than, equal to, or greater than val.
	 */
	public static int compareTo(Date d1, Date d2){
		return d1.compareTo(d2);
	}
	
	public static boolean isAfter(Date d1, Date d2){
		return d1.compareTo(d2) > 0;
	}
	
	public static boolean isBefore(Date d1, Date d2){
		return d1.compareTo(d2) < 0;
	}

	public static Date addMinute(Date d, int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MINUTE, amount);
		return new Date(c.getTimeInMillis());
	}

	public static Date add(Date d, int amount, int field){
		int calendar_field = SECONDS == field ? Calendar.SECOND :
					MINUTES == field ? Calendar.MINUTE :
						HOURS == field ? Calendar.HOUR :
							DAYS == field ? Calendar.DATE : Calendar.ERA;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(calendar_field, amount);
		return new Date(c.getTimeInMillis());
	}

	private static int diff(Date d1, Date d2, long amount){
		return (int) ((d1.getTime() - d2.getTime()) / amount);
	}

	public static int diff(Date d1, Date d2, int field){
		long s = SECONDS == field ? SECONDS_NUM :
					MINUTES == field ? MINUTES_NUM :
						HOURS == field ? HOURS_NUM :
							DAYS == field ? DAYS_NUM : 1;
		return diff(d1, d2, s);
	}

	public static Date sub(Date d1, int amount, int field){
		long s = SECONDS == field ? SECONDS_NUM :
				MINUTES == field ? MINUTES_NUM :
						HOURS == field ? HOURS_NUM :
								DAYS == field ? DAYS_NUM : 1;
		return new Date(d1.getTime() - s * amount);
	}

	public static void main(String[] args) {
		Date d = DateUtil.parse("2017-12-28 21:00:01", "yyyy-MM-dd HH:mm:ss");
		d = addMinute(d, 100);
		System.out.println(DateUtil.formatFullDate(d));
	}
}
