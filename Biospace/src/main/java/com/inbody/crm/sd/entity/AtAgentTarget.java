/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 代理商目标Entity
 * @author zhanglulu
 * @version 2017-09-26
 */
public class AtAgentTarget extends DataEntity<AtAgentTarget> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String customerId;		// 客户代码 : &ldquo;KH&rdquo;(2)+流水(6)
	private String agreementId;		// 协议编码
	private String targetType;		// 目标类型 : 代码
	private String period;		// 阶段
	private String totalAmount;		// 总金额
	private String materialNo;		// 物料号
	private String num;		// 数量
	private String model;		// 物料型号
	
	// 业绩数据
	private String trackTotalAmount;		// 总金额
	private String trackAmount;		// 金额
	private String trackMaterialNo;		// 物料号
	private String trackNum;		// 数量
	private String trackModel;		// 物料型号
	
	public AtAgentTarget() {
		super();
	}

	public AtAgentTarget(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="客户代码 : &ldquo;KH&rdquo;(2)+流水(6)长度必须介于 1 和 15 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=1, max=15, message="协议编码长度必须介于 1 和 15 之间")
	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	
	@Length(min=1, max=5, message="目标类型 : 代码长度必须介于 1 和 5 之间")
	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
	@Length(min=0, max=5, message="阶段长度必须介于 0 和 5 之间")
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Length(min=0, max=50, message="物料号长度必须介于 0 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@Length(min=0, max=3, message="数量长度必须介于 0 和 3 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTrackTotalAmount() {
		return trackTotalAmount;
	}

	public void setTrackTotalAmount(String trackTotalAmount) {
		this.trackTotalAmount = trackTotalAmount;
	}

	public String getTrackAmount() {
		return trackAmount;
	}

	public void setTrackAmount(String trackAmount) {
		this.trackAmount = trackAmount;
	}

	public String getTrackMaterialNo() {
		return trackMaterialNo;
	}

	public void setTrackMaterialNo(String trackMaterialNo) {
		this.trackMaterialNo = trackMaterialNo;
	}

	public String getTrackNum() {
		return trackNum;
	}

	public void setTrackNum(String trackNum) {
		this.trackNum = trackNum;
	}

	public String getTrackModel() {
		return trackModel;
	}

	public void setTrackModel(String trackModel) {
		this.trackModel = trackModel;
	}
	
}