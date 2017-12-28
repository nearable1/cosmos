package com.nssol_sh.util.tools.wechat.qy.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.nssol_sh.util.tools.properties.CnfPropsUtil;

/**
 * 用户令牌存储.
 */
public class UserTokenCacher {

	/**
	 * 构造函数.
	 */
	private UserTokenCacher() {

	}

	/**
	 * 常量-令牌过期时间5分钟
	 */
	private static final long KEY_TOKEN_EXPIRES_TIME = 1800000;

	/**
	 * 用户代码的键
	 */
	public static final String KEY_USER_CD = "user_cd";

	/**
	 * TenantID
	 */
	public static final String KEY_TENANT_ID = "tenant_id";

	/**
	 * 令牌过期时间的键
	 */
	private static final String KEY_EXPIRES_TIME = "expires_time";

	/**
	 * 生成单例.
	 */
	private static final UserTokenCacher single = new UserTokenCacher();

	/**
	 * 存储令牌票据对象对象.
	 */
	private Map<String, Map<String, Object>> _ttCache = new HashMap<String, Map<String, Object>>();

	/**
	 * 静态工厂方法.
	 */
	public static UserTokenCacher getInstance() {
		return single;
	}

	/**
	 * 获得AccessToken.
	 * 
	 * @param token
	 *            用户令牌
	 *
	 * @return Map 用户代码和TenantID
	 */
	public Map<String, Object> checkToken(final String token) {

		// 返回值
		Map<String, Object> result = null;

		// 是否存在
		if (this._ttCache.get(token) != null) {
			// 值
			Map<String, Object> values = this._ttCache.get(token);
			// 未过期
			if ((Long) values.get(KEY_EXPIRES_TIME) > (new Date()).getTime()) {

				// 返回数据
				result = values;
			} else {
				// 去掉过期值
				this._ttCache.remove(token);
			}
		}
		return result;
	}

	/**
	 * 设定用户的令牌 .
	 * 
	 * @param userCd
	 *            用户代码
	 * @param tenantId
	 *            TenantID
	 */
	public String setToken(final String userCd, final String tenantId) {
		// 返回值
		String result = UUID.randomUUID().toString();

		// 初始化值
		Map<String, Object> values = new HashMap<String, Object>();

		// 设定用户ID
		values.put(KEY_USER_CD, userCd);

		// 设定TenantID
		values.put(KEY_TENANT_ID, tenantId);

		// 过期时间
		long expiresTime = KEY_TOKEN_EXPIRES_TIME;

		// 获得配置文件值
		String strExpTime = CnfPropsUtil.getProperty("wx.io.cookie.user_token_expires_time");
		if (!StringUtils.isEmpty(strExpTime)) {
			// 算出毫秒数
			expiresTime = Long.valueOf(strExpTime) * 60000;
		}

		// 设定用户ID
		values.put(KEY_EXPIRES_TIME, (new Date()).getTime() + expiresTime);

		// 值设定
		this._ttCache.put(result, values);

		return result;
	}

	/**
	 * 清理过期令牌
	 */
	public void clearExpiredToken() {

		// 过期令牌键
		List<String> expiredTokenKeys = new ArrayList<String>();

		// 遍历令牌
		for (Map.Entry<String, Map<String, Object>> entry : this._ttCache.entrySet()) {
			// 取得键
			String key = entry.getKey();

			// 取得值
			Map<String, Object> values = entry.getValue();

			// 当前时间
			long currentTime = (new Date()).getTime();

			// 查看是否过期
			if (values.get(KEY_EXPIRES_TIME) == null || (Long) values.get(KEY_EXPIRES_TIME) <= currentTime) {
				expiredTokenKeys.add(key);
			}
		}

		// 移除过期
		for (String key : expiredTokenKeys) {
			this._ttCache.remove(key);
		}
	}
}
