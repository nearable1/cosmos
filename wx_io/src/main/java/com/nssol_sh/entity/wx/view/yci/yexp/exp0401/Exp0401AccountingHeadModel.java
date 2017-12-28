package com.nssol_sh.entity.wx.view.yci.yexp.exp0401;

import java.util.List;

/**
 * 出差报销申请-财务支付Header信息
 * 
 * @author S1mple
 *
 */
public class Exp0401AccountingHeadModel {

	/**
	 * 记账日.
	 */
	public String bookDate;

	/**
	 * 希望支付日.
	 */
	public String hopePayDate;

	/**
	 * 凭证抬头文本.
	 */
	public String voucherHeader;

	/**
	 * 财务支付明细.
	 */
	public List<Exp0401AccountingDetailRowModel> detailRowModel;

	/**
	 * @return the bookDate
	 */
	public String getBookDate() {
		return bookDate;
	}

	/**
	 * @return the hopePayDate
	 */
	public String getHopePayDate() {
		return hopePayDate;
	}

	/**
	 * @return the voucherHeader
	 */
	public String getVoucherHeader() {
		return voucherHeader;
	}

	/**
	 * @return the detailRowModel
	 */
	public List<Exp0401AccountingDetailRowModel> getDetailRowModel() {
		return detailRowModel;
	}

}
