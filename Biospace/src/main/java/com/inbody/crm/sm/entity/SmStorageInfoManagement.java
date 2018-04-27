package com.inbody.crm.sm.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inbody.crm.common.persistence.ActEntity;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.utils.DictUtils;

public class SmStorageInfoManagement extends ActEntity<SmStorageInfoManagement>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String storageId;
	private String storageApplyId ;
	private String storageType;
	private String purchaseNo;
	private String orderNo;
	private String lineNo;
	private String snNo;
	private String materialNo;
	private String num;
	private String warehouse;
	private String processDate;
	private String responsiblePersonId;
	private String newRemarks;
	private String lendingType;
	private String industry;
	private String address;
	private String contactsName;
	private String telephone;
	private String accessoriesRemarks;
	private String lendingDateFrom;
	private String lendingDateTo;
	private String extendDateTo;
	private String workflowStatus;
	private String materialType;
	
	
	 // 出入库记录使用字段
	/*
	 * 生产日期
	 */
	private String productionDateStr; 
	
	/*
	 * 安装人
	 */
	private String installPersonId;
	
	/*
	 * 是否需要安装
	 */
	private String ifInstall;
	
	/*
	 * 附件
	 */
	private String attachments;
	
	/*
	 * 区分
	 */
	private String distinguish;
	
	/*
	 * 最晚安装日期
	 */
	private String latestInstallDateStr;
	
	/*
	 * 物料名称
	 */
	private String materielName;
	
	/*
	 * 超过最晚安装日期
	 */
	private String ExceedLatestDate;
	
	
	/*
	 * 借用目的
	 */
	private String borrowingPurpose;
	
	/*
	 * 客户信息
	 */
	private String customerName;
	
	/*
	 * 安装时间	
	 */
	private String actualInstallDateStr;
	
	/*
	 * SN更新时间
	 */
	private String SNUpdateDate;
	
	/**
	 * 实际SN
	 */
	private String actualSN;
	
	/*
	 * 记录人
	 */
	private String noteTaker;
	
	/*
	 *记录日期起
	 */
	private String processDateFrom;
	
	/**
	 * 记录日期止
	 */
	private String processDateTo;
	
	/**
	 * IF_VIRTUAL_SN
	 */
	private Integer ifvirtualsn;
	
	private String userId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
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
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}
	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	public String getNewRemarks() {
		return newRemarks;
	}
	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	public String getLendingType() {
		return lendingType;
	}
	public void setLendingType(String lendingType) {
		this.lendingType = lendingType;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAccessoriesRemarks() {
		return accessoriesRemarks;
	}
	public void setAccessoriesRemarks(String accessoriesRemarks) {
		this.accessoriesRemarks = accessoriesRemarks;
	}
	public String getLendingDateFrom() {
		return lendingDateFrom;
	}
	public void setLendingDateFrom(String lendingDateFrom) {
		this.lendingDateFrom = lendingDateFrom;
	}
	public String getLendingDateTo() {
		return lendingDateTo;
	}
	public void setLendingDateTo(String lendingDateTo) {
		this.lendingDateTo = lendingDateTo;
	}
	public String getExtendDateTo() {
		return extendDateTo;
	}
	public void setExtendDateTo(String extendDateTo) {
		this.extendDateTo = extendDateTo;
	}
	
	public String getProductionDateStr() {
		return productionDateStr;
	}
	public void setProductionDateStr(String productionDateStr) {
		this.productionDateStr = productionDateStr;
	}
	public String getInstallPersonId() {
		return installPersonId;
	}
	public void setInstallPersonId(String installPersonId) {
		this.installPersonId = installPersonId;
	}
	
	
	public String getIfInstall() {
		return ifInstall;
	}
	public void setIfInstall(String ifInstall) {
		this.ifInstall = ifInstall;
	}
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	public String getDistinguish() {
		return distinguish;
	}
	public void setDistinguish(String distinguish) {
		this.distinguish = distinguish;
	}
	public String getLatestInstallDateStr() {
		return latestInstallDateStr;
	}
	public void setLatestInstallDateStr(String latestInstallDateStr) {
		this.latestInstallDateStr = latestInstallDateStr;
	}
	public String getMaterielName() {
		return materielName;
	}
	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}
	public String getExceedLatestDate() {
		return ExceedLatestDate;
	}
	public void setExceedLatestDate(String exceedLatestDate) {
		ExceedLatestDate = exceedLatestDate;
	}
	public String getBorrowingPurpose() {
		return borrowingPurpose;
	}
	public void setBorrowingPurpose(String borrowingPurpose) {
		this.borrowingPurpose = borrowingPurpose;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getActualInstallDateStr() {
		return actualInstallDateStr;
	}
	public void setActualInstallDateStr(String actualInstallDateStr) {
		this.actualInstallDateStr = actualInstallDateStr;
	}
	public String getNoteTaker() {
		return noteTaker;
	}
	public void setNoteTaker(String noteTaker) {
		this.noteTaker = noteTaker;
	}
	public String getSNUpdateDate() {
		return SNUpdateDate;
	}
	public void setSNUpdateDate(String sNUpdateDate) {
		SNUpdateDate = sNUpdateDate;
	}
	public String getActualSN() {
		return actualSN;
	}
	public void setActualSN(String actualSN) {
		this.actualSN = actualSN;
	}
	public String getProcessDateFrom() {
		return processDateFrom;
	}
	public void setProcessDateFrom(String processDateFrom) {
		this.processDateFrom = processDateFrom;
	}
	public String getProcessDateTo() {
		return processDateTo;
	}
	public void setProcessDateTo(String processDateTo) {
		this.processDateTo = processDateTo;
	}
	public Integer getIfvirtualsn() {
		return ifvirtualsn;
	}
	public void setIfvirtualsn(Integer ifvirtualsn) {
		this.ifvirtualsn = ifvirtualsn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	public String getIndustryForExcel() {
		String industryForExcel = null;
		if (!StringUtils.isEmptyNull(this.industry)) {
			industryForExcel = DictUtils.getDictLabel(this.industry, "DM0002", "");
		}
		
		if (!StringUtils.isEmptyNull(this.customerName)) {
			if (!StringUtils.isEmptyNull(industryForExcel)) {
				industryForExcel = industryForExcel + " " + this.customerName;
			} else {
				industryForExcel = this.customerName;
			}
		}
		return industryForExcel;
	}
	private List<Map<String, String>> smStorageAppDtl = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getSmStorageAppDtl() {
		return smStorageAppDtl;
	}

	public void setSmStorageAppDtl(List<Map<String, String>> smStorageAppDtl) {
		this.smStorageAppDtl = smStorageAppDtl;
	}
}
