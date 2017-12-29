package com.nssol_sh.util.tools.bean;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * Bean与哈希表转换类
 * 
 * @author liu.yigeng
 *
 */
public class BeanMapUtil {

	/**
	 * Bean转哈希表
	 * 
	 * @param object
	 *            Bean对象
	 * @return 哈希表
	 */
	public static Map<String, Object> bean2Map(Object object) {
		// 转换成哈希表
		return JSON.parseObject(JSON.toJSONString(object));
	}

	/**
	 * 哈希表转Bean
	 * 
	 * @param map
	 *            哈希表
	 * @param c
	 *            类
	 * @return
	 * @throws Exception
	 */
	public static Object map2Bean(Map<String, Object> map, Class<?> c) {
		return JSON.parseObject(JSON.toJSONString(map), c);
	}

}
