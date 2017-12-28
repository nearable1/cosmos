package com.nssol_sh.util.tools.wechat.qy.model.api.user.create;

/**
 * 创建成员参数
 * 
 * @author he.jiaqi
 *
 */
public class CreateUserParamsModel {
	/**
	 * 令牌
	 */
	public String accesstoken;

	/**
	 * 成员信息
	 */
	public CreateUserModel userInfo;

	/**
	 * 是否测试
	 */
	public String test;
}
