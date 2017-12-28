package com.nssol_sh.util.tools.http;

import java.lang.reflect.Field;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.nssol_sh.util.log.LogUtils;

/**
 * 请求对象操作类
 * 
 * @author he.jiaqi
 *
 */
public class RequestUtil {
	/**
	 * 将请求对象转换为实体
	 * 
	 * @param request
	 *            请求对象
	 * @param c
	 *            实体类
	 */
	public static <T> T requestParams2Bean(HttpServletRequest request, Class<T> c) {
		// 返回值
		T result = null;

		// 生成对象
		try {
			// 生成View对象
			result = (T) Class.forName(c.getName()).newInstance();
			// LogUtils.debug("生成View对象【result：" + result.toString() + "】");
			// 获得类的所有属性
			Field[] fs = result.getClass().getDeclaredFields();

			// 如果有属性
			if (fs != null && fs.length > 0) {
				// 遍历
				for (Field f : fs) {
					// 判断请求参数中是否有这个属性
					if (!StringUtils.isEmpty(request.getParameter(f.getName()))) {
						// 属性访问权限打开
						f.setAccessible(true);

						// 属性赋值
						f.set(result, request.getParameter(f.getName()));
					}
				}
			}
		} catch (Exception e) {
			LogUtils.error("将请求对象转换为实体发生异常..." + e.toString(), e);
		}

		return result;
	}

	/**
	 * 获得单个Cookie的值
	 * 
	 * @param request
	 *            请求对象
	 * @param name
	 *            名称
	 * @return value 值
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		// 返回值
		String result = "";
		// 获得请求的cookie
		Cookie[] cookies = request.getCookies();

		// 如果不为空
		if (cookies != null) {
			// 遍历
			for (Cookie cookie : cookies) {
				// 如果有键
				if (name.equals(cookie.getName())) {
					// 设定返回值
					result = cookie.getValue();
					break;
				}
			}
		}

		// 返回
		return result;
	}
}
