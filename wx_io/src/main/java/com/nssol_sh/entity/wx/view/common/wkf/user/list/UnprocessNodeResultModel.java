package com.nssol_sh.entity.wx.view.common.wkf.user.list;

/**
 * 未处理案件
 * 
 * @author he.jiaqi
 *
 */
public class UnprocessNodeResultModel {
	/**
	 * 是否调用出错（true：有错误，false：无错）
	 */
	public boolean error;

	/**
	 * 错误信息，如果无错为空
	 */
	public String err_msg;

	/**
	 * 数据
	 */
	public UnprocessNodeDataModel data;

	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @return the err_msg
	 */
	public String getErr_msg() {
		return err_msg;
	}

	/**
	 * @return the data
	 */
	public UnprocessNodeDataModel getData() {
		return data;
	}

}
