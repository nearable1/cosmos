/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import java.util.Date;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 维修查询维修信息
 * 
 * @author yangj
 * @version 2017-08-16
 */
public class RmRepairSnSearch extends DataEntity<RmRepairSnSearch> {

	private static final long serialVersionUID = -1614795382744562112L;

	/** 型号 */
	private String model;

	/** sn */
	private String snNo;
	
	private String snVersion;
	
	private String lbSnNo;
	
	/** 单位名称:报修客户名称 */
	private String repairCusName;

	/** 最终客户 */
	private String endCustomerName;

	/** 电话 */
	private String telephone;

	/** 签约方名称 */
	private String customerChName;

	/** 合同编号 */
	private String orderNo;

	/** 生产日期 */
	private Date productionDate;

	/** 保修到日期 */
	private Date warrantyDateTo;

	/** 维修编号 */
	private String repairNo;

	/** 问题描述 */
	private String issueDescribe;

	/** 机器状态 */
	private String macStatus;

	/** 检测日期 */
	private Date testingDate;

	/** 查询类型 */
	private String searchType;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getSnVersion() {
		return snVersion;
	}

	public void setSnVersion(String snVersion) {
		this.snVersion = snVersion;
	}

	public String getLbSnNo() {
		return lbSnNo;
	}

	public void setLbSnNo(String lbSnNo) {
		this.lbSnNo = lbSnNo;
	}

	public String getRepairCusName() {
		return repairCusName;
	}

	public void setRepairCusName(String repairCusName) {
		this.repairCusName = repairCusName;
	}

	public String getEndCustomerName() {
		return endCustomerName;
	}

	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCustomerChName() {
		return customerChName;
	}

	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Date getWarrantyDateTo() {
		return warrantyDateTo;
	}

	public void setWarrantyDateTo(Date warrantyDateTo) {
		this.warrantyDateTo = warrantyDateTo;
	}

	public String getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}

	public String getIssueDescribe() {
		return issueDescribe;
	}

	public void setIssueDescribe(String issueDescribe) {
		this.issueDescribe = issueDescribe;
	}

	public String getMacStatus() {
		return macStatus;
	}

	public void setMacStatus(String macStatus) {
		this.macStatus = macStatus;
	}

	public Date getTestingDate() {
		return testingDate;
	}

	public void setTestingDate(Date testingDate) {
		this.testingDate = testingDate;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

}