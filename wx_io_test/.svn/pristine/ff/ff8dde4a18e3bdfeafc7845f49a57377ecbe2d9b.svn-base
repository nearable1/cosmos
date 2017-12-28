package com.nssol_sh.cache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户令牌存储.
 */
public class RequestCacher {

	/**
	 * 构造函数.
	 */
	private RequestCacher() {

	}

	/**
	 * 生成单例.
	 */
	private static final RequestCacher single = new RequestCacher();

	/**
	 * 存储接收到到的对象.
	 */
	private List<Map<String, Object>> _messageCache = new ArrayList<Map<String, Object>>();

	/**
	 * 静态工厂方法.
	 */
	public static RequestCacher getInstance() {
		return single;
	}

	/**
	 * 设定消息 .
	 * 
	 * @param msg
	 *            消息对象
	 */
	public void setMessage(final Map<String, Object> msg) {
		if (msg != null) {
			// 时间格式化
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// 接收时间
			msg.put("receive_time", formatter.format(new Date()));

			this._messageCache.add(msg);
		}
	}

	/**
	 * 获取所有消息 .
	 * @return 所有消息.
	 */
	public List<Map<String, Object>> getMessages() {
		return this._messageCache;
	}
}
