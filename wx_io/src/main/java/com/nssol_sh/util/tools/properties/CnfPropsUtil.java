package com.nssol_sh.util.tools.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.nssol_sh.util.log.LogUtils;

/**
 * 设定文件属性操作类
 *
 * @author he.jiaqi
 *
 */
public class CnfPropsUtil {
	/**
	 * 常量-配置文件路径
	 */
	private static final String CNF_FILE_PATH = "wxio_config.properties";

	/**
	 * 属性列表
	 */
	private static Properties props;

	/**
	 * 静态加载属性列表
	 */
	static {
		// 载入
		loadProps();
	}

	/**
	 * 载入Properties方法
	 */
	synchronized static private void loadProps() {
		props = new Properties();
		InputStream in = null;
		try {
			// 取得properties文件
			in = CnfPropsUtil.class.getClassLoader().getResourceAsStream(CNF_FILE_PATH);
			// 读取
			props.load(in);
		} catch (FileNotFoundException e) {
			LogUtils.error("加载properties文件发生异常..." + e.toString(), e);
		} catch (IOException e) {
			LogUtils.error("读取properties文件发生异常..." + e.toString(), e);
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				LogUtils.error("关闭properties文件发生异常..." + e.toString(), e);
			}
		}
	}

	/**
	 * 取得property方法
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static String getProperty(String key) {
		// 如果内容为空
		if (null == props) {
			// 重新读取
			loadProps();
		}

		// 返回对应值
		return props.getProperty(key);
	}

	/**
	 * 取得property方法
	 * 
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 值
	 */
	public static String getProperty(String key, String defaultValue) {
		// 如果内容为空
		if (null == props) {
			// 重新读取
			loadProps();
		}

		// 返回值
		return props.getProperty(key, defaultValue);
	}
}
