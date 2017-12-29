package com.nssol_sh.entity.wx.view.common.wkf.user.list;

import java.util.List;

import com.nssol_sh.entity.wx.view.common.approveview.ai.AccountInfoModel;

/**
 * 未处理案件
 * 
 * @author liu.yigeng
 *
 */
public class UnprocessNodeDataModel {
	/**
	 * 总数据条数
	 */
	public int total;

	/**
	 * 本次获取数据条数
	 */
	public int count;

	/**
	 * 未处理数据
	 */
	public List<UnprocessNodeModel> unprocessNodeList;

	/**
	 * 用户数据
	 */
	public AccountInfoModel accountInfo;

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
