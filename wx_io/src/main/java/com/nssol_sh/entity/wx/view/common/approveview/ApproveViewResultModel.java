package com.nssol_sh.entity.wx.view.common.approveview;

/**
 * 审批页面数据获取返回值
 *
 * @author liu.yigeng
 *
 */
public class ApproveViewResultModel {
	/**
	 * 是否调用出错（true：有错误，false：无错）
	 */
	public boolean error;

	/**
	 * 错误信息，如果无错为空
	 */
	public String error_msg;

	/**
	 * 审批页面数据
	 */
	public ApproveViewResultDataModel data;
}
