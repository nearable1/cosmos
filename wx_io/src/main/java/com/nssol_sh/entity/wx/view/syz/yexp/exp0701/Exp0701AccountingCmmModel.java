/**
 * 
 */
package com.nssol_sh.entity.wx.view.syz.yexp.exp0701;

import java.util.List;

/**
 * 财务支付一览
 * 
 * @author S1mple
 *
 */
public class Exp0701AccountingCmmModel {

	/**
	 * 希望支付日 是否显示
	 */
	public boolean hopeDisplay;

	/**
	 * 会计科目一览
	 */
	public List<Exp0701AccountingHeadModel> accountingHeadList;

	/**
	 * @return the hopeDisplay
	 */
	public boolean isHopeDisplay() {
		return hopeDisplay;
	}

	/**
	 * @return the accountingHeadList
	 */
	public List<Exp0701AccountingHeadModel> getAccountingHeadList() {
		return accountingHeadList;
	}

}
