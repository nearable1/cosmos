package com.nssol_sh.model.message;

/**
 * 文本消息体
 * 
 * @author he.jiaqi
 *
 */
public class NewsMessageModel {
	/**
	 * 成员ID列表
	 */
	public String touser;

	/**
	 * 部门ID列表
	 */
	public String toparty;

	/**
	 * 标签ID列表
	 */
	public String totag;

	/**
	 * 消息类型，此处固定为'news'
	 */
	public String msgtype = "news";

	/**
	 * 企业应用ID
	 */
	public int agentid;

	/**
	 * 消息内容
	 */
	public NewsModel news;

	/**
	 * 是否为保密消息
	 */
	public int safe;
}
