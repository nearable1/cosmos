package com.nssol_sh.entity.wx.test.user;

import java.util.List;

import com.nssol_sh.entity.wx.test.common.ExtAttrModel;

public class GetUserModel {
	/**
	 * 错误码
	 */
	public int errcode;

	/**
	 * 错误信息
	 */
	public String errmsg;

	/**
	 * 成员UserID.
	 */
	public String userid;

	/**
	 * 成员名称
	 */
	public String name;

	/**
	 * 英文名
	 */
	public String english_name;

	/**
	 * 手机号码
	 */
	public String mobile;

	/**
	 * 成员所属部门id列表
	 */
	public List<Integer> department;

	/**
	 * 部门内的排序值
	 */
	public List<Integer> order;

	/**
	 * 职位信息
	 */
	public String position;

	/**
	 * 性别（0表示未定义，1表示男性，2表示女性）
	 */
	public String gender;

	/**
	 * 邮箱
	 */
	public String email;

	/**
	 * 上级字段
	 */
	public int isleader;

	/**
	 * 成员头像的mediaID
	 */
	public String avatar_mediaid;

	/**
	 * 头像URL
	 */
	public String avatar;

	/**
	 * 座机
	 */
	public String telephone;

	/**
	 * 扩展属性
	 */
	public ExtAttrModel extattr;

	/**
	 * 启用/禁用成员（1表示启用成员，0表示禁用成员）
	 */
	public int enable;

	/**
	 * 激活状态 1=已激活，2=已禁用，4=未激活 已激活代表已激活企业微信或已关注微信插件。 未激活代表既未激活企业微信又未关注微信插件
	 */
	public int status;

	/**
	 * 关注微信插件的状态: 1=已关注，0=未关注
	 */
	public int wxplugin_status;

}
