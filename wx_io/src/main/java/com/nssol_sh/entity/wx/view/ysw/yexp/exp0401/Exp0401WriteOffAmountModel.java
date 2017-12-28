/**
 * 
 */
package com.nssol_sh.entity.wx.view.ysw.yexp.exp0401;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 借款金额
 * 
 * @author S1mple
 *
 */
public class Exp0401WriteOffAmountModel {

	/**
	 * 借款金额.
	 */
	public BigDecimal amount;

	/**
	 * 借款币种代码.
	 */
	public String currencyCd;

	/**
	 * 借款币种名.
	 */
	public String currencyName;

	/**
	 * 支付方式编码.
	 */
	public String payMethod;

	/**
	 * 支付方式名.
	 */
	public String payMethodName;

	/**
	 * 支付方式一览.
	 */
	public List<Map<String, Object>> payMethodList;

	/**
	 * 币种一览
	 */
	public List<Map<String, Object>> currencyList;

	/**
	 * 支付希望日
	 */
	public String hopePayDate;

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
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
	 * @return the payMethod
	 */
	public String getPayMethod() {
		return payMethod;
	}

	/**
	 * @return the payMethodName
	 */
	public String getPayMethodName() {
		return payMethodName;
	}

	/**
	 * @return the payMethodList
	 */
	public List<Map<String, Object>> getPayMethodList() {
		return payMethodList;
	}

	/**
	 * @return the currencyList
	 */
	public List<Map<String, Object>> getCurrencyList() {
		return currencyList;
	}

	/**
	 * @return the hopePayDate
	 */
	public String getHopePayDate() {
		return hopePayDate;
	}

}
