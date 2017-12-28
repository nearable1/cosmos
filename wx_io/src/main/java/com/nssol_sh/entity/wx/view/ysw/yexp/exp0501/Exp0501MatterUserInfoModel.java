package com.nssol_sh.entity.wx.view.ysw.yexp.exp0501;

import java.util.List;
import java.util.Map;

/**
 * 其他经费报销
 * 
 * @author long.liming
 *
 */
public class Exp0501MatterUserInfoModel {
	/** 申请日期. */
	public String applyDate;
	/** 項目代码. */
	public String projectCd;
	/** 項目名字. */
	public String projectName;
	/** 項目負責人. */
	public String pmName;

	/** 目的 */
	public String reimbursePurpose;
	/**
	 * 公司编码.
	 */
	public String companyCd;
	/**
	 * 公司名.
	 */
	public String companyName;
	/**
	 * 公司一览.
	 */
	public List<Map<String, Object>> companyList;

	/**
	 * 借款冲销金额一览.
	 */
	public List<Exp0501LoanModel> loanDtoList;

	/**
	 * 其他经费报销（0：无、1：有）.
	 */
	public String hasEexpenseFlag;
	/**
	 * 其他经费报销一览.
	 */
	public List<Exp0501DetailModel> detailDtoList;

	/** 借款冲销金额 */
	public String writeOffAmount;

	/** 报销支付金额. */
	public String paymentAmount;

	/** 公司信用卡金额. */
	public String companyAmount;

	/** 报销申请总金额. */
	public String detailTotal;

	/**
	 * 有无财务模块（0：无、1：有）.
	 */
	public String accountingFlag;

	/**
	 * 财务入力模块.
	 */
	public Exp0501AccountingCmmModel accountingCmmDto;

	/**
	 * @return the loanDtoList
	 */
	public List<Exp0501LoanModel> getLoanDtoList() {
		return loanDtoList;
	}

	/**
	 * @return the accountingFlag
	 */
	public String getAccountingFlag() {
		return accountingFlag;
	}

	/**
	 * @return the accountingCmmDto
	 */
	public Exp0501AccountingCmmModel getAccountingCmmDto() {
		return accountingCmmDto;
	}

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
	 * @return the reimbursePurpose
	 */
	public String getReimbursePurpose() {
		return reimbursePurpose;
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
	 * @return the companyList
	 */
	public List<Map<String, Object>> getCompanyList() {
		return companyList;
	}

	/**
	 * @return the hasEexpenseFlag
	 */
	public String getHasEexpenseFlag() {
		return hasEexpenseFlag;
	}

	/**
	 * @return the detailDtoList
	 */
	public List<Exp0501DetailModel> getDetailDtoList() {
		return detailDtoList;
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
	 * @return the companyAmount
	 */
	public String getCompanyAmount() {
		return companyAmount;
	}

	/**
	 * @return the detailTotal
	 */
	public String getDetailTotal() {
		return detailTotal;
	}
}
