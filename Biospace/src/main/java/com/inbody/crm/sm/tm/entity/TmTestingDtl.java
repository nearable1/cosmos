/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.tm.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 主子表Entity
 * @author yangj
 * @version 2017-09-20
 */
public class TmTestingDtl extends DataEntity<TmTestingDtl> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private TmTesting tmTesting;		// 检测编号
	private String testingNo;		// 检测编号
	private String lineNo;		// 行号
	private String materialNo;		// 物料号
	private String snNo;		// SN
	private String ifPackingGood;		// 包装外观完好
	private String ifOutlookGood;		// 仪器外观完好
	private String additionalInstructions;		// 附加说明
	private String newRemarks;		// 备注说明

	private Date productionDate;		// 物料号
	private String status;		// 物料号
	private String machineType;		// 物料号

	private String machineTypeName;
	private String statusValue;
	private String productionDateValue;

	private String snVersion;		// SN版本号
	
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

	public TmTestingDtl() {
		super();
	}

	public TmTestingDtl(String id){
		super(id);
	}

	public TmTestingDtl(TmTesting tmTesting){
		this.tmTesting = tmTesting;
		this.testingNo = tmTesting.getTestingNo();
	}


	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="检测编号 : 流水(8)长度必须介于 1 和 15 之间")
	public String getTestingNo() {
		return testingNo;
	}

	public void setTestingNo(String testingNo) {
		this.testingNo = testingNo;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=1, max=50, message="物料号长度必须介于 1 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@Length(min=1, max=50, message="SN长度必须介于 1 和 50 之间")
	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	
	@Length(min=0, max=1, message="包装外观完好长度必须介于 0 和 1 之间")
	public String getIfPackingGood() {
		return ifPackingGood;
	}

	public void setIfPackingGood(String ifPackingGood) {
		this.ifPackingGood = ifPackingGood;
	}
	
	@Length(min=0, max=1, message="仪器外观完好长度必须介于 0 和 1 之间")
	public String getIfOutlookGood() {
		return ifOutlookGood;
	}

	public void setIfOutlookGood(String ifOutlookGood) {
		this.ifOutlookGood = ifOutlookGood;
	}
	
	@Length(min=0, max=5, message="附加说明分类 : 代码长度必须介于 0 和 5 之间")
	public String getAdditionalInstructions() {
		return additionalInstructions;
	}

	public void setAdditionalInstructions(String additionalInstructions) {
		this.additionalInstructions = additionalInstructions;
	}
	
	@Length(min=1, max=300, message="备注说明长度必须介于 1 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public TmTesting getTmTesting() {
		return tmTesting;
	}

	public void setTmTesting(TmTesting tmTesting) {
		this.tmTesting = tmTesting;
	}

	public String getSnVersion() {
		return snVersion;
	}

	public void setSnVersion(String snVersion) {
		this.snVersion = snVersion;
	}
	
}