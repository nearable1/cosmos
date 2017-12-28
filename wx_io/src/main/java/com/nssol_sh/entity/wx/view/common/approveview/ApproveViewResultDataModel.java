package com.nssol_sh.entity.wx.view.common.approveview;

import java.util.Map;

import com.nssol_sh.entity.wx.view.common.approveview.ai.AccountInfoModel;
import com.nssol_sh.entity.wx.view.common.approveview.msi.MatterSystemInfoModel;

/**
 * 审批页面数据
 * 
 * @author he.jiaqi
 *
 */
public class ApproveViewResultDataModel {
	/**
	 * 案件系统信息
	 */
	public MatterSystemInfoModel matterSystemInfo;

	/**
	 * 案件用户信息
	 */
	public AccountInfoModel accountInfo;

	/**
	 * 用户数据
	 */
	public Map<String, Object> matterUserInfo;
}
