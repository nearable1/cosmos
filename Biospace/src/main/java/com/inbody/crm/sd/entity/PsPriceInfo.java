/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import java.util.Date;

/**
 * 单表生成Entity
 * @author zhanglulu
 * @version 2017-08-25
 */
public class PsPriceInfo {
	
	private String procInsId;		// 流程编号
	private String procInsGroupid;		// 流程组号
	private String priceSystem;		// 价格体系 : 代码
	private String customerId;		// 客户代码 : &ldquo;KH&rdquo;(2)+流水(6)
	private Date validityDateFrom;		// 协议有效期起
	private Date validityDateTo;		// 协议有效期止
	private Date orderDate;		// 签订日期
	private String materialNo;		// 物料号
	private String unitPrice;		// 单价
	private String region;		// 地区 : 代码
	private String industry;		// 行业 : 代码
	private String workflowStatus;		// 工作流状态 : 代码
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getProcInsGroupid() {
		return procInsGroupid;
	}
	public void setProcInsGroupid(String procInsGroupid) {
		this.procInsGroupid = procInsGroupid;
	}
	public String getPriceSystem() {
		return priceSystem;
	}
	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Date getValidityDateFrom() {
		return validityDateFrom;
	}
	public void setValidityDateFrom(Date validityDateFrom) {
		this.validityDateFrom = validityDateFrom;
	}
	public Date getValidityDateTo() {
		return validityDateTo;
	}
	public void setValidityDateTo(Date validityDateTo) {
		this.validityDateTo = validityDateTo;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
}