package com.inbody.crm.sm.entity;

import com.inbody.crm.common.persistence.ActEntity;

public class SmWarehouseInfoManagement extends ActEntity<SmWarehouseInfoManagement>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String id;
	private String appId;
	private String newId;
	private String snNo;
	private String workflowStatus;
	private String storageType;
	private String materialNo;
	private String materialName;
	private String warehouse;
	private String storageId;
	private String inStockStatus;
	private String num;
	private String occupationNo;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getNewId() {
		return newId;
	}
	public void setNewId(String newId) {
		this.newId = newId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getStorageId() {
		return storageId;
	}
	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	public String getInStockStatus() {
		return inStockStatus;
	}
	public void setInStockStatus(String inStockStatus) {
		this.inStockStatus = inStockStatus;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getOccupationNo() {
		return occupationNo;
	}
	public void setOccupationNo(String occupationNo) {
		this.occupationNo = occupationNo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


}
