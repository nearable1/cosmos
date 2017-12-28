package com.nssol_sh.entity.wx.view.yci.yexp.exp0501;

import java.util.List;

/**
 * 其他经费报销申请-财务入力信息
 * 
 * @author long.liming
 *
 */
public class Exp0501AccountingCmmModel {

	/**
	 * 希望支付日是否显示.
	 */
	public boolean hopeDisplay;

	/**
	 * 会计科目一览
	 */
	public List<Exp0501AccountingHeadModel> accountingHeadList;

	/**
	 * 币种(元).
	 */
	public String currencyName;

	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * 含税金额合计(公司).
	 */
	public String totalPaymentAmountTaxExC;
	/**
	 * 含税金额合计(个人).
	 */
	public String totalPaymentAmountTaxExP;
	/**
	 * 费用金额合计(公司)
	 */
	public String totalPaymentAmountTaxInC;
	/**
	 * 费用金额合计(个人)
	 */
	public String totalPaymentAmountTaxInP;
	/**
	 * 税额合计(公司).
	 */
	public String totalTaxAmountC;
	/**
	 * 税额合计(个人).
	 */
	public String totalTaxAmountP;

	/**
	 * @return the totalPaymentAmountTaxExC
	 */
	public String getTotalPaymentAmountTaxExC() {
		return totalPaymentAmountTaxExC;
	}

	/**
	 * @return the totalPaymentAmountTaxExP
	 */
	public String getTotalPaymentAmountTaxExP() {
		return totalPaymentAmountTaxExP;
	}

	/**
	 * @return the totalPaymentAmountTaxInC
	 */
	public String getTotalPaymentAmountTaxInC() {
		return totalPaymentAmountTaxInC;
	}

	/**
	 * @return the totalPaymentAmountTaxInP
	 */
	public String getTotalPaymentAmountTaxInP() {
		return totalPaymentAmountTaxInP;
	}

	/**
	 * @return the totalTaxAmountC
	 */
	public String getTotalTaxAmountC() {
		return totalTaxAmountC;
	}

	/**
	 * @return the totalTaxAmountP
	 */
	public String getTotalTaxAmountP() {
		return totalTaxAmountP;
	}

	/**
	 * @return the hopeDisplay
	 */
	public boolean getHopeDisplay() {
		return hopeDisplay;
	}

	/**
	 * @return the accountingHeadList
	 */
	public List<Exp0501AccountingHeadModel> getAccountingHeadList() {
		return accountingHeadList;
	}

}
