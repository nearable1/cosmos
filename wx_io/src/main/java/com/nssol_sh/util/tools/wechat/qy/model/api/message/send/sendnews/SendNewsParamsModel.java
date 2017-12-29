package com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendnews;

/**
 * 发送图文信息参数
 * 
 * @author liu.yigeng
 *
 */
public class SendNewsParamsModel {
	/**
	 * 令牌
	 */
	public String accesstoken;

	/**
	 * 消息体
	 */
	public NewsMessageModel message;

	/**
	 * 是否测试
	 */
	public String test;
}
