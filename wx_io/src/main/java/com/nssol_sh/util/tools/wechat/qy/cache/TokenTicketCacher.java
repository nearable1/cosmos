package com.nssol_sh.util.tools.wechat.qy.cache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.nssol_sh.util.tools.properties.CnfPropsUtil;

/**
 * 令牌存储.
 */
public class TokenTicketCacher {

	/**
	 * 构造函数.
	 */
	private TokenTicketCacher() {

	}

	/**
	 * 常量-令牌过期缓冲时间5分钟（毫秒）
	 */
	private static final long KEY_TOKEN_EXPIRES_BUFFER = 300000;

	/**
	 * 常量-请求次数缓冲4次
	 */
	private static final long KEY_REQUEST_TIMES_BUFFER = 5;

	/**
	 * 常量-令牌键名
	 */
	private static final String KEY_ACCESS_TOKEN = "access_token";

	/**
	 * 常量-过期时间键名
	 */
	private static final String KEY_EXPIRES_IN_TIME = "expires_in_time";

	/**
	 * 生成单例.
	 */
	private static final TokenTicketCacher single = new TokenTicketCacher();

	/**
	 * 存储令牌票据对象对象.
	 */
	private Map<String, Map<Integer, Map<String, Object>>> _ttCache = new HashMap<String, Map<Integer, Map<String, Object>>>();

	/**
	 * 存储通讯录令牌票据对象对象.
	 */
	private Map<String, Map<String, Object>> _cttCache = new HashMap<String, Map<String, Object>>();

	/**
	 * 请求当前日期
	 */
	private String _requestCurrentDate;

	/**
	 * 计数器
	 */
	private long _requestTimes = 0;

	/**
	 * 静态工厂方法.
	 */
	public static TokenTicketCacher getInstance() {
		return single;
	}

	/**
	 * 获得AccessToken.
	 * 
	 * @param corpId
	 *            企业ID
	 * @param agentId
	 *            应用程序代号
	 *
	 * @return String AccessToken
	 */
	public String getAccessToken(final String corpId, final int agentId) {
		// 获取返回值
		String result = "";
		// 获得某企业的暂存数据
		Map<Integer, Map<String, Object>> corpMap = _ttCache.get(corpId);

		// 如果企业的暂存数据不为空
		if (corpMap != null) {
			// 获得程序对应内容
			Map<String, Object> appMap = corpMap.get(agentId);

			// 到期时间
			long expiresInTime = (Long) appMap.get(KEY_EXPIRES_IN_TIME);

			// 当前时间
			long nowTime = (new Date()).getTime();

			// 如果令牌已经过期
			if ((nowTime + KEY_TOKEN_EXPIRES_BUFFER) < expiresInTime) {
				result = (String) appMap.get(KEY_ACCESS_TOKEN);
			}
		}

		return result;
	}

	/**
	 * 获得AccessToken.
	 * 
	 * @param corpId
	 *            企业ID
	 * @param corpSecret
	 *            权限组密码
	 * @param accessToken
	 *            令牌
	 */
	public void setAccessToken(final String corpId, final int agentId, final String accessToken, final long expiresIn) {
		// 获得某企业的暂存数据
		Map<Integer, Map<String, Object>> corpMap = new HashMap<Integer, Map<String, Object>>();

		// 如果该企业暂存数据不为空
		if (_ttCache.get(corpId) != null) {
			corpMap = _ttCache.get(corpId);
		}

		// 应用暂存数据
		Map<String, Object> appMap = new HashMap<String, Object>();

		// 如果应用的令牌数据为空
		if (corpMap.get(agentId) != null) {
			appMap = corpMap.get(agentId);
		}

		// 设定令牌
		appMap.put(KEY_ACCESS_TOKEN, accessToken);

		// 过期时间
		appMap.put(KEY_EXPIRES_IN_TIME, (new Date()).getTime() + (expiresIn * 1000));

		// 回传-程序对象向企业对象传
		corpMap.put(agentId, appMap);

		// 回传-企业对象往全局对象传
		_ttCache.put(corpId, corpMap);

	}

	/**
	 * 获得通讯录AccessToken.
	 * 
	 * @param corpId
	 *            企业ID
	 * @param agentId
	 *            应用程序代号
	 *
	 * @return String AccessToken
	 */
	public String getContactAccessToken(final String corpId) {
		// 获取返回值
		String result = "";

		// 获得某企业的暂存数据
		Map<String, Object> corpMap = _cttCache.get(corpId);

		// 如果企业的暂存数据不为空
		if (corpMap != null) {
			// 到期时间
			long expiresInTime = (Long) corpMap.get(KEY_EXPIRES_IN_TIME);

			// 当前时间
			long nowTime = (new Date()).getTime();

			// 如果令牌已经过期
			if ((nowTime + KEY_TOKEN_EXPIRES_BUFFER) < expiresInTime) {
				result = (String) corpMap.get(KEY_ACCESS_TOKEN);
			}
		}

		return result;
	}

	/**
	 * 获得AccessToken.
	 * 
	 * @param corpId
	 *            企业ID
	 * @param corpSecret
	 *            权限组密码
	 * @param accessToken
	 *            令牌
	 */
	public void setContactAccessToken(final String corpId, final String accessToken, final long expiresIn) {
		// 获得某企业的暂存数据
		Map<String, Object> corpMap = new HashMap<String, Object>();

		// 如果该企业暂存通讯录数据不为空
		if (_cttCache.get(corpId) != null) {
			corpMap = _cttCache.get(corpId);
		}

		// 设定令牌
		corpMap.put(KEY_ACCESS_TOKEN, accessToken);

		// 过期时间
		corpMap.put(KEY_EXPIRES_IN_TIME, (new Date()).getTime() + (expiresIn * 1000));

		// 回传-企业对象往全局对象传
		_cttCache.put(corpId, corpMap);

	}

	/**
	 * 累加令牌请求次数
	 */
	public void requestTimesCum() {
		// 当前时间格式化
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		// 当前日期
		String currentDate = df.format(new Date());
		if (currentDate.equals(_requestCurrentDate)) {
			// 令牌请求次数
			_requestTimes++;
		} else {
			_requestTimes = 1;

			// 缓存日期等于当前日期
			_requestCurrentDate = currentDate;

		}
	}

	/**
	 * 判断是否可以请求获取令牌
	 */
	public boolean requestOverflow() {

		// 获取默认每天请求数
		String strAllowRequestNum = CnfPropsUtil.getProperty("wx.io.token.allow_request_num");

		// 转换为Long
		long allowRequestNum = StringUtils.isEmpty(strAllowRequestNum) ? 0 : Long.valueOf(strAllowRequestNum);

		// 当前请求次数小于允许请求次数减去缓冲请求次数
		if (_requestTimes < (allowRequestNum - KEY_REQUEST_TIMES_BUFFER)) {
			return false;
		}

		return true;
	}
}
