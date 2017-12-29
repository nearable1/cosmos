package com.nssol_sh.util.tools.wechat.qy.model.api.user.getuserinfo;

/**
 * 根据code获取成员信息参数
 * 
 * @author liu.yigeng
 *
 */
public class GetUserInfoByCodeParamsModel {
	/**
	 * 令牌
	 */
	public String accesstoken;

	/**
	 * 通过成员授权获取到的code
	 */
	public String code;

}
