package com.nssol_sh.util.tools.http;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 响应对象操作类
 * 
 * @author he.jiaqi
 *
 */
public class ResponseUtil {

	/**
	 * 公共错误页面
	 */
	private static final String OTHER_ERROR_PAGE = "/wx/common/other_error";

	/**
	 * 返回404
	 * 
	 * @param response
	 *            响应对象
	 * @param c
	 *            实体类
	 * @return null
	 * @throws IOException
	 *             异常
	 */
	public static String returnErrorNotFound(HttpServletResponse response) throws IOException {
		// 返回其他错误页面
		return OTHER_ERROR_PAGE;
	}

	/**
	 * 设定Cookie的值
	 * 
	 * @param response
	 *            响应对象
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param age
	 *            存在时间
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int age) {
		// 生成cookie对象
		Cookie cookie = new Cookie(name.trim(), value.trim());

		// 设定cookie有效时间
		cookie.setMaxAge(age);

		// 设定路径（全局）
		cookie.setPath("/");

		// 向响应对象中添加Cookie
		response.addCookie(cookie);
	}
}
