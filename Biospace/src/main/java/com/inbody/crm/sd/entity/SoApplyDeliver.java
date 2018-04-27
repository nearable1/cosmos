/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.ActEntity;

/**
 * 发货申请Entity
 * @author zhanglulu
 * @version 2017-09-11
 */
public class SoApplyDeliver extends ActEntity<SoApplyDeliver> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String orderNo;		// 合同号 :
	private Date expectDate;		// 预定日期
	private String workflowStatus;		// 工作流状态 : 代码
	private String ifOut;		// 是否出库 : 代码

	private String orderId;		// 合同ID
	private String customerName;		// 签约方
	private String receiveStatus;		// 收款状态
	private String invoiceStatus;		// 开票状态
	private String opt;		// 画面操作

	private List<SoApplyDeliverDtl> soApplyDeliverDtlList = Lists.newArrayList();		// 发货申请明细
	
	public SoApplyDeliver() {
		super();
	}

	public SoApplyDeliver(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}
	
	@Length(min=0, max=5, message="工作流状态 : 代码长度必须介于 0 和 5 之间")
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Length(min=0, max=2, message="是否出库 : 代码长度必须介于 0 和 2 之间")
	public String getIfOut() {
		return ifOut;
	}

	public void setIfOut(String ifOut) {
		this.ifOut = ifOut;
	}

	public List<SoApplyDeliverDtl> getSoApplyDeliverDtlList() {
		return soApplyDeliverDtlList;
	}

	public void setSoApplyDeliverDtlList(
			List<SoApplyDeliverDtl> soApplyDeliverDtlList) {
		this.soApplyDeliverDtlList = soApplyDeliverDtlList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}
	
}