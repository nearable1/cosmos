package com.nssol_sh.entity.wx.view.common.approveview;

import org.springframework.util.StringUtils;

/**
 * 请求参数实体类
 * 
 * @author he.jiaqi
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
	 * 系统案件号
	 */
	public String smid;

	/**
	 * 用户数据号
	 */
	public String udid;

	/**
	 * 节点号
	 */
	public String nodeid;

	/**
	 * 服务器类型
	 */
	public String sf;

	/**
	 * nc_token
	 */
	public String nc_token;

	/**
	 * 微信跳转状态信息
	 */
	public String state;

	/**
	 * 微信跳转代码
	 */
	public String code;

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

		// 系统案件号
		if (StringUtils.isEmpty(this.smid)) {
			return false;
		}

		// 用户数据号
		if (StringUtils.isEmpty(this.udid)) {
			return false;
		}

		// 节点号
		if (StringUtils.isEmpty(this.nodeid)) {
			return false;
		}

		return true;
	}

}
