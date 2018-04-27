/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author NSSOL
 * @version 2017-08-10
 */
public class SmStorageInfo extends DataEntity<SmStorageInfo> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String storageId;		// 出入库履历编号 : storage_type(2)+流水(8)
	private String storageApplyId;		// 出入库申请编号 : sm_storage_app_dtl.storage_apply_id = sm_storage_app.id
	private String storageType;		// 出入库类型 : 代码
	private String purchaseNo;		// 采购订单号
	private String orderNo;		// 合同号 :
	private String lineNo;		// 行号
	private String snNo;		// SN
	private String materialNo;		// 物料号
	private Integer num;		// 数量
	private String warehouse;		// 库房 : 代码
	private Date processDate;		// 处理日期
	private String responsiblePersonId;		// 负责人
	private String newRemarks;		// 备注说明
	private String lendingType;		// 借用类型 : 代码
	private String industry;		// 行业 : 代码
	private String address;		// 地址
	private String contactsName;		// 联系人
	private String telephone;		// 电话
	private String accessoriesRemarks;		// 配件备注 : 借出相关配件
	private Date lendingDateFrom;		// 借出日期起
	private Date lendingDateTo;		// 借出日期止
	private Date extendDateTo;		// 延长到期日
	
	public SmStorageInfo() {
		super();
	}

	public SmStorageInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="出入库履历编号 : storage_type(2)+流水(8)长度必须介于 1 和 15 之间")
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getStorageApplyId() {
		return storageApplyId;
	}

	public void setStorageApplyId(String storageApplyId) {
		this.storageApplyId = storageApplyId;
	}
	
	@Length(min=1, max=2, message="出入库类型 : 代码长度必须介于 1 和 2 之间")
	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
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
	
	@Length(min=0, max=50, message="SN长度必须介于 0 和 50 之间")
	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	
	@Length(min=0, max=50, message="物料号长度必须介于 0 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Length(min=0, max=2, message="库房 : 代码长度必须介于 0 和 2 之间")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	
	@Length(min=0, max=50, message="负责人长度必须介于 0 和 50 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
	@Length(min=0, max=2, message="借用类型 : 代码长度必须介于 0 和 2 之间")
	public String getLendingType() {
		return lendingType;
	}

	public void setLendingType(String lendingType) {
		this.lendingType = lendingType;
	}
	
	@Length(min=0, max=2, message="行业 : 代码长度必须介于 0 和 2 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="联系人长度必须介于 0 和 100 之间")
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=300, message="配件备注 : 借出相关配件长度必须介于 0 和 300 之间")
	public String getAccessoriesRemarks() {
		return accessoriesRemarks;
	}

	public void setAccessoriesRemarks(String accessoriesRemarks) {
		this.accessoriesRemarks = accessoriesRemarks;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="借出日期起不能为空")
	public Date getLendingDateFrom() {
		return lendingDateFrom;
	}

	public void setLendingDateFrom(Date lendingDateFrom) {
		this.lendingDateFrom = lendingDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLendingDateTo() {
		return lendingDateTo;
	}

	public void setLendingDateTo(Date lendingDateTo) {
		this.lendingDateTo = lendingDateTo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExtendDateTo() {
		return extendDateTo;
	}

	public void setExtendDateTo(Date extendDateTo) {
		this.extendDateTo = extendDateTo;
	}
	
}