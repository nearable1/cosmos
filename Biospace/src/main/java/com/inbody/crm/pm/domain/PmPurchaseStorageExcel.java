/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;


/**
 * 采购入库查询结果Entity
 * 
 * @author yangj
 * @version 2017-07-24
 */
public class PmPurchaseStorageExcel {

	private String id; // id编号
	private String purchaseNo; // 采购订单号
	private String purchaseType; // 采购类型 : 代码
	private String ifFoc; // 是否FOC
	private String materialNo; // 物料号
	private String materialName; // 物料名
	private String sn; // sn
	private String productionDate; // 生产日期

	@ExcelField(title="ID", type=0, align=1, sort=1, locked=true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title="采购订单号", type=0, align=1, sort=2, locked=true)
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	@ExcelField(title="采购类型", type=0, align=1, sort=3, locked=true, dictType="DM0021")
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@ExcelField(title="FOC", type=0, align=1, sort=4, locked=true, dictType="yes_no")
	public String getIfFoc() {
		return ifFoc;
	}

	public void setIfFoc(String ifFoc) {
		this.ifFoc = ifFoc;
	}

	@ExcelField(title="物料号", type=0, align=1, sort=5, locked=true)
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	@ExcelField(title="物料名称", type=0, align=1, sort=6, locked=true)
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@ExcelField(title="S/N", type=0, align=1, sort=7)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@ExcelField(title="生产日期", type=0, align=1, sort=8)
	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

}