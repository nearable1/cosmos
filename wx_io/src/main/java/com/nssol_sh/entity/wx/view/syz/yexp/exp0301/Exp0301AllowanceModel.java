package com.nssol_sh.entity.wx.view.syz.yexp.exp0301;

import java.math.BigDecimal;

/**
 * 补贴
 * 
 * @author liu.yigeng
 *
 */
public class Exp0301AllowanceModel {

	/**
	 * 序号.
	 */
	public BigDecimal sortKey;

	/**
	 * 补贴项目名. (参照IM，已使用多语言Message，弃用)
	 */
	public String itemCd;

	/**
	 * 小计.
	 */
	public BigDecimal subtotal;

	/**
	 * 币种代码.
	 */
	public String currencyCd;

	/**
	 * 币种名.
	 */
	public String currencyName;

	/**
	 * 参考汇率.
	 */
	public BigDecimal exchangeRate;

	/**
	 * 小计（元）.
	 */
	public BigDecimal subtotalLocalCurrency;

	/**
	 * @return the sortKey
	 */
	public BigDecimal getSortKey() {
		return sortKey;
	}

	/**
	 * @return the itemCd
	 */
	public String getItemCd() {
		return itemCd;
	}

	/**
	 * @return the subtotal
	 */
	public BigDecimal getSubtotal() {
		return subtotal;
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
	 * @return the exchangeRate
	 */
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @return the subtotalLocalCurrency
	 */
	public BigDecimal getSubtotalLocalCurrency() {
		return subtotalLocalCurrency;
	}

}
