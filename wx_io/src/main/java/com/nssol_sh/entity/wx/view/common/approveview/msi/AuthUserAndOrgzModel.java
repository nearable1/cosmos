package com.nssol_sh.entity.wx.view.common.approveview.msi;

import java.util.List;
import java.util.Map;

/**
 * 可处理用户一览实体类
 * 
 * @author liu.yigeng
 *
 */
public class AuthUserAndOrgzModel {
	/**
	 * 权限者编码
	 */
	public String authUserCode;

	/**
	 * 权限者名
	 */
	public String authUserName;

	/**
	 * 权限者部门一览
	 */
	public Map<String, String> orgzList;

	/**
	 * 印章一览
	 */
	public List<StampInfoModel> stampInfo;

	/**
	 * @return the authUserCode
	 */
	public String getAuthUserCode() {
		return authUserCode;
	}

	/**
	 * @return the authUserName
	 */
	public String getAuthUserName() {
		return authUserName;
	}

	/**
	 * @return the orgzList
	 */
	public Map<String, String> getOrgzList() {
		return orgzList;
	}

	/**
	 * @return the stampInfo
	 */
	public List<StampInfoModel> getStampInfo() {
		return stampInfo;
	}
}
