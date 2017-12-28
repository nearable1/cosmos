package com.nssol_sh.util.tools.wechat.qy.model.api.user.getuserinfo;

import com.nssol_sh.util.tools.wechat.qy.model.api.APIResultModel;

/**
 * 根据code获取成员信息API返回值
 *
 * @author he.jiaqi
 *
 */
public class GetUserInfoByCodeResultModel extends APIResultModel {
	/**
	 * 用户代码.
	 */
	public String UserId;

	/**
	 * 用户设备号
	 */
	public String DeviceId;

	/**
	 * 成员票据
	 */
	public String user_ticket;

	/**
	 * 成员票据有效时间（秒）
	 */
	public long expires_in;
}
