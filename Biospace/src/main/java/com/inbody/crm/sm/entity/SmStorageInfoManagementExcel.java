package com.inbody.crm.sm.entity;

import com.inbody.crm.common.persistence.ActEntity;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.annotation.ExcelField;
import com.inbody.crm.modules.sys.utils.DictUtils;

public class SmStorageInfoManagementExcel extends ActEntity<SmStorageInfoManagementExcel>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * 记录人
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
//	private String borrowingPurpose;
	
	/*
	 * 借用信息
	 */
	private String customerName;
	
	/*
	 * SN更新时间
	 */
	private String updateDateSN;
	
	/*
	 * 安装时间
	 */
	private String actualInstallDateStr;
	
	/*
	 * 记录人
	 */
	private String noteTaker;
	
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@ExcelField(title = "出入库编号", type = 1, align = 1, sort = 1)
	public String getStorageId() {
		return storageId;
	}
	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	
	@ExcelField(title = "区分", type = 1, align = 1, sort = 2)
	public String getDistinguish() {
		return distinguish;
	}
	public void setDistinguish(String distinguish) {
		this.distinguish = distinguish;
	}
	
	
	@ExcelField(title = "物料号", type = 1, align = 1, sort = 3)
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@ExcelField(title = "物料名称", type = 1, align = 1, sort = 4)
	public String getMaterielName() {
		return materielName;
	}
	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}
	
	@ExcelField(title = "物料类型", dictType="DM0058", type = 1, align = 1, sort = 5)
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	
	@ExcelField(title = "S/N", type = 1, align = 1, sort = 6)
	public String getSnNo() {
		return snNo;
	}
	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	
	@ExcelField(title = "生产日期", type = 1, align = 1, sort = 7)
	public String getProductionDateStr() {
		return productionDateStr;
	}
	public void setProductionDateStr(String productionDateStr) {
		this.productionDateStr = productionDateStr;
	}
	
	@ExcelField(title = "数量", type = 1, align = 3, sort = 8)
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@ExcelField(title = "记录日期", type = 1, align = 1, sort = 9)
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	
	@ExcelField(title = "记录人", type = 1, align = 1, sort = 10)
	public String getNoteTaker() {
		return noteTaker;
	}
	public void setNoteTaker(String noteTaker) {
		this.noteTaker = noteTaker;
	}
	
	@ExcelField(title = "库房", dictType="DM0050", type = 1, align = 1, sort = 11)
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	
	@ExcelField(title = "出入库分类", dictType="DM0035", type = 1, align = 1, sort = 12)
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	
	@ExcelField(title = "合同/报价单编号", type = 1, align = 1, sort = 13)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title = "采购订单编号", type = 1, align = 1, sort = 14)
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
	@ExcelField(title = "联系人", type = 1, align = 1, sort = 15)
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	@ExcelField(title = "联系方式", type = 1, align = 1, sort = 16)
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@ExcelField(title = "收货地址", type = 1, align = 1, sort = 17)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title = "借用目的", dictType="DM0036", type = 1, align = 1, sort = 18)
	public String getLendingType() {
		return lendingType;
	}
	public void setLendingType(String lendingType) {
		this.lendingType = lendingType;
	}
	@ExcelField(title = "借用信息", value="industryForExcel", type = 1, align = 1, sort = 19)
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@ExcelField(title = "借出日期", type = 1, align = 1, sort = 20)
	public String getLendingDateFrom() {
		return lendingDateFrom;
	}
	public void setLendingDateFrom(String lendingDateFrom) {
		this.lendingDateFrom = lendingDateFrom;
	}
	@ExcelField(title = "借出到期日", type = 1, align = 1, sort = 21)
	public String getLendingDateTo() {
		return lendingDateTo;
	}
	public void setLendingDateTo(String lendingDateTo) {
		this.lendingDateTo = lendingDateTo;
	}
	
	@ExcelField(title = "是否需要安装", dictType="yes_no", type = 1, align = 1, sort = 22)
	public String getIfInstall() {
		return ifInstall;
	}
	public void setIfInstall(String ifInstall) {
		this.ifInstall = ifInstall;
	}
	
	@ExcelField(title = "最晚安装日期", type = 1, align = 1, sort = 23)
	public String getLatestInstallDateStr() {
		return latestInstallDateStr;
	}
	public void setLatestInstallDateStr(String latestInstallDateStr) {
		this.latestInstallDateStr = latestInstallDateStr;
	}
	
	
	@ExcelField(title = "安装日期", type = 1, align = 1, sort = 24)
	public String getActualInstallDateStr() {
		return actualInstallDateStr;
	}
	public void setActualInstallDateStr(String actualInstallDateStr) {
		this.actualInstallDateStr = actualInstallDateStr;
	}
	
	public void setInstallPersonId(String installPersonId) {
		this.installPersonId = installPersonId;
	}
	@ExcelField(title = "安装人", type = 1, align = 1, sort = 25)
	public String getInstallPersonId() {
		return installPersonId;
	}
	
	public String getStorageApplyId() {
		return storageApplyId;
	}
	public void setStorageApplyId(String storageApplyId) {
		this.storageApplyId = storageApplyId;
	}
	
	
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
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
	
	public String getAccessoriesRemarks() {
		return accessoriesRemarks;
	}
	public void setAccessoriesRemarks(String accessoriesRemarks) {
		this.accessoriesRemarks = accessoriesRemarks;
	}
	
	
	public String getExtendDateTo() {
		return extendDateTo;
	}
	public void setExtendDateTo(String extendDateTo) {
		this.extendDateTo = extendDateTo;
	}
	
	
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	
	
	public String getExceedLatestDate() {
		return ExceedLatestDate;
	}
	public void setExceedLatestDate(String exceedLatestDate) {
		ExceedLatestDate = exceedLatestDate;
	}
	
	public String getUpdateDateSN() {
		return updateDateSN;
	}
	public void setUpdateDateSN(String updateDateSN) {
		this.updateDateSN = updateDateSN;
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
	
}
