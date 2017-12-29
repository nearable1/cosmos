package com.nssol_sh.entity.wx.view.syz.yexp.exp0501;

import java.util.List;

/**
 * 会计科目表头信息
 * 
 * @author liu.yigeng
 *
 */
public class Exp0501AccountingHeadModel {

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
	public List<Exp0501AccountingDetailRowModel> detailRowModel;

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
	public List<Exp0501AccountingDetailRowModel> getDetailRowModel() {
		return detailRowModel;
	}

}
