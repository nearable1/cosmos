package com.nssol_sh.entity.wx.view.common.approveview.ai;

/**
 * 用户信息
 * 
 * @author he.jiaqi
 *
 */
public class AccountInfoModel {

	/**
	 * TenantID
	 */
	public String tenantId;

	/**
	 * 当前用户编码
	 */
	public String userCd;

	/**
	 * 区域编码
	 */
	public String localId;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @return the userCd
	 */
	public String getUserCd() {
		return userCd;
	}

	/**
	 * @return the localId
	 */
	public String getLocalId() {
		return localId;
	}
}