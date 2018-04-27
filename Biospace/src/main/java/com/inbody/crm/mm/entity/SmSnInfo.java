/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 合同出库Entity
 * @author zhanglulu
 * @version 2017-09-20
 */
@Alias("InSmSnInfo")
public class SmSnInfo extends DataEntity<SmSnInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String snNo;		// SN
	private String materialNo;		// 物料号
	private String storageId;		// 出入库履历编号 : storage_type(2)+流水(8)
	private String warehouse;		// 库房 : 代码
	private Date productionDate;		// 生产日期
	private Date entryDate;		// 入库日期
	private String machineType;		// 机器类型 : 代码
	private String status;		// 机器状态 : 代码
	private Date warrantyDateFrom;		// 保修日期起
	private Date warrantyDateTo;		// 保修到期止
	private String ifInstall;		// 是否安装
	private String ifLocked;		// 是否锁定
	private String installPersonId;		// 安装人
	private Date latestInstallDate;		// 最晚安装日期
	private Date actualInstallDate;		// 实际安装日期
	private String purchaseNo;		// 采购订单号
	private String orderNo;		// 合同号 :
	private String lineNo;		// 行号
	private String oldOrderNo;		// 原合同号
	private String oldLineNo;		// 原行号

	public SmSnInfo() {
		super();
	}

	public SmSnInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=50, message="SN长度必须介于 1 和 50 之间")
	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	
	@Length(min=1, max=50, message="物料号长度必须介于 1 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@Length(min=1, max=15, message="出入库履历编号 : storage_type(2)+流水(8)长度必须介于 1 和 15 之间")
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	
	@Length(min=1, max=2, message="库房 : 代码长度必须介于 1 和 2 之间")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="生产日期不能为空")
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
	
	@Length(min=1, max=5, message="机器类型 : 代码长度必须介于 1 和 5 之间")
	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	
	@Length(min=1, max=5, message="机器状态 : 代码长度必须介于 1 和5 之间")
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
	
	@Length(min=0, max=1, message="是否安装长度必须介于 0 和 1 之间")
	public String getIfInstall() {
		return ifInstall;
	}

	public void setIfInstall(String ifInstall) {
		this.ifInstall = ifInstall;
	}
	
	@Length(min=1, max=5, message="是否锁定 : 代码长度必须介于 1 和 5 之间")
	public String getIfLocked() {
		return ifLocked;
	}

	public void setIfLocked(String ifLocked) {
		this.ifLocked = ifLocked;
	}
	
	@Length(min=0, max=50, message="安装人长度必须介于 0 和 50 之间")
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
	
	@Length(min=0, max=50, message="采购订单号长度必须介于 0 和 50 之间")
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
	@Length(min=0, max=50, message="合同号 :长度必须介于 0 和 50 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=2, message="行号长度必须介于 0 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=0, max=50, message="原合同号长度必须介于 0 和 50 之间")
	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}
	
	@Length(min=0, max=2, message="原行号长度必须介于 0 和 2 之间")
	public String getOldLineNo() {
		return oldLineNo;
	}

	public void setOldLineNo(String oldLineNo) {
		this.oldLineNo = oldLineNo;
	}
	
}