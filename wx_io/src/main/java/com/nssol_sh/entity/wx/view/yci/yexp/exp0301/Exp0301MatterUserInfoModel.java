package com.nssol_sh.entity.wx.view.yci.yexp.exp0301;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Exp0301MatterUserInfoModel {

	/**
	 * 申请者编码.
	 */
	public String applyUserCd;

	/**
	 * 申请者名.
	 */
	public String imwApplyUserName;

	/**
	 * 是否代理.
	 */
	public String agencyApply;

	/**
	 * 代理者编码.
	 */
	public String agencyApplyUserCd;

	/**
	 * 代理者名.
	 */
	public String imwApplyAgentName;

	/**
	 * 申请主所属部门编码.
	 */
	public String mainDepartmentCd;

	/**
	 * 申请主所属部门名.
	 */
	public String imwApplyUserDepartName;

	/**
	 * 申请日.
	 */
	public String applyDate;

	/**
	 * 项目代码.
	 */
	public String projectCd;

	/**
	 * 项目名.
	 */
	public String projectName;

	/**
	 * 项目负责人编码.
	 */
	public String pmCode;

	/**
	 * 项目负责人名.
	 */
	public String pmName;

	/**
	 * 出差期间开始日.
	 */
	public String bizTripStartDate;

	/**
	 * 出差期间结束日.
	 */
	public String bizTripEndDate;

	/**
	 * 国内国外区分.
	 */
	public String overseasFlag;

	/**
	 * 国内国外区分名.
	 */
	public String overseasFlagName;

	/**
	 * 国内海外区分一览.
	 */
	public List<Map<String, Object>> offshoreList;

	/**
	 * 出差的内容和目的.
	 */
	public String bizTripPurpose;

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
	 * 支付希望日
	 */
	public String hopePayDate;

	/**
	 * 本土货币代码
	 */
	public String localCurrencyCd;

	/**
	 * 本土货币名
	 */
	public String localCurrencyName;

	/**
	 * 币种列表
	 */
	public List<Map<String, Object>> currencyList;

	/**
	 * 出差日程一览.
	 */
	public List<Exp0301ScheduleModel> scheduleDtoList;

	/**
	 * 公司代订（0：无、1：有）.
	 */
	public String ticketReserveFlag;

	/**
	 * 公司代订一览.
	 */
	public List<Exp0301FlightTicketModel> flightTicketDtoList;

	/**
	 * 个人预订（0：无、1：有）.
	 */
	public String personalTicketReserveFlag;

	/**
	 * 个人预订一览.
	 */
	public List<Exp0301RailwayTicketModel> railwayTicketDtoList;

	/**
	 * 有住宿（0：无、1：有）.
	 */
	public String hotelRequiredFlag;

	/**
	 * 住宿一览.
	 */
	public List<Exp0301HotelModel> hotelDtoList;

	/**
	 * 补贴一览.
	 */
	public List<Exp0301AllowanceModel> allowanceDtoList;

	/**
	 * 交通费预算（个人）合计.
	 */
	public BigDecimal ticketBudgetTotalP;

	/**
	 * 交通费预算（公司）合计.
	 */
	public BigDecimal ticketBudgetTotalC;

	/**
	 * 交通费预算合计.
	 */
	public BigDecimal ticketBudgetTotal;

	/**
	 * 住宿费合计.
	 */
	public BigDecimal hotelBudgetTotal;

	/**
	 * 住宿费合计.
	 */
	public BigDecimal shceduleBudgetTotal;

	/**
	 * 小计（个人）.
	 */
	public BigDecimal subtotalP;

	/**
	 * 小计（公司）.
	 */
	public BigDecimal subtotalC;

	/**
	 * 预算总金额.
	 */
	public BigDecimal budgetTotal;

	/**
	 * 有无财务模块（0：无、1：有）.
	 */
	public String accountingFlag;

	/**
	 * 财务入力模块.
	 */
	public Exp0301AccountingCmmModel accountingCmmDto;

	/**
	 * @return the accountingFlag
	 */
	public String getAccountingFlag() {
		return accountingFlag;
	}

	/**
	 * @return the currencyList
	 */
	public List<Map<String, Object>> getCurrencyList() {
		return currencyList;
	}

	/**
	 * @return the localCurrencyCd
	 */
	public String getLocalCurrencyCd() {
		return localCurrencyCd;
	}

	/**
	 * @return the localCurrencyName
	 */
	public String getLocalCurrencyName() {
		return localCurrencyName;
	}

	/**
	 * @return the applyUserCd
	 */
	public String getApplyUserCd() {
		return applyUserCd;
	}

	/**
	 * @return the imwApplyUserName
	 */
	public String getImwApplyUserName() {
		return imwApplyUserName;
	}

	/**
	 * @return the agencyApply
	 */
	public String getAgencyApply() {
		return agencyApply;
	}

	/**
	 * @return the agencyApplyUserCd
	 */
	public String getAgencyApplyUserCd() {
		return agencyApplyUserCd;
	}

	/**
	 * @return the imwApplyAgentName
	 */
	public String getImwApplyAgentName() {
		return imwApplyAgentName;
	}

	/**
	 * @return the mainDepartmentCd
	 */
	public String getMainDepartmentCd() {
		return mainDepartmentCd;
	}

	/**
	 * @return the imwApplyUserDepartName
	 */
	public String getImwApplyUserDepartName() {
		return imwApplyUserDepartName;
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
	 * @return the pmCode
	 */
	public String getPmCode() {
		return pmCode;
	}

	/**
	 * @return the pmName
	 */
	public String getPmName() {
		return pmName;
	}

	/**
	 * @return the bizTripStartDate
	 */
	public String getBizTripStartDate() {
		return bizTripStartDate;
	}

	/**
	 * @return the bizTripEndDate
	 */
	public String getBizTripEndDate() {
		return bizTripEndDate;
	}

	/**
	 * @return the overseasFlag
	 */
	public String getOverseasFlag() {
		return overseasFlag;
	}

	/**
	 * @return the overseasFlagName
	 */
	public String getOverseasFlagName() {
		return overseasFlagName;
	}

	/**
	 * @return the offshoreList
	 */
	public List<Map<String, Object>> getOffshoreList() {
		return offshoreList;
	}

	/**
	 * @return the bizTripPurpose
	 */
	public String getBizTripPurpose() {
		return bizTripPurpose;
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
	 * @return the hopePayDate
	 */
	public String getHopePayDate() {
		return hopePayDate;
	}

	/**
	 * @return the scheduleDtoList
	 */
	public List<Exp0301ScheduleModel> getScheduleDtoList() {
		return scheduleDtoList;
	}

	/**
	 * @return the ticketReserveFlag
	 */
	public String getTicketReserveFlag() {
		return ticketReserveFlag;
	}

	/**
	 * @return the flightTicketDtoList
	 */
	public List<Exp0301FlightTicketModel> getFlightTicketDtoList() {
		return flightTicketDtoList;
	}

	/**
	 * @return the personalTicketReserveFlag
	 */
	public String getPersonalTicketReserveFlag() {
		return personalTicketReserveFlag;
	}

	/**
	 * @return the railwayTicketDtoList
	 */
	public List<Exp0301RailwayTicketModel> getRailwayTicketDtoList() {
		return railwayTicketDtoList;
	}

	/**
	 * @return the hotelRequiredFlag
	 */
	public String getHotelRequiredFlag() {
		return hotelRequiredFlag;
	}

	/**
	 * @return the hotelDtoList
	 */
	public List<Exp0301HotelModel> getHotelDtoList() {
		return hotelDtoList;
	}

	/**
	 * @return the allowanceDtoList
	 */
	public List<Exp0301AllowanceModel> getAllowanceDtoList() {
		return allowanceDtoList;
	}

	/**
	 * @return the ticketBudgetTotalP
	 */
	public BigDecimal getTicketBudgetTotalP() {
		return ticketBudgetTotalP;
	}

	/**
	 * @return the ticketBudgetTotalC
	 */
	public BigDecimal getTicketBudgetTotalC() {
		return ticketBudgetTotalC;
	}

	/**
	 * @return the ticketBudgetTotal
	 */
	public BigDecimal getTicketBudgetTotal() {
		return ticketBudgetTotal;
	}

	/**
	 * @return the hotelBudgetTotal
	 */
	public BigDecimal getHotelBudgetTotal() {
		return hotelBudgetTotal;
	}

	/**
	 * @return the shceduleBudgetTotal
	 */
	public BigDecimal getShceduleBudgetTotal() {
		return shceduleBudgetTotal;
	}

	/**
	 * @return the subtotalP
	 */
	public BigDecimal getSubtotalP() {
		return subtotalP;
	}

	/**
	 * @return the subtotalC
	 */
	public BigDecimal getSubtotalC() {
		return subtotalC;
	}

	/**
	 * @return the budgetTotal
	 */
	public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}

	/**
	 * @return the accountingCmmDto
	 */
	public Exp0301AccountingCmmModel getAccountingCmmDto() {
		return accountingCmmDto;
	}

}
