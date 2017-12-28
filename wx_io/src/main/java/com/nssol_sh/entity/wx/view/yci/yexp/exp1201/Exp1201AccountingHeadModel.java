package com.nssol_sh.entity.wx.view.yci.yexp.exp1201;

import java.util.List;

public class Exp1201AccountingHeadModel {

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
	public List<Exp1201AccountingDetailRowModel> detailRowModel;

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
	public List<Exp1201AccountingDetailRowModel> getDetailRowModel() {
		return detailRowModel;
	}

	/**
	 * @return the bookDate
	 */
	public String getBookDate() {
		return bookDate;
	}
}
