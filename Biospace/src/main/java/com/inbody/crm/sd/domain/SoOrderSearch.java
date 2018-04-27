/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import java.util.Date;
//import java.util.List;
//
//import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 合同查询结果Entity
 * 
 * @author zhanglulu
 * @version 2017-08-30
 */
public class SoOrderSearch extends DataEntity<SoOrderSearch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2721929598366301456L;
	
	private String orderNo; // 合同编号
	private String materialName; // 物料名称
	private String orderDateBegin; // 订立日期开始
	private String orderDateEnd; // 订立日期结束
	private String orderType; // 合同类型
	private String region; // 地区
	private String employeeNo; // 业务员
	private String endCustomerId; // 最终客户
	private String customerId; // 签约方
	private String workflowStatus; // 合同状态
	private String receiveStatus; // 收款状态
	private String invoiceStatus; // 开票状态
	private String deliverStatus; // 发货状态
	
	private String procInsId; // 流程编号
	private String orderDate; // 订立日期
	private String priceSystem; // 销售方式
	private String employeeName; // 业务员名
	private String customerChName; // 签约方名
	private String cityName; // 签约地名
	private String currency;		// 币种 : 代码
	private String exchangeRate;		// 汇率
	private String commission;		// 佣金
	private String paymaentCon;		// 支付条件: DM0057
	private String conditionRemarks;		// 条件备注

	private String endCustomerName; // 最终客户名称
	private String industry;	// 行业
	private String customerDiff;	// 具体分类
	private String customerSegmentation;		// 客户细分
	private String num;		// 数量
	private String totalAmount;		// 总金额
	private Date invoiceDate;		// 开票日期
	private String responsiblePersonName;		// 负责人
	private String warrantyPeriod;		// 质保期 : 单位-年
	private String model;		// 物料型号
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getEndCustomerName() {
		return endCustomerName;
	}
	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
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
	public String getCustomerSegmentation() {
		return customerSegmentation;
	}
	public void setCustomerSegmentation(String customerSegmentation) {
		this.customerSegmentation = customerSegmentation;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}
	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}
	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}
	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getOrderDateBegin() {
		return orderDateBegin;
	}
	public void setOrderDateBegin(String orderDateBegin) {
		this.orderDateBegin = orderDateBegin;
	}
	public String getOrderDateEnd() {
		return orderDateEnd;
	}
	public void setOrderDateEnd(String orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getEndCustomerId() {
		return endCustomerId;
	}
	public void setEndCustomerId(String endCustomerId) {
		this.endCustomerId = endCustomerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public String getDeliverStatus() {
		return deliverStatus;
	}
	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getPriceSystem() {
		return priceSystem;
	}
	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getCustomerChName() {
		return customerChName;
	}
	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getPaymaentCon() {
		return paymaentCon;
	}
	public void setPaymaentCon(String paymaentCon) {
		this.paymaentCon = paymaentCon;
	}
	public String getConditionRemarks() {
		return conditionRemarks;
	}
	public void setConditionRemarks(String conditionRemarks) {
		this.conditionRemarks = conditionRemarks;
	}
}