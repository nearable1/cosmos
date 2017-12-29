package com.nssol_sh.util.tools.wechat.qy.model.api.user.create;

import java.util.List;

import com.nssol_sh.util.tools.wechat.qy.model.api.user.ExtAttrModel;

/**
 * 成员信息
 *
 * @author liu.yigeng
 *
 */
public class CreateUserModel {
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
	 * 部门内排序
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
	 * 座机
	 */
	public String telephone;

	/**
	 * 上级字段
	 */
	public int isleader;

	/**
	 * 成员头像的mediaID
	 */
	public String avatar_mediaid;

	/**
	 * 扩展属性
	 */
	public ExtAttrModel extattr;

	/**
	 * 启用/禁用成员（1表示启用成员，0表示禁用成员）
	 */
	public int enable;

}
