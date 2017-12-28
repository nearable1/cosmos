package com.nssol_sh.entity.wx.view.yci.yexp.exp0501;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 其他经费报销
 * 
 * @author long.liming
 *
 */
public class Exp0501DetailModel {
	/**
	 * 序号.
	 */
	public BigDecimal sortKey;
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
	 * 事业部编码.
	 */
	public String buCd;

	/**
	 * 事业部名称.
	 */
	public String buName;

	/**
	 * 事业部一览.
	 */
	public List<Map<String, Object>> buList;

	/**
	 * 部门编码.
	 */
	public String deptCd;

	/**
	 * 部门名.
	 */
	public String deptName;

	/**
	 * 部门一览.
	 */
	public List<Map<String, Object>> deptList;
	/**
	 * 成本中心代码.
	 */
	public String costCenterCd;

	/**
	 * 成本中心名.
	 */
	public String costCenterName;

	/**
	 * 成本中心一览.
	 */
	public List<Map<String, Object>> costCenterList;
	/**
	 * 金额.
	 */
	public String amount;
	/**
	 * 支付方式编码.
	 */
	public String payWayCd;

	/**
	 * 支付方式名.
	 */
	public String payWayName;

	/**
	 * 支付方式一览.
	 */
	public List<Map<String, Object>> payWayList;

	/**
	 * 币种代码.
	 */
	public String currencyCd;

	/**
	 * 币种名.
	 */
	public String currencyName;

	/**
	 * 币种列表一览
	 */
	public List<Map<String, Object>> currencyList;
	/**
	 * 税率编码
	 */
	public String taxRateCd;

	/**
	 * 税率名.
	 */
	public String taxRateName;

	/**
	 * 税率一览.
	 */
	public List<Map<String, Object>> taxRateList;

	/**
	 * 汇率.
	 */
	public BigDecimal exchangeRate;
	/**
	 * 发票.
	 */
	public String invoice;
	/**
	 * 合作伙伴.
	 */
	public String partnerCd;

	/**
	 * 用途.
	 */
	public String remarks;

	/**
	 * @return the sortKey
	 */
	public BigDecimal getSortKey() {
		return sortKey;
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
	 * @return the buCd
	 */
	public String getBuCd() {
		return buCd;
	}

	/**
	 * @return the buName
	 */
	public String getBuName() {
		return buName;
	}

	/**
	 * @return the buList
	 */
	public List<Map<String, Object>> getBuList() {
		return buList;
	}

	/**
	 * @return the deptCd
	 */
	public String getDeptCd() {
		return deptCd;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @return the deptList
	 */
	public List<Map<String, Object>> getDeptList() {
		return deptList;
	}

	/**
	 * @return the costCenterCd
	 */
	public String getCostCenterCd() {
		return costCenterCd;
	}

	/**
	 * @return the costCenterName
	 */
	public String getCostCenterName() {
		return costCenterName;
	}

	/**
	 * @return the costCenterList
	 */
	public List<Map<String, Object>> getCostCenterList() {
		return costCenterList;
	}

	/**
	 * @return the payWayCd
	 */
	public String getPayWayCd() {
		return payWayCd;
	}

	/**
	 * @return the payWayName
	 */
	public String getPayWayName() {
		return payWayName;
	}

	/**
	 * @return the payWayList
	 */
	public List<Map<String, Object>> getPayWayList() {
		return payWayList;
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
	 * @return the origExchangeRate
	 */
	public BigDecimal getExchangeRate() {
		return exchangeRate;
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
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

}
