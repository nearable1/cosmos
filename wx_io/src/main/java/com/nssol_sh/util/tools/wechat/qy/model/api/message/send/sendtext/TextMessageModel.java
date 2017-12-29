package com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendtext;

/**
 * 文本消息体
 * 
 * @author liu.yigeng
 *
 */
public class TextMessageModel {
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
	 * 消息类型，此处固定为'text'
	 */
	public String msgtype = "text";

	/**
	 * 企业应用ID
	 */
	public int agentid;

	/**
	 * 消息内容
	 */
	public ContentModel text;

	/**
	 * 是否为保密消息
	 */
	public int safe;
}
