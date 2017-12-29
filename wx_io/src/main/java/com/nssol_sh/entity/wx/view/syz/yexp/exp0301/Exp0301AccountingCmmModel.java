package com.nssol_sh.entity.wx.view.syz.yexp.exp0301;

import java.util.List;

/**
 * 出差申请-财务入力信息
 * 
 * @author liu.yigeng
 *
 */
public class Exp0301AccountingCmmModel {

	/**
	 * 希望支付日是否显示.
	 */
	public boolean hopeDisplay;

	/**
	 * 会计科目一览
	 */
	public List<Exp0301AccountingHeadModel> accountingHeadList;

	/**
	 * @return the hopeDisplay
	 */
	public boolean getHopeDisplay() {
		return hopeDisplay;
	}

	/**
	 * @return the accountingHeadList
	 */
	public List<Exp0301AccountingHeadModel> getAccountingHeadList() {
		return accountingHeadList;
	}

}
