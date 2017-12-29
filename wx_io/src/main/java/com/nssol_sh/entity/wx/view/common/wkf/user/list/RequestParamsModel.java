package com.nssol_sh.entity.wx.view.common.wkf.user.list;

import org.springframework.util.StringUtils;

/**
 * 请求参数实体类
 * 
 * @author liu.yigeng
 *
 */
public class RequestParamsModel {
	/**
	 * TenantID
	 */
	public String tenantid;

	/**
	 * 用户编码
	 */
	public String usercd;

	/**
	 * 微信跳转状态信息
	 */
	public String state;

	/**
	 * 微信跳转代码
	 */
	public String code;

	/**
	 * 起始行
	 */
	public String offset;

	/**
	 * 取得行
	 */
	public String count;

	/**
	 * 服务器类型
	 */
	public String sf;

	/**
	 * nc_token
	 */
	public String nc_token;

	/**
	 * @return the nc_token
	 */
	public String getNc_token() {
		return nc_token;
	}

	/**
	 * @return the tenantid
	 */
	public String getTenantid() {
		return tenantid;
	}

	/**
	 * @return the usercd
	 */
	public String getUsercd() {
		return usercd;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the offset
	 */
	public String getOffset() {
		return offset;
	}

	/**
	 * @return the sf
	 */
	public String getSf() {
		return sf;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * 检查必须
	 */
	public boolean checkRequired() {
		// 用户名
		if (StringUtils.isEmpty(this.usercd)) {
			return false;
		}

		// TenantID
		if (StringUtils.isEmpty(this.tenantid)) {
			return false;
		}

		return true;
	}

}
