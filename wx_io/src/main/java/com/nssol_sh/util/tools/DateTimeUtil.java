package com.nssol_sh.util.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间操作类
 * 
 * @author he.jiaqi
 *
 */
public class DateTimeUtil {

	/**
	 * 默认日期格式
	 */
	private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

	/**
	 * 将Long型日期格式化成字符串
	 * 
	 * @param time
	 *            日期Long值
	 * @return 格式化后的日期
	 */
	public static String formatDate(long time) {
		// 日期格式
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

		// 日期对象
		Date dt = new Date(time);

		// 返回日期格式
		return sdf.format(dt);
	}

	/**
	 * 格式化字符串时间
	 * 
	 * @param time
	 *            hhMM格式时间
	 * @return 格式化后的时间
	 */
	public static String formatTime(String time) {
		// 返回值
		String result = time;

		// 如果字符串大于4个字符
		if (result.length() >= 4) {
			result = result.substring(0, 2) + ":" + result.substring(2, 4);
		}

		// 返回时间格式
		return result;
	}
}
