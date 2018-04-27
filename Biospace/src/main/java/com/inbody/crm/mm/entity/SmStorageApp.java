/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.entity;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.ActEntity;

/**
 * 其他入库申请_出入库申请Entity
 * @author zhanglulu
 * @version 2017-10-23
 */
@Alias("InSmStorageApp")
public class SmStorageApp extends ActEntity<SmStorageApp> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String storageType;		// 出入库类型 : 代码
	private String workflowStatus;		// 工作流状态 : 代码
	private String responsiblePersonId;		// 负责人
	private String lendingType;		// 借用类型
	private String customerName;		// 客户
	private String industry;		// 行业
	private Date lendingDateFrom;		// 借出日期起
	private Date processDate;		// 处理日期
	private String newRemarks;		// 备注说明
	
	private List<SmStorageAppDtl> smStorageAppDtlList = Lists.newArrayList();		// 出入库申请明细
	private List<String> selectedList = Lists.newArrayList();	//list页面操作中选中的信息

	// 退货画面用
	private String warehouse;		// 退回库房
	
	// 检索用
	private String orderNo;		// 合同编号
	private String snNo;		// S/N
	private String materialNo;		// 物料号

	public List<String> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<String> selectedList) {
		this.selectedList = selectedList;
	}

	private String responsiblePersonName; // 负责人

	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	private String opt;
	
	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}
	
	public SmStorageApp() {
		super();
	}

	public SmStorageApp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=5, message="出入库类型 : 代码长度必须介于 1 和 5 之间")
	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	
	@Length(min=1, max=5, message="工作流状态 : 代码长度必须介于 1 和 5 之间")
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Length(min=0, max=100, message="负责人长度必须介于 0 和 100 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	@Length(min=0, max=5, message="借用类型长度必须介于 0 和 5 之间")
	public String getLendingType() {
		return lendingType;
	}

	public void setLendingType(String lendingType) {
		this.lendingType = lendingType;
	}
	
	@Length(min=0, max=100, message="客户长度必须介于 0 和 100 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=5, message="行业长度必须介于 0 和 5 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLendingDateFrom() {
		return lendingDateFrom;
	}

	public void setLendingDateFrom(Date lendingDateFrom) {
		this.lendingDateFrom = lendingDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}

	public List<SmStorageAppDtl> getSmStorageAppDtlList() {
		return smStorageAppDtlList;
	}

	public void setSmStorageAppDtlList(List<SmStorageAppDtl> smStorageAppDtlList) {
		this.smStorageAppDtlList = smStorageAppDtlList;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	
}