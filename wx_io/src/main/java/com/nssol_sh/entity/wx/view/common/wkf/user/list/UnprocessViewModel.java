package com.nssol_sh.entity.wx.view.common.wkf.user.list;

import java.util.List;

import com.nssol_sh.entity.wx.view.common.approveview.ai.AccountInfoModel;

/**
 * 未处理案件
 * 
 * @author he.jiaqi
 *
 */
public class UnprocessViewModel {
	/**
	 * 是否调用出错（true：有错误，false：无错）
	 */
	public boolean error;

	/**
	 * 错误信息，如果无错为空
	 */
	public String err_msg;

	/**
	 * 总数据条数
	 */
	public int total;

	/**
	 * 每页记录数
	 */
	public int pageRows;

	/**
	 * 起始行数
	 */
	public int offset;

	/**
	 * 本次获取数据条数
	 */
	public int count;

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
	 * @return the sf
	 */
	public String getSf() {
		return sf;
	}

	/**
	 * @return the pageRows
	 */
	public int getPageRows() {
		return pageRows;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * 未处理数据
	 */
	public List<UnprocessNodeModel> unprocessNodeList;

	/**
	 * 用户数据
	 */
	public AccountInfoModel accountInfo;

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
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @return the unprocessNodeList
	 */
	public List<UnprocessNodeModel> getUnprocessNodeList() {
		return unprocessNodeList;
	}

	/**
	 * @return the accountInfo
	 */
	public AccountInfoModel getAccountInfo() {
		return accountInfo;
	}

}
