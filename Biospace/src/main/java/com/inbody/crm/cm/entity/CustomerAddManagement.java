package com.inbody.crm.cm.entity;


import java.util.Date;

import com.inbody.crm.common.persistence.DataEntity;

public class CustomerAddManagement extends DataEntity<CustomerAddManagement>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String customerId;		// 客户代码 : &ldquo;KH&rdquo;(2)+流水(6)
	private String customerChName;		// 客户名称
	private String customerParentCo;		// 客户总公司代码 : customer_id
	private String customerType;		// 客户类型 : 代码
	private String axCusNo;		// AX编号
	private Date developDate;	
	private String Date;// 发展日期
	private String lastVisitDate;		// 最后拜访日期
	private String ifAgreement;		// 是否协议
	private String validityDateFrom;//协议开始日期
	private String validityDateTo;//协议结束日期
	private String responsiblePersonId;		// 负责人
	private String ifImportant;		// 是否重点对象
	private String ifEffective;		// 是否有效
	private String industry;		// 行业 : 代码
	private String customerDiff;		// 客户分类 : 代码
	private String region;		// 地区 : 代码
	private String province;		// 省市 : 代码
	private String city;		// 城市 : 代码
	private String address;		// 地址
	private String invoiceType;		// 开票类型 : 代码
	private String invoiceTitle;		// 开票抬头
	private String taxpayerIdentNo;		// 纳税人识别号
	private String depositBank;		// 开户行
	private String bankAccount;		// 银行账号
	private String invoiceAddress;		// 开票地址
	private String telephone;		// 电话
	private String zipCode;	
	private String group;
	private String officeId;
	private String officeName;
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getOfficeId() {
		return officeId;
	}
	
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerChName() {
		return customerChName;
	}
	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}
	public String getCustomerParentCo() {
		return customerParentCo;
	}
	public void setCustomerParentCo(String customerParentCo) {
		this.customerParentCo = customerParentCo;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getAxCusNo() {
		return axCusNo;
	}
	public void setAxCusNo(String axCusNo) {
		this.axCusNo = axCusNo;
	}
	public Date getDevelopDate() {
		return developDate;
	}
	public void setDevelopDate(Date developDate) {
		this.developDate = developDate;
	}
	public String getLastVisitDate() {
		return lastVisitDate;
	}
	public void setLastVisitDate(String lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}
	public String getIfAgreement() {
		return ifAgreement;
	}
	public void setIfAgreement(String ifAgreement) {
		this.ifAgreement = ifAgreement;
	}
	public String getValidityDateFrom() {
		return validityDateFrom;
	}
	public void setValidityDateFrom(String validityDateFrom) {
		this.validityDateFrom = validityDateFrom;
	}
	public String getValidityDateTo() {
		return validityDateTo;
	}
	public void setValidityDateTo(String validityDateTo) {
		this.validityDateTo = validityDateTo;
	}
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}
	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	public String getIfImportant() {
		return ifImportant;
	}
	public void setIfImportant(String ifImportant) {
		this.ifImportant = ifImportant;
	}
	public String getIfEffective() {
		return ifEffective;
	}
	public void setIfEffective(String ifEffective) {
		this.ifEffective = ifEffective;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getCustomerDiff() {
		return customerDiff;
	}
	public void setCustomerDiff(String customerDiff) {
		this.customerDiff = customerDiff;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getTaxpayerIdentNo() {
		return taxpayerIdentNo;
	}
	public void setTaxpayerIdentNo(String taxpayerIdentNo) {
		this.taxpayerIdentNo = taxpayerIdentNo;
	}
	public String getDepositBank() {
		return depositBank;
	}
	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getInvoiceAddress() {
		return invoiceAddress;
	}
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

}
