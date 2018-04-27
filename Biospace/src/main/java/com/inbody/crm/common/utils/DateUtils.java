/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.SSS", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		if (date == null) {
			return null;
		}
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将字符串转换成指定格式的sql日期类型
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static java.sql.Date parseToSqlDate(String dateStr, String formatStr) {
		Date javaDate = parse(dateStr, formatStr);
		if (null == javaDate) {
			return null;
		}
		java.sql.Date date = new java.sql.Date(javaDate.getTime());
		return date;
	}
	
	/**
	 * 将字符串转换成指定格式的日期类型
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date parse(String dateStr, String formatStr) {
		DateFormat df = new SimpleDateFormat(formatStr);
		try {
			return df.parse(dateStr);
		} catch (Exception e) {
			//return null;
		}
		return null;
	}
	
	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 将非SQL日期转换为SQL日期
	 * @param utilDate
	 * @return
	 */
	public static java.sql.Date convertToSqlDate(java.util.Date utilDate){
		return new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * 将SQL日期转换为非SQL日期
	 * @param sqlDate
	 * @return
	 */
	public static java.util.Date convertToUtilDate(java.sql.Date sqlDate){
		return new java.util.Date(sqlDate.getTime());
	}
	
	/**
	 * 将两个日期(String)进行比较
	 * @param end
	 * @param begin
	 * @return
	 */
	public static int compareDate(String date1, String date2, String formatStr) {
		 DateFormat df = new SimpleDateFormat(formatStr);
		 
		 if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) {
			 return 0;
		 }
	        try {
	            Date dt1 = df.parse(date1);
	            Date dt2 = df.parse(date2);
	            if (dt1.getTime() > dt2.getTime()) {

	                return 1;
	            } else if (dt1.getTime() < dt2.getTime()) {

	                return -1;
	            } else {
	                return 0;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return 0;
	}

	/**
	 * 将两个日期(Date)进行比较
	 * 
	 * @param date1
	 *            比较日期1
	 * @param date2
	 *            比较日期2
	 * 
	 * @return 1: date1 大于 date2<br>
	 *         -1: date1 小于 date2<br>
	 *         0: date1 等于 date2
	 */
	public static int compareDate(Date date1, Date date2) {

		if (date1 == null) {
			date1 = MIN_TERM_DATE;
		}

		if (date2 == null) {
			date2 = MIN_TERM_DATE;
		}

		if (date1.getTime() > date2.getTime()) {
			return 1;
		} else if (date1.getTime() < date2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public static int isOneYear(String start, String end) {
		if (start == null || end == null) {
			 return -1;
		}
		
		Date startD = parse(start, "yyyy-MM-dd");
		Date endD = parse(end, "yyyy-MM-dd");
		
		return isOneYear(startD, endD);
		
	}

	public static int isOneYear(Date start, Date end) {
		if (start == null || end == null) {
			 return -1;
		}
		
        Calendar startday = Calendar.getInstance();
        Calendar endday = Calendar.getInstance();
        startday.setTime(start);
        endday.setTime(end);
        if (startday.after(endday)) {
            return -2;
        }
        endday.add(Calendar.DAY_OF_MONTH, 1);
        long sl = startday.getTimeInMillis();
        long el = endday.getTimeInMillis();
        long days = ((el - sl) / (1000 * 60 * 60 * 24));
        if (days == 365 || days == 366) {
            if (startday.get(Calendar.MONTH) <= 1) {
                startday.set(Calendar.MONTH, 1);
                int lastDay = startday.getActualMaximum(Calendar.DAY_OF_MONTH);
                if ((lastDay == 28 && days == 365) || (lastDay == 29 && days == 366)) {
                	return 0;
                } else if (lastDay == 28 && days == 366) {
                	return 1;
                } else if (lastDay == 29 && days == 365) {
                	return -1;
                }
            } else {
                endday.set(Calendar.MONTH, 1);
                int lastDay = endday.getActualMaximum(Calendar.DAY_OF_MONTH);
                if ((lastDay == 28 && days == 365) || (lastDay == 29 && days == 366)) {
                	return 0;
                } else if (lastDay == 28 && days == 366) {
                	return 1;
                } else if (lastDay == 29 && days == 365) {
                	return -1;
                }
            }
        } else if (days > 366) {
            return 1;
        } else if (days < 365) {
            return -1;
        }
        return -1;
    }
	
	public static Date MIN_TERM_DATE = parseDate("1900-01-01 00:00:00");
	public static Date MAX_TERM_DATE = parseDate("2099-12-31 23:59:59");
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
//		Date start = parse("2017-10-26", "yyyy-MM-dd");
//		Date end1 = parse("2018-10-24", "yyyy-MM-dd");
//		Date end2 = parse("2018-10-25", "yyyy-MM-dd");
//		Date end3 = parse("2018-10-26", "yyyy-MM-dd");
//		System.out.println(isOneYear(start, end1));
//		System.out.println(isOneYear(start, end2));
//		System.out.println(isOneYear(start, end3));
	}
	
	/**
     * 返回指定年月的月的第一天
    *
    * @param year
    * @param month
    * @return
    */
   public static Date getFirstDayOfMonth(Integer year, Integer month) {
       Calendar calendar = Calendar.getInstance();
       if (year == null) {
           year = calendar.get(Calendar.YEAR);
       }
       if (month == null) {
           month = calendar.get(Calendar.MONTH);
       }
       calendar.set(year, month-1, 1);
       return calendar.getTime();
   }

   /**
    * 返回指定年月的月的最后一天
    *
    * @param year
    * @param month
    * @return
    */
   public static Date getLastDayOfMonth(Integer year, Integer month) {
       Calendar calendar = Calendar.getInstance();
       if (year == null) {
           year = calendar.get(Calendar.YEAR);
       }
       if (month == null) {
           month = calendar.get(Calendar.MONTH);
       }
       calendar.set(year, month-1, 1);
       calendar.roll(Calendar.DATE, -1);
       return calendar.getTime();
   }

}
