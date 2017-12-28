/**
 * 
 */
package com.nssol_sh.entity.wx.view.ysw.yexp.exp0801;

import java.util.List;
import java.util.Map;

/**
 * 交际费报销
 * 
 * @author S1mple
 *
 */
public class Exp0801EntmDetailModel {

	/**
	 * 备注／其他
	 */
	public boolean viewFlag;

	/**
	 * 事业部Cd
	 */
	public String buCd;

	/**
	 * 事业部名称
	 */
	public String buName;

	/**
	 * 事业部一览
	 */
	public List<Map<String, Object>> buList;

	/**
	 * 部门
	 */
	public String deptCd;

	/**
	 * 部门名称
	 */
	public String deptName;

	/**
	 * 部门一览
	 */
	public List<Map<String, Object>> deptList;

	/**
	 * 成本中心
	 */
	public String costCenterCd;

	/**
	 * 成本中心名称
	 */
	public String costCenterName;

	/**
	 * 成本中心一览
	 */
	public List<Map<String, Object>> costCenterList;

	/**
	 * 金额
	 */
	public String amount;

	/**
	 * 币种
	 */
	public String entmCurrencyCd;

	/**
	 * 币种名称
	 */
	public String entmCurrencyName;

	/**
	 * 币种一览
	 */
	public List<Map<String, Object>> entmCurrencyCdList;

	/**
	 * 汇率
	 */
	public String exchangeRate;

	/**
	 * 业务招待费用明细-支付方式
	 */
	public String modeOfPayment;

	/**
	 * 业务招待费用明细-支付方式名称
	 */
	public String modeOfPaymentName;

	/**
	 * 业务招待费用明细-支付方式一览
	 */
	public List<Map<String, Object>> payWayList;

	/**
	 * 招待日期
	 */
	public String entmDate;

	/**
	 * 招待地点
	 */
	public String entmAddress;

	/**
	 * 招待方参加者
	 */
	public String entmCustomerParticipants;

	/**
	 * 招待人数
	 */
	public String entmCustomerNumber;

	/**
	 * 我方参加者
	 */
	public String entmOurParticipants;

	/**
	 * 我方人数
	 */
	public String entmOurNumber;

	/**
	 * 备注 类型
	 */
	public String communicationType;

	/**
	 * 备注
	 */
	public String communicationName;

	/**
	 * 备注一览
	 */
	public List<Map<String, Object>> communicationTypeList;

	/**
	 * 备注-其他 内容
	 */
	public String remarks;

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
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @return the entmCurrencyCd
	 */
	public String getEntmCurrencyCd() {
		return entmCurrencyCd;
	}

	/**
	 * @return the entmCurrencyName
	 */
	public String getEntmCurrencyName() {
		return entmCurrencyName;
	}

	/**
	 * @return the entmCurrencyCdList
	 */
	public List<Map<String, Object>> getEntmCurrencyCdList() {
		return entmCurrencyCdList;
	}

	/**
	 * @return the entmDate
	 */
	public String getEntmDate() {
		return entmDate;
	}

	/**
	 * @return the entmAddress
	 */
	public String getEntmAddress() {
		return entmAddress;
	}

	/**
	 * @return the entmCustomerParticipants
	 */
	public String getEntmCustomerParticipants() {
		return entmCustomerParticipants;
	}

	/**
	 * @return the entmCustomerNumber
	 */
	public String getEntmCustomerNumber() {
		return entmCustomerNumber;
	}

	/**
	 * @return the entmOurParticipants
	 */
	public String getEntmOurParticipants() {
		return entmOurParticipants;
	}

	/**
	 * @return the entmOurNumber
	 */
	public String getEntmOurNumber() {
		return entmOurNumber;
	}

	/**
	 * @return the communicationType
	 */
	public String getCommunicationType() {
		return communicationType;
	}

	/**
	 * @return the communicationName
	 */
	public String getCommunicationName() {
		return communicationName;
	}

	/**
	 * @return the communicationTypeList
	 */
	public List<Map<String, Object>> getCommunicationTypeList() {
		return communicationTypeList;
	}

	/**
	 * @return the viewFlag
	 */
	public boolean isViewFlag() {
		return viewFlag;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the exchangeRate
	 */
	public String getExchangeRate() {
		return exchangeRate;
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
	 * @return the payWayList
	 */
	public List<Map<String, Object>> getPayWayList() {
		return payWayList;
	}

}
