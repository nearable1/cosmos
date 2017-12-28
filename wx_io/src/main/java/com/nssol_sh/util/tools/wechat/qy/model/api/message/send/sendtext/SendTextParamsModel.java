package com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendtext;

/**
 * 发送文本信息参数
 * 
 * @author he.jiaqi
 *
 */
public class SendTextParamsModel {
	/**
	 * 令牌
	 */
	public String accesstoken;

	/**
	 * 消息体
	 */
	public TextMessageModel message;

	/**
	 * 是否测试
	 */
	public String test;

}
