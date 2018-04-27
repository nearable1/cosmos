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
 * 其他入库申请_借出物料Entity
 * @author zhanglulu
 * @version 2017-10-23
 */
@Alias("InSmLendingMat")
public class SmLendingMat extends DataEntity<SmLendingMat> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String snNo;		// SN
	private String materialNo;		// 物料号
	private String warehouse;		// 库房 : 代码
	private String num;		// 数量
	private String storageId;		// 出入库履历编号 : storage_type(2)+流水(8)
	private String workflowStatus;		// 工作流状态 : 代码
	private String lenderName;		// 借出方
	private Date lendingDateFrom;		// 借出日期起
	private Date lendingDateTo;		// 借出日期止

	private String materialName; // 物料名称
	private Date productionDate; // 生产日期
	
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public SmLendingMat() {
		super();
	}

	public SmLendingMat(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
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
	
	@Length(min=0, max=2, message="库房 : 代码长度必须介于 0 和 2 之间")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@Length(min=1, max=3, message="数量长度必须介于 1 和 3 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=15, message="出入库履历编号 : storage_type(2)+流水(8)长度必须介于 0 和 15 之间")
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	
	@Length(min=1, max=5, message="工作流状态 : 代码长度必须介于 1 和 5 之间")
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Length(min=1, max=100, message="借出方长度必须介于 1 和 100 之间")
	public String getLenderName() {
		return lenderName;
	}

	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
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
	@NotNull(message="借出日期止不能为空")
	public Date getLendingDateTo() {
		return lendingDateTo;
	}

	public void setLendingDateTo(Date lendingDateTo) {
		this.lendingDateTo = lendingDateTo;
	}
	
}