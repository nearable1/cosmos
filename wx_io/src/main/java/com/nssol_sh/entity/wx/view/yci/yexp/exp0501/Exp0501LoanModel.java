package com.nssol_sh.entity.wx.view.yci.yexp.exp0501;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 借款冲销金额
 * 
 * @author long.liming
 *
 */
public class Exp0501LoanModel {
	/**
	 * 是否选中.
	 */
	public String checked;
	/**
	 * 序号.
	 */
	public BigDecimal sortKey;
	/**
	 * 案件号.
	 */
	public String matterId;
	/**
	 * 申请日.
	 */
	public String applyDate;
	/**
	 * 支付日.
	 */
	public String payDate;
	/**
	 * 报销支付金额.
	 */
	public String amount;
	/**
	 * 币种名.
	 */
	public String currencyName;
	/**
	 * 币种一览.
	 *//*
		 * public List<Map<String, Object>> currencyList;
		 */
	/**
	 * 支付方式编码.
	 */
	public String payMethodCd;
	/**
	 * 支付方式名称.
	 */
	public String payMethodName;
	/**
	 * 支付方式一览.
	 */
	public List<Map<String, Object>> payMethodList;

	/**
	 * 说明.
	 */
	public String purpose;

	/**
	 * @return the payMethodName
	 */
	public String getPayMethodName() {
		return payMethodName;
	}

	/**
	 * @return the checked
	 */
	public String getChecked() {
		return checked;
	}

	/**
	 * @return the sortKey
	 */
	public BigDecimal getSortKey() {
		return sortKey;
	}

	/**
	 * @return the matterId
	 */
	public String getMatterId() {
		return matterId;
	}

	/**
	 * @return the applyDate
	 */
	public String getApplyDate() {
		return applyDate;
	}

	/**
	 * @return the payDate
	 */
	public String getPayDate() {
		return payDate;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * @return the payMethodCd
	 */
	public String getPayMethodCd() {
		return payMethodCd;
	}

	/**
	 * @return the payMethodList
	 */
	public List<Map<String, Object>> getPayMethodList() {
		return payMethodList;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}
}
