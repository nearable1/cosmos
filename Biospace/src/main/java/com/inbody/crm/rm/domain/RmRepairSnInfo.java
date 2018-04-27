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
public class RmRepairSnInfo extends DataEntity<RmRepairSnInfo> {

	private static final long serialVersionUID = -1614795382744562112L;

	/** sn */
	private String snNo;

	/** 型号 */
	private String model;

	/** 生产日期 */
	private Date productionDate;

	/** 安装日期 */
	private Date actualInstallDate;

	/** 保修到日期 */
	private Date warrantyDateTo;

	/** 签约方 */
	private String customerName;

	/** 最终客户 */
	private String endCustomerName;

	/** 销售方式 */
	private String priceSystem;

	/** 组 */
	private String organizeName;

	private String lbSnNo;		// LB

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Date getActualInstallDate() {
		return actualInstallDate;
	}

	public void setActualInstallDate(Date actualInstallDate) {
		this.actualInstallDate = actualInstallDate;
	}

	public Date getWarrantyDateTo() {
		return warrantyDateTo;
	}

	public void setWarrantyDateTo(Date warrantyDateTo) {
		this.warrantyDateTo = warrantyDateTo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEndCustomerName() {
		return endCustomerName;
	}

	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
	}

	public String getPriceSystem() {
		return priceSystem;
	}

	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

	public String getLbSnNo() {
		return lbSnNo;
	}

	public void setLbSnNo(String lbSnNo) {
		this.lbSnNo = lbSnNo;
	}
}