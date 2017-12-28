/**
 * 
 */
package com.nssol_sh.entity.wx.view.ysw.yexp.exp0801;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 交际费报销
 * 
 * @author S1mple
 *
 */
public class Exp0801AccountingDetailRowModel {

	/**
	 * 借贷区分.
	 */
	public String debtorFlag;

	/**
	 * 借贷区分名.
	 */
	public String debtorFlagName;

	/**
	 * 经费大分类编码
	 */
	public String expBroadCategoryCd;

	/**
	 * 经费大分类名.
	 */
	public String expBroadCategoryName;

	/**
	 * 经费大分类一览
	 */
	public List<Map<String, Object>> expBroadCategoryList;

	/**
	 * 经费小分类编码.
	 */
	public String expSubCategoryCd;

	/**
	 * 经费小分类名.
	 */
	public String expSubCategoryName;

	/**
	 * 经费小分类一览.
	 */
	public List<Map<String, Object>> expSubCategoryList;

	/**
	 * 经费项目编码.
	 */
	public String expItemCd;

	/**
	 * 经费项目名.
	 */
	public String expItemName;

	/**
	 * 经费项目一览.
	 */
	public List<Map<String, Object>> expItemList;

	/**
	 * 科目代码.
	 */
	public String accountingSubjectCd;

	/**
	 * 科目名.
	 */
	public String accountingSubjectName;

	/**
	 * 含税金额.
	 */
	public String paymentAmountTaxIn;

	/**
	 * 税代码编码
	 */
	public String taxRateCd;

	/**
	 * 税率代码名.
	 */
	public String taxRateName;

	/**
	 * 税率代码一览.
	 */
	public List<Map<String, Object>> taxRateList;

	/**
	 * 税率项目名.
	 */
	public String inputTaxName;

	/**
	 * 税率项目一览.
	 */
	public List<Map<String, Object>> inputTaxList;

	/**
	 * 费用金额.
	 */
	public String paymentAmountTaxOut;

	/**
	 * 税额.
	 */
	public String taxAmount;

	/**
	 * 币种编码
	 */
	public String currencyCd;

	/**
	 * 币种名
	 */
	public String currencyName;

	/**
	 * 币种一览.
	 */
	public List<Map<String, Object>> currencyList;

	/**
	 * 汇率.
	 */
	public BigDecimal origExchangeRate;

	/**
	 * 成本中心.
	 */
	public String costCenterCd;

	/**
	 * 发票.
	 */
	public String invoice;

	/**
	 * 合作伙伴.
	 */
	public String partnerCd;

	/**
	 * 行文本摘要.
	 */
	public String summary;

	/**
	 * @return the debtorFlag
	 */
	public String getDebtorFlag() {
		return debtorFlag;
	}

	/**
	 * @return the debtorFlagName
	 */
	public String getDebtorFlagName() {
		return debtorFlagName;
	}

	/**
	 * @return the expBroadCategoryCd
	 */
	public String getExpBroadCategoryCd() {
		return expBroadCategoryCd;
	}

	/**
	 * @return the expBroadCategoryName
	 */
	public String getExpBroadCategoryName() {
		return expBroadCategoryName;
	}

	/**
	 * @return the expBroadCategoryList
	 */
	public List<Map<String, Object>> getExpBroadCategoryList() {
		return expBroadCategoryList;
	}

	/**
	 * @return the expSubCategoryCd
	 */
	public String getExpSubCategoryCd() {
		return expSubCategoryCd;
	}

	/**
	 * @return the expSubCategoryName
	 */
	public String getExpSubCategoryName() {
		return expSubCategoryName;
	}

	/**
	 * @return the expSubCategoryList
	 */
	public List<Map<String, Object>> getExpSubCategoryList() {
		return expSubCategoryList;
	}

	/**
	 * @return the expItemCd
	 */
	public String getExpItemCd() {
		return expItemCd;
	}

	/**
	 * @return the expItemName
	 */
	public String getExpItemName() {
		return expItemName;
	}

	/**
	 * @return the expItemList
	 */
	public List<Map<String, Object>> getExpItemList() {
		return expItemList;
	}

	/**
	 * @return the accountingSubjectCd
	 */
	public String getAccountingSubjectCd() {
		return accountingSubjectCd;
	}

	/**
	 * @return the accountingSubjectName
	 */
	public String getAccountingSubjectName() {
		return accountingSubjectName;
	}

	/**
	 * @return the paymentAmountTaxIn
	 */
	public String getPaymentAmountTaxIn() {
		return paymentAmountTaxIn;
	}

	/**
	 * @return the taxRateCd
	 */
	public String getTaxRateCd() {
		return taxRateCd;
	}

	/**
	 * @return the taxRateName
	 */
	public String getTaxRateName() {
		return taxRateName;
	}

	/**
	 * @return the taxRateList
	 */
	public List<Map<String, Object>> getTaxRateList() {
		return taxRateList;
	}

	/**
	 * @return the inputTaxName
	 */
	public String getInputTaxName() {
		return inputTaxName;
	}

	/**
	 * @return the inputTaxList
	 */
	public List<Map<String, Object>> getInputTaxList() {
		return inputTaxList;
	}

	/**
	 * @return the paymentAmountTaxOut
	 */
	public String getPaymentAmountTaxOut() {
		return paymentAmountTaxOut;
	}

	/**
	 * @return the taxAmount
	 */
	public String getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @return the currencyCd
	 */
	public String getCurrencyCd() {
		return currencyCd;
	}

	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * @return the currencyList
	 */
	public List<Map<String, Object>> getCurrencyList() {
		return currencyList;
	}

	/**
	 * @return the origExchangeRate
	 */
	public BigDecimal getOrigExchangeRate() {
		return origExchangeRate;
	}

	/**
	 * @return the costCenterCd
	 */
	public String getCostCenterCd() {
		return costCenterCd;
	}

	/**
	 * @return the invoice
	 */
	public String getInvoice() {
		return invoice;
	}

	/**
	 * @return the partnerCd
	 */
	public String getPartnerCd() {
		return partnerCd;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

}
