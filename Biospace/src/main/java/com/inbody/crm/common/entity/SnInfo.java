/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * sn infoEntity
 * @author sn info
 * @version 2017-10-09
 */
public class SnInfo extends DataEntity<SnInfo> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String snNo;		// SN
	private String materialNo;		// 物料号
	private String storageId;		// 出入库履历编号
	private String warehouse;		// 库房 : 代码
	private Date productionDate;		// 生产日期
	private Date entryDate;		// 入库日期
	private String machineType;		// 机器类型 : 代码
	private String status;		// 机器状态 : 代码
	private Date warrantyDateFrom;		// 保修日期起
	private Date warrantyDateTo;		// 保修到期止
	private String ifInstall;		// 是否安装
	private String installPersonId;		// 安装人
	private Date latestInstallDate;		// 最晚安装日期
	private Date actualInstallDate;		// 实际安装日期
	private String purchaseNo;		// 采购订单号
	private String orderNo;		// 合同号 :
	private String lineNo;		// 行号
	private String oldOrderNo;		// 原合同号
	private String oldLineNo;		// 原行号
	
	private String machineTypeName;
	private String statusValue;
	private String productionDateValue;
	private String model;       // 型号

	private String snVersion;		// SN版本号
	private String lbSnNo;		// LB_SN_NO
	
	public SnInfo() {
		super();
	}

	public SnInfo(String id){
		super(id);
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWarrantyDateFrom() {
		return warrantyDateFrom;
	}

	public void setWarrantyDateFrom(Date warrantyDateFrom) {
		this.warrantyDateFrom = warrantyDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWarrantyDateTo() {
		return warrantyDateTo;
	}

	public void setWarrantyDateTo(Date warrantyDateTo) {
		this.warrantyDateTo = warrantyDateTo;
	}
	
	public String getIfInstall() {
		return ifInstall;
	}

	public void setIfInstall(String ifInstall) {
		this.ifInstall = ifInstall;
	}
	
	public String getInstallPersonId() {
		return installPersonId;
	}

	public void setInstallPersonId(String installPersonId) {
		this.installPersonId = installPersonId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLatestInstallDate() {
		return latestInstallDate;
	}

	public void setLatestInstallDate(Date latestInstallDate) {
		this.latestInstallDate = latestInstallDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getActualInstallDate() {
		return actualInstallDate;
	}

	public void setActualInstallDate(Date actualInstallDate) {
		this.actualInstallDate = actualInstallDate;
	}
	
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}
	
	public String getOldLineNo() {
		return oldLineNo;
	}

	public void setOldLineNo(String oldLineNo) {
		this.oldLineNo = oldLineNo;
	}

	public String getMachineTypeName() {
		return machineTypeName;
	}

	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getProductionDateValue() {
		return productionDateValue;
	}

	public void setProductionDateValue(String productionDateValue) {
		this.productionDateValue = productionDateValue;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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
	
}