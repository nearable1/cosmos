/**
 * 
 */
package com.nssol_sh.entity.wx.view.syz.yexp.exp0701;

import java.util.List;
import java.util.Map;

/**
 * 交际费申请
 * 
 * @author S1mple
 *
 */
public class Exp0701MatterUserInfoModel {

	/**
	 * 招待第几次
	 */
	public String applicationsTimes;

	/**
	 * 招待次数一览
	 */
	public List<Map<String, Object>> applicationsTimesList;

	/**
	 * 本地货币名称
	 */
	public String localCurrencyName;

	/**
	 * 货币一览
	 */
	public List<Map<String, Object>> entmCurrencyCdList;

	/**
	 * 申请日 .
	 */
	public String applyDate;

	/**
	 * 项目Cd .
	 */
	public String projectCd;

	/**
	 * 项目名称 .
	 */
	public String projectName;

	/**
	 * 项目名称 .
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
	 * 借款金额/借款冲销金额 .
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
	 * 借款金额-支付方式 .
	 */
	public String payMethod;

	/**
	 * 借款金额-支付方式名 .
	 */
	public String payMethodName;

	/**
	 * 借款金额-支付方式一览 .
	 */
	public List<Map<String, Object>> writeOffPaymentList;

	/**
	 * 借款金额-希望支付日 .
	 */
	public String hopePayDate;

	/**
	 * 预算金额
	 */
	public String entmTotalAmount;

	/**
	 * 平均每人消费金额
	 */
	public String entmAmountPerPerson;

	/**
	 * 业务招待费用明细一览
	 */
	public List<Exp0701EntmDetailModel> entmDetailModelList;

	/**
	 * 财务支付标志 true：有；false：无
	 */
	public boolean accountingFlag;

	/**
	 * 财务支付一览
	 */
	public Exp0701AccountingCmmModel accountingCmmDto;

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
	 * @return the applyDate
	 */
	public String getApplyDate() {
		return applyDate;
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
	 * @return the entmDetailModelList
	 */
	public List<Exp0701EntmDetailModel> getEntmDetailModelList() {
		return entmDetailModelList;
	}

	/**
	 * @return the accountingFlag
	 */
	public boolean isAccountingFlag() {
		return accountingFlag;
	}

	/**
	 * @return the accountingCmmDto
	 */
	public Exp0701AccountingCmmModel getAccountingCmmDto() {
		return accountingCmmDto;
	}

	/**
	 * @return the localCurrencyName
	 */
	public String getLocalCurrencyName() {
		return localCurrencyName;
	}

	/**
	 * @return the entmCurrencyCdList
	 */
	public List<Map<String, Object>> getEntmCurrencyCdList() {
		return entmCurrencyCdList;
	}

	/**
	 * @return the applicationsTimesList
	 */
	public List<Map<String, Object>> getApplicationsTimesList() {
		return applicationsTimesList;
	}

	/**
	 * @return the applicationsTimes
	 */
	public String getApplicationsTimes() {
		return applicationsTimes;
	}

}
