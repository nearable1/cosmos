package com.inbody.crm.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtils {

	/**
	 * 取得list的size
	 */
	public static <T> int size(List<T> list) {
		
		if (list == null) {
			return 0;
		} else {
			return list.size();
		}
	}
	
	/**
	 * 将对象List转换为map
	 * 
	 * @param list
	 *            对象List
	 * @param keyProperty
	 *            作为map key值的对象属性名,必须为String类型
	 * @return map对象
	 */
	public static <T> Map<String, T> convertListToMap(List<T> list,
			String keyProperty) {

		Map<String, T> map = new HashMap<String, T>();
		if (list == null || list.size() == 0
				|| StringUtils.isEmptyNull(keyProperty)) {
			return map;
		}

		try {
			// 将属性的首字符大写，方便构造get方法
			keyProperty = keyProperty.substring(0, 1).toUpperCase()
					+ keyProperty.substring(1);
			for (T item : list) {
				Method m = item.getClass().getMethod("get" + keyProperty);
				// 调用getter方法获取属性值
				String keyvalue = (String) m.invoke(item);
				if (StringUtils.isEmptyNull(keyvalue)) {
					continue;
				}
				map.put(keyvalue, item);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return map;
	}
}
