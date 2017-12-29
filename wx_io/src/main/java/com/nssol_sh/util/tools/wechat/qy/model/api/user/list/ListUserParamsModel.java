package com.nssol_sh.util.tools.wechat.qy.model.api.user.list;

/**
 * 读取成员列表
 * 
 * @author liu.yigeng
 *
 */
public class ListUserParamsModel {
	/**
	 * 令牌
	 */
	public String accesstoken;

	/**
	 * 部门ID
	 */
	public String department_id;

	/**
	 * 是否递归子部门
	 */
	public String fetch_child;

	/**
	 * 是否测试
	 */
	public String test;
}
