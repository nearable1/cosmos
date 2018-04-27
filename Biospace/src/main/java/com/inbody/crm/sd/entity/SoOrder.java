/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.ActEntity;

/**
 * 合同信息录入Entity
 * @author zhanglulu
 * @version 2017-08-22
 */
public class SoOrder extends ActEntity<SoOrder> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String orderNo;		// 合同号 :
	private String businessOppNo;		// 商机号 :
	private String orderType;		// 合同类型 : 代码
	private Date orderDate;		// 订立日期
	private String priceSystem;		// 价格体系 : 代码
	private String receiptType;		// 收款方式 : 代码
	private String receiptRemarks;		// 收款备注
	private String paymaentCon;		// 支付条件
	private String conditionRemarks;		// 条件备注
	private String employeeNo;		// 员工号
	private String employeeName;		// 员工名
	private String city;		// 城市 : 代码
	private String customerId;		// 客户代码 : &ldquo;KH&rdquo;(2)+流水(6)
	private String customerChName; // 客户名
	private String currency;		// 币种 : 代码
	private String exchangeRate;		// 汇率
	private String commission;		// 佣金
	private String workflowStatus;		// 合同状态 : 代码
	private String receiveStatus;		// 收款状态 : 代码
	private String invoiceStatus;		// 开票状态 : 代码
	private String deliverStatus;		// 发货状态 : 代码
	private String deliverWorkflowStatus;		// 发货申请状态 : 代码
	private String newRemarks;		// 备注说明
	private String ifEffective;		// 是否有效
	private List<SoOrderDtl> soOrderDtlList = Lists.newArrayList();		// 合同明细
	private List<SoGatheringInfo> soGatheringInfoList = Lists.newArrayList();		// 收款明细
	private ImInvoice imInvoice;		// 发票管理
	private List<ImInvoiceDtl> imInvoiceDtlList = Lists.newArrayList();		// 发票明细
	
	private String dataType;	// 画面显示数据类型（order：合同；invoice：发票）
	
	private BigDecimal orderDtlTotalAmount;	// 合同明细总金额
	private BigDecimal gatheringTotalAmount;	// 收款总金额
	private BigDecimal imInvoiceTotalAmount;	// 开票总金额
	
	public BigDecimal getOrderDtlTotalAmount() {
		return orderDtlTotalAmount;
	}

	public void setOrderDtlTotalAmount(BigDecimal orderDtlTotalAmount) {
		this.orderDtlTotalAmount = orderDtlTotalAmount;
	}

	public BigDecimal getGatheringTotalAmount() {
		return gatheringTotalAmount;
	}

	public void setGatheringTotalAmount(BigDecimal gatheringTotalAmount) {
		this.gatheringTotalAmount = gatheringTotalAmount;
	}

	public BigDecimal getImInvoiceTotalAmount() {
		return imInvoiceTotalAmount;
	}

	public void setImInvoiceTotalAmount(BigDecimal imInvoiceTotalAmount) {
		this.imInvoiceTotalAmount = imInvoiceTotalAmount;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	private String opt;
	
	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public SoOrder() {
		super();
	}

	public SoOrder(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=50, message="合同号 :长度必须介于 1 和 50 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=15, message="商机号 :长度必须介于 0 和 15 之间")
	public String getBusinessOppNo() {
		return businessOppNo;
	}

	public void setBusinessOppNo(String businessOppNo) {
		this.businessOppNo = businessOppNo;
	}
	
	@Length(min=1, max=2, message="合同类型 : 代码长度必须介于 0 和 5 之间")
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@Length(min=0, max=2, message="价格体系 : 代码长度必须介于 0 和 5 之间")
	public String getPriceSystem() {
		return priceSystem;
	}

	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}
	
	@Length(min=0, max=2, message="收款方式 : 代码长度必须介于 0 和 5 之间")
	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
	@Length(min=0, max=300, message="收款备注长度必须介于 0 和 300 之间")
	public String getReceiptRemarks() {
		return receiptRemarks;
	}

	public void setReceiptRemarks(String receiptRemarks) {
		this.receiptRemarks = receiptRemarks;
	}
	
	@Length(min=0, max=2, message="支付条件: 代码长度必须介于 0 和 5 之间")
	public String getPaymaentCon() {
		return paymaentCon;
	}

	public void setPaymaentCon(String paymaentCon) {
		this.paymaentCon = paymaentCon;
	}
	
	@Length(min=0, max=300, message="条件备注长度必须介于 0 和 300 之间")
	public String getConditionRemarks() {
		return conditionRemarks;
	}

	public void setConditionRemarks(String conditionRemarks) {
		this.conditionRemarks = conditionRemarks;
	}
	
	@Length(min=0, max=50, message="员工号长度必须介于 0 和 50 之间")
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Length(min=0, max=10, message="城市 : 代码长度必须介于 0 和 10 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=15, message="客户代码 : &ldquo;KH&rdquo;(2)+流水(6)长度必须介于 0 和 15 之间")
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

	@Length(min=0, max=2, message="币种 : 代码长度必须介于 0 和 5 之间")
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
	
	@Length(min=1, max=2, message="合同状态 : 代码长度必须介于 0 和 5 之间")
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Length(min=1, max=2, message="收款状态 : 代码长度必须介于 0 和 5 之间")
	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
	
	@Length(min=1, max=2, message="开票状态 : 代码长度必须介于 0 和 5 之间")
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	@Length(min=1, max=2, message="发货状态 : 代码长度必须介于 0 和 5 之间")
	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	
	public String getDeliverWorkflowStatus() {
		return deliverWorkflowStatus;
	}

	public void setDeliverWorkflowStatus(String deliverWorkflowStatus) {
		this.deliverWorkflowStatus = deliverWorkflowStatus;
	}

	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
	@Length(min=1, max=1, message="是否有效长度必须介于 1 和 1 之间")
	public String getIfEffective() {
		return ifEffective;
	}

	public void setIfEffective(String ifEffective) {
		this.ifEffective = ifEffective;
	}
	
	public List<SoOrderDtl> getSoOrderDtlList() {
		return soOrderDtlList;
	}

	public void setSoOrderDtlList(List<SoOrderDtl> soOrderDtlList) {
		this.soOrderDtlList = soOrderDtlList;
	}

	public List<SoGatheringInfo> getSoGatheringInfoList() {
		return soGatheringInfoList;
	}

	public void setSoGatheringInfoList(List<SoGatheringInfo> soGatheringInfoList) {
		this.soGatheringInfoList = soGatheringInfoList;
	}

	public ImInvoice getImInvoice() {
		return imInvoice;
	}

	public void setImInvoice(ImInvoice imInvoice) {
		this.imInvoice = imInvoice;
	}

	public List<ImInvoiceDtl> getImInvoiceDtlList() {
		return imInvoiceDtlList;
	}

	public void setImInvoiceDtlList(List<ImInvoiceDtl> imInvoiceDtlList) {
		this.imInvoiceDtlList = imInvoiceDtlList;
	}
}