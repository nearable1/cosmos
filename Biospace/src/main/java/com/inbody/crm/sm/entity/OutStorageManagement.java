package com.inbody.crm.sm.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.ActEntity;

public class OutStorageManagement extends ActEntity<OutStorageManagement>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String date;
	
	private String newSnNo;
	
	private String storageType="22";
	
	private String workflowStatus;
			
	private String customerId;
	
	private String industry;
		
	private String processDate;
	
	private String newRemarks;
	
	private String appId;
	
	private String purchaseNo;
	
	private String orderDetail;
	
	private String orderNo;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String lineNo;

	private String materialNo;
	
	private String extendDateTo;
	
	private String extendReason;
		
	private String lendingDateFrom;
	
	private String lendingDateTo;
	
	private String contacts;

	private String telephone;

	private String address;

	private String snNo;

	private String accessoriesRemarks;

	private String procInsId;

	private String num;

	//private String inStockStatus;
		
	private String warehouse;
	
	
	private String toWarehouse;
	
	private String lendingType;
	
	private String responsiblePerson;
	
	private String inStockStatus;
	
	
	
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getNewSnNo() {
		return newSnNo;
	}

	public void setNewSnNo(String newSnNo) {
		this.newSnNo = newSnNo;
	}

	public String getToWarehouse() {
		return toWarehouse;
	}

	public void setToWarehouse(String toWarehouse) {
		this.toWarehouse = toWarehouse;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLendingDateTo() {
		return lendingDateTo;
	}

	public void setLendingDateTo(String lendingDateTo) {
		this.lendingDateTo = lendingDateTo;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getExtendDateTo() {
		return extendDateTo;
	}

	public void setExtendDateTo(String extendDateTo) {
		this.extendDateTo = extendDateTo;
	}

	public String getExtendReason() {
		return extendReason;
	}

	public void setExtendReason(String extendReason) {
		this.extendReason = extendReason;
	}

	public String getLendingDateFrom() {
		return lendingDateFrom;
	}

	public void setLendingDateFrom(String lendingDateFrom) {
		this.lendingDateFrom = lendingDateFrom;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getAccessoriesRemarks() {
		return accessoriesRemarks;
	}

	public void setAccessoriesRemarks(String accessoriesRemarks) {
		this.accessoriesRemarks = accessoriesRemarks;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
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

	public String getLendingType() {
		return lendingType;
	}

	public void setLendingType(String lendingType) {
		this.lendingType = lendingType;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getInStockStatus() {
		return inStockStatus;
	}

	public void setInStockStatus(String inStockStatus) {
		this.inStockStatus = inStockStatus;
	}

	

	private List<Map<String, String>> smStorageAppDtl = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getSmStorageAppDtl() {
		return smStorageAppDtl;
	}

	public void setSmStorageAppDtl(List<Map<String, String>> smStorageAppDtl) {
		this.smStorageAppDtl = smStorageAppDtl;
	}
	private List<String> selectedList = Lists.newArrayList();	//list页面操作中选中的信息

	public List<String> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<String> selectedList) {
		this.selectedList = selectedList;
	}


}
