package com.inbody.crm.cm.entity;

import com.inbody.crm.common.persistence.ActEntity;
import com.inbody.crm.common.utils.excel.annotation.ExcelField;

public class CustomerManagementExecl extends ActEntity<CustomerManagementExecl>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5176339379364200037L;
	
	
	private String customerId; //客户编号
	private String customerType;		// 客户类型 : 代码
	private String customerChName;		// 客户名称
	private String developDateStr;		// 发展日期
	private String industry;		// 行业 : 代码
	private String responsiblePersonId;		// 负责人
	private String ifImportantStr;		// 是否重点对象
	private String province;		// 省市 : 代码
	private String city;		// 城市 : 代码
	private String region;		// 地区 : 代码
	private String customerParentCo;		// 客户总公司代码 : customer_id
	private String lastVisitDateStr;		// 最后拜访日期
	private String validityDateFrom;//协议开始日期
	private String validityDateTo;//协议结束日期
	private String ifEffectiveStr;		// 是否有效
	
	@ExcelField(title = "编号", type = 1, align = 1, sort = 1)
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	@ExcelField(title = "类型", type = 1, align = 1, sort = 2)
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	@ExcelField(title = "名称", type = 1, align = 1, sort = 3)
	public String getCustomerChName() {
		return customerChName;
	}
	
	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}
	
	@ExcelField(title = "发展日期", type = 1, align = 1, sort = 4)
	public String getDevelopDateStr() {
		return developDateStr;
	}
	public void setDevelopDateStr(String developDateStr) {
		this.developDateStr = developDateStr;
	}
	@ExcelField(title = "行业", type = 1, align = 1, sort = 5)
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	@ExcelField(title = "销售负责人", type = 1, align = 1, sort = 6)
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}
	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	
	@ExcelField(title = "重点对象", type = 1, align = 1, sort = 7)
	public String getIfImportantStr() {
		return ifImportantStr;
	}
	public void setIfImportantStr(String ifImportantStr) {
		this.ifImportantStr = ifImportantStr;
	}
	@ExcelField(title = "省市", type = 1, align = 1, sort = 8)
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	@ExcelField(title = "城市", type = 1, align = 1, sort = 9)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@ExcelField(title = "地区", type = 1, align = 1, sort = 10)
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@ExcelField(title = "总公司名称", type = 1, align = 1, sort = 11)
	public String getCustomerParentCo() {
		return customerParentCo;
	}
	public void setCustomerParentCo(String customerParentCo) {
		this.customerParentCo = customerParentCo;
	}
	
	
	@ExcelField(title = "协议开始时间", type = 1, align = 1, sort = 13)
	public String getValidityDateFrom() {
		return validityDateFrom;
	}
	public void setValidityDateFrom(String validityDateFrom) {
		this.validityDateFrom = validityDateFrom;
	}
	@ExcelField(title = "最后一次拜访时间", type = 1, align = 1, sort = 12)
	public String getLastVisitDateStr() {
		return lastVisitDateStr;
	}
	public void setLastVisitDateStr(String lastVisitDateStr) {
		this.lastVisitDateStr = lastVisitDateStr;
	}
	
	@ExcelField(title = "协议终止时间", type = 1, align = 1, sort = 14)
	public String getValidityDateTo() {
		return validityDateTo;
	}
	public void setValidityDateTo(String validityDateTo) {
		this.validityDateTo = validityDateTo;
	}
	
	@ExcelField(title = "无效", type = 1, align = 1, sort = 15)
	public String getIfEffectiveStr() {
		return ifEffectiveStr;
	}
	public void setIfEffectiveStr(String ifEffectiveStr) {
		this.ifEffectiveStr = ifEffectiveStr;
	}
	
}
