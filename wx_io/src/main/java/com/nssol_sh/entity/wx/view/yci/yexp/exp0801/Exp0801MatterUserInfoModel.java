/**
 * 
 */
package com.nssol_sh.entity.wx.view.yci.yexp.exp0801;

import java.util.List;
import java.util.Map;

/**
 * 交际费报销
 * 
 * @author S1mple
 *
 */
public class Exp0801MatterUserInfoModel {

	/**
	 * 申请日 .
	 */
	public String applyDate;

	/**
	 * 报销币种一览
	 */
	public List<Map<String, Object>> entmCurrencyCdList;

	/**
	 * 本地币种名
	 */
	public String localCurrencyName;

	/**
	 * 项目Cd .
	 */
	public String projectCd;

	/**
	 * 项目名称 .
	 */
	public String projectName;

	/**
	 * 项目负责人编码 .
	 */
	public String pmName;

	/**
	 * 招待对象公司名称 .
	 */
	public String customerCompanyName;

	/**
	 * 与我方关系 .
	 */
	public String relationWithUs;

	/**
	 * 与我方关系-备注 .
	 */
	public String relationWithUsRemarks;

	/**
	 * 目的 .
	 */
	public String purpose;

	/**
	 * 目的／其他／备注 .
	 */
	public String purposeRemarks;

	/**
	 * 公司cd .
	 */
	public String companyCd;

	/**
	 * 公司名称 .
	 */
	public String companyName;

	/**
	 * 公司一览 .
	 */
	public List<Map<String, Object>> companyNameList;

	/**
	 * 借款冲销金额 .
	 */
	public String amount;

	/**
	 * 币种编码
	 */
	public String entmCurrencyCd;

	/**
	 * 币种名称
	 */
	public String entmCurrencyName;

	/**
	 * 借款金额标志 true：有；false：无
	 */
	public boolean writeOffFlag;

	/**
	 * 借款金额-支付方式 .
	 */
	public String writeOffPayment;

	/**
	 * 借款金额-支付方式名 .
	 */
	public String payMethodName;

	/**
	 * 借款金额支付方式一览 .
	 */
	public List<Map<String, Object>> writeOffPaymentList;

	/**
	 * 希望支付日 .
	 */
	public String hopePayDate;

	/**
	 * 有业务招待标记
	 */
	public String hasEntmFlag;

	/**
	 * 交际费合计
	 */
	public String entmTotalAmount;

	/**
	 * 平均每人消费金额
	 */
	public String entmAmountPerPerson;

	/**
	 * 借款冲销金额
	 */
	public String writeOffAmount;

	/**
	 * 报销支付金额
	 */
	public String paymentAmount;

	/**
	 * 公司信用卡
	 */
	public String creditAmount;

	/**
	 * /** 业务招待费用明细一览
	 */
	public List<Exp0801EntmDetailModel> entmDetailModelList;

	/**
	 * 财务支付标志 true：有；false：无
	 */
	public boolean accountingFlag;

	/**
	 * 财务支付一览
	 */
	public Exp0801AccountingCmmModel accountingCmmDto;

	/**
	 * @return the applyDate
	 */
	public String getApplyDate() {
		return applyDate;
	}

	/**
	 * @return the projectCd
	 */
	public String getProjectCd() {
		return projectCd;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @return the pmName
	 */
	public String getPmName() {
		return pmName;
	}

	/**
	 * @return the customerCompanyName
	 */
	public String getCustomerCompanyName() {
		return customerCompanyName;
	}

	/**
	 * @return the relationWithUs
	 */
	public String getRelationWithUs() {
		return relationWithUs;
	}

	/**
	 * @return the relationWithUsRemarks
	 */
	public String getRelationWithUsRemarks() {
		return relationWithUsRemarks;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @return the purposeRemarks
	 */
	public String getPurposeRemarks() {
		return purposeRemarks;
	}

	/**
	 * @return the companyCd
	 */
	public String getCompanyCd() {
		return companyCd;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @return the companyNameList
	 */
	public List<Map<String, Object>> getCompanyNameList() {
		return companyNameList;
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
	 * @return the payMethodName
	 */
	public String getPayMethodName() {
		return payMethodName;
	}

	/**
	 * @return the writeOffPaymentList
	 */
	public List<Map<String, Object>> getWriteOffPaymentList() {
		return writeOffPaymentList;
	}

	/**
	 * @return the hopePayDate
	 */
	public String getHopePayDate() {
		return hopePayDate;
	}

	/**
	 * @return the entmTotalAmount
	 */
	public String getEntmTotalAmount() {
		return entmTotalAmount;
	}

	/**
	 * @return the entmAmountPerPerson
	 */
	public String getEntmAmountPerPerson() {
		return entmAmountPerPerson;
	}

	/**
	 * @return the accountingFlag
	 */
	public boolean isAccountingFlag() {
		return accountingFlag;
	}

	/**
	 * @return the hasEntmFlag
	 */
	public String getHasEntmFlag() {
		return hasEntmFlag;
	}

	/**
	 * @return the entmDetailModelList
	 */
	public List<Exp0801EntmDetailModel> getEntmDetailModelList() {
		return entmDetailModelList;
	}

	/**
	 * @return the accountingCmmDto
	 */
	public Exp0801AccountingCmmModel getAccountingCmmDto() {
		return accountingCmmDto;
	}

	/**
	 * @return the writeOffAmount
	 */
	public String getWriteOffAmount() {
		return writeOffAmount;
	}

	/**
	 * @return the paymentAmount
	 */
	public String getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @return the creditAmount
	 */
	public String getCreditAmount() {
		return creditAmount;
	}

	/**
	 * @return the entmCurrencyCdList
	 */
	public List<Map<String, Object>> getEntmCurrencyCdList() {
		return entmCurrencyCdList;
	}

	/**
	 * @return the localCurrencyName
	 */
	public String getLocalCurrencyName() {
		return localCurrencyName;
	}

	/**
	 * @return the writeOffPayment
	 */
	public String getWriteOffPayment() {
		return writeOffPayment;
	}

	/**
	 * @return the writeOffFlag
	 */
	public boolean isWriteOffFlag() {
		return writeOffFlag;
	}

}
