/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import java.math.BigDecimal;

/**
 * 单表生成Entity
 * @author zhanglulu
 * @version 2017-08-31
 */
public class SoRmQuotationDtl {

	private String procInsId;		// 流程编号
	private String quotationNo;		// 报价单编号 : 维修编号+&ldquo;_&rdquo;+01(预报价单）/02（最终报价单）
	private String lineNo;		// 行号
	private String repairNo;		// 维修编号 : YYMMDD(6)+流水(4-年循环)
	private String itemType;		// 报价项目分类 : 代码
	private String materialNo;		// 物料号
	private String model;		// 物料型号
	private String newSnNo;		// 新SN
	private String snNo;		// SN
	private BigDecimal unitPrice;		// 单价
	private Integer num;		// 数量
	private BigDecimal totalAmount;		// 总金额
	private String ifWarranty;		// 是否保内
	private BigDecimal actAmount;		// 实际金额
	private String ifPurchase;		// 是否已采购
 
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getQuotationNo() {
		return quotationNo;
	}
	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getRepairNo() {
		return repairNo;
	}
	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getNewSnNo() {
		return newSnNo;
	}
	public void setNewSnNo(String newSnNo) {
		this.newSnNo = newSnNo;
	}
	public String getSnNo() {
		return snNo;
	}
	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getIfWarranty() {
		return ifWarranty;
	}
	public void setIfWarranty(String ifWarranty) {
		this.ifWarranty = ifWarranty;
	}
	public BigDecimal getActAmount() {
		return actAmount;
	}
	public void setActAmount(BigDecimal actAmount) {
		this.actAmount = actAmount;
	}
	public String getIfPurchase() {
		return ifPurchase;
	}
	public void setIfPurchase(String ifPurchase) {
		this.ifPurchase = ifPurchase;
	}
}