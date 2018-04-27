/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 主子表生成Entity
 * @author Nssol
 * @version 2017-07-20
 */
public class BoBusinessOppDtl extends DataEntity<BoBusinessOppDtl> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String procInsGroupid;		// 流程组号
	private BoBusinessOpp businessOpp;		// 商机号 : 父类
	private String businessOppNo;		// 商机号 : 父类
	private String lineNo;		// 行号
	private String materialNo;		// 物料号
	private String model;		// 物料号
	private String num;		// 数量
	private String unitPrice;		// 单价
	private String totalAmount;		// 总金额
	private String standardPrice;		// 标准价
	private String ifSpecialOffer;		// 是否特价
	private String ifSpecialOfferLabel;		// 是否特价标签显示内容
	
	public BoBusinessOppDtl() {
		super();
	}

	public BoBusinessOppDtl(String id){
		super(id);
	}

	public BoBusinessOppDtl(BoBusinessOpp businessOpp){
		this.businessOpp = businessOpp;
		this.businessOppNo = businessOpp.getBusinessOppNo();
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=64, message="流程组号长度必须介于 0 和 64 之间")
	public String getProcInsGroupid() {
		return procInsGroupid;
	}

	public void setProcInsGroupid(String procInsGroupid) {
		this.procInsGroupid = procInsGroupid;
	}
	
	@Length(min=1, max=15, message="商机号 :长度必须介于 1 和 15 之间")
	public String getBusinessOppNo() {
		return businessOppNo;
	}

	public void setBusinessOppNo(String businessOppNo) {
		this.businessOppNo = businessOppNo;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
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
	
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}
	
	@Length(min=0, max=1, message="是否特价长度必须介于 0 和 1 之间")
	public String getIfSpecialOffer() {
		return ifSpecialOffer;
	}

	public void setIfSpecialOffer(String ifSpecialOffer) {
		this.ifSpecialOffer = ifSpecialOffer;
	}

	public BoBusinessOpp getBusinessOpp() {
		return businessOpp;
	}

	public void setBusinessOpp(BoBusinessOpp businessOpp) {
		this.businessOpp = businessOpp;
	}

	public String getIfSpecialOfferLabel() {
		return ifSpecialOfferLabel;
	}

	public void setIfSpecialOfferLabel(String ifSpecialOfferLabel) {
		this.ifSpecialOfferLabel = ifSpecialOfferLabel;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}