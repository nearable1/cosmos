/**
 * 
 */
package com.nssol_sh.util.log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 * Logger封装共通
 * 
 * @author S1mple
 *
 */
public class LogUtils {
	/**
	 * 日志开关
	 */
	@Value("${wx.io.ychips.logOutput}")
	private static String levelFlag;

	private static Map<String, Object> loggerMap = new HashMap<String, Object>();
	/* 日志对象自获取 */
	private static final Logger loggerSelf = Logger.getLogger(LogUtils.class);

	/**
	 * Debug
	 */
	public static void debug(String message) {
		String className = getClassName();
		Logger log = getLogger(className);
		if (log.isDebugEnabled()) {
			log.debug("[Debug]" + message);
		}
	}

	/**
	 * Error
	 * 
	 * @param Object
	 *            message
	 * @param Throwable
	 *            e
	 */
	public static void error(Object message, Exception e) {
		String className = getClassName();
		Logger log = getLogger(className);
		log.error("[Error]" + message + e.toString(), e);
	}

	/**
	 * Info
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static void info(Object message) {
		String className = getClassName();
		Logger log = getLogger(className);
		if (log.isInfoEnabled()) {
			log.info("[INFO]" + message);
		}
	}

	private static String getClassName() {
		Throwable th = new Throwable();
		StackTraceElement[] stes = th.getStackTrace();
		StackTraceElement ste = stes[2];
		loggerMap.put("className", ste.getClassName());
		return ste.getClassName();
	}

	private static Logger getLogger(String className) {
		Logger log = null;
		if (loggerMap.containsKey(className)) {
			// 获取日志对象
			log = Logger.getLogger(className);
		} else {
			try {
				log = Logger.getLogger(Class.forName(className));
				loggerMap.put(className, log);
			} catch (ClassNotFoundException e) {
				loggerSelf.error("Logger对象获取失败...【对象名：" + className + "】", e);
				e.printStackTrace();
			}
		}
		return log;
	}
}
