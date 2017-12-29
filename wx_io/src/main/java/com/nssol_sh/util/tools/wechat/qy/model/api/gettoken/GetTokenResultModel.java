package com.nssol_sh.util.tools.wechat.qy.model.api.gettoken;

import com.nssol_sh.util.tools.wechat.qy.model.api.APIResultModel;

/**
 * 令牌获取API返回值
 *
 * @author liu.yigeng
 *
 */
public class GetTokenResultModel extends APIResultModel {
	/**
	 * 令牌.
	 */
	public String access_token;

	/**
	 * 凭证有效时间
	 */
	public long expires_in;
}
