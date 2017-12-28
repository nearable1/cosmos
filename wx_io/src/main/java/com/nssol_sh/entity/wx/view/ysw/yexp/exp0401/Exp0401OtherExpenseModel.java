package com.nssol_sh.entity.wx.view.ysw.yexp.exp0401;

import java.util.List;
import java.util.Map;

/**
 * 出差报销- 出差其他费用报销
 * 
 * @author S1mple
 *
 */
public class Exp0401OtherExpenseModel {

	/**
	 * 支付方式一览
	 */
	public List<Map<String, Object>> settlPayWayList;

	/**
	 * 支付方式
	 */
	public String modeOfPayment;

	/**
	 * 支付方式名称
	 */
	public String modeOfPaymentName;

	/**
	 * 日期
	 */
	public String occurredDate;

	/**
	 * 经费大分类
	 */
	public String expBroadCategoryCd;

	/**
	 * 经费大分类名称
	 */
	public String expBroadCategoryName;

	/**
	 * 经费大分类一览
	 */
	public List<Map<String, Object>> expBroadCategoryList;

	/**
	 * 经费小分类
	 */
	public String expSubCategoryCd;

	/**
	 * 经费小分类名称
	 */
	public String expSubCategoryName;

	/**
	 * 经费小分类一览
	 */
	public List<Map<String, Object>> expSubCategoryList;

	/**
	 * 经费项目
	 */
	public String expItemCd;

	/**
	 * 经费项目名称
	 */
	public String expItemName;

	/**
	 * 经费项目一览
	 */
	public List<Map<String, Object>> expItemList;

	/**
	 * 金额
	 */
	public String otherExpenseAmount;

	/**
	 * 币种
	 */
	public String otherExpenseCurrencyCd;

	/**
	 * 币种名称
	 */
	public String otherExpenseCurrencyName;

	/**
	 * 币种一览
	 */
	public List<Map<String, Object>> currencyList;

	/**
	 * 汇率
	 */
	public String otherExpenseExchangeRate;

	/**
	 * 备注
	 */
	public String otherExpenseRemarks;

	/**
	 * @return the settlPayWayList
	 */
	public List<Map<String, Object>> getSettlPayWayList() {
		return settlPayWayList;
	}

	/**
	 * @return the modeOfPayment
	 */
	public String getModeOfPayment() {
		return modeOfPayment;
	}

	/**
	 * @return the modeOfPaymentName
	 */
	public String getModeOfPaymentName() {
		return modeOfPaymentName;
	}

	/**
	 * @return the occurredDate
	 */
	public String getOccurredDate() {
		return occurredDate;
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
	 * @return the otherExpenseAmount
	 */
	public String getOtherExpenseAmount() {
		return otherExpenseAmount;
	}

	/**
	 * @return the otherExpenseCurrencyCd
	 */
	public String getOtherExpenseCurrencyCd() {
		return otherExpenseCurrencyCd;
	}

	/**
	 * @return the otherExpenseCurrencyName
	 */
	public String getOtherExpenseCurrencyName() {
		return otherExpenseCurrencyName;
	}

	/**
	 * @return the currencyList
	 */
	public List<Map<String, Object>> getCurrencyList() {
		return currencyList;
	}

	/**
	 * @return the otherExpenseExchangeRate
	 */
	public String getOtherExpenseExchangeRate() {
		return otherExpenseExchangeRate;
	}

	/**
	 * @return the otherExpenseRemarks
	 */
	public String getOtherExpenseRemarks() {
		return otherExpenseRemarks;
	}

}
