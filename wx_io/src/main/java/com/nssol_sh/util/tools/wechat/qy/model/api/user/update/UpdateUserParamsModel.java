package com.nssol_sh.util.tools.wechat.qy.model.api.user.update;

import com.nssol_sh.util.tools.wechat.qy.model.api.user.UserModel;

/**
 * 更新成员信息
 * 
 * @author he.jiaqi
 *
 */
public class UpdateUserParamsModel {
	/**
	 * 令牌
	 */
	public String accesstoken;

	/**
	 * 成员信息
	 */
	public UserModel userInfo;

	/**
	 * 是否测试
	 */
	public String test;
}
