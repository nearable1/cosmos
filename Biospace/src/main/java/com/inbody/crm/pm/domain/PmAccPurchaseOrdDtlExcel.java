/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 配件采购订单导出excel用
 * 
 * @author yangj
 * @version 2017-07-24
 */
public class PmAccPurchaseOrdDtlExcel {

	private String materialNo;		// 物料号
	private String model;         // 型号
	private String accessoryName; // 配件名
	private String num;           // 数量
	private String paymentType;   // 有无偿
	private String unitPrice;     // 单价
	private String totalAmount;   // 合计
	private String repairNo;      // 维修编号

	@ExcelField(title = "物料号", type = 1, align = 1, sort = 1)
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	@ExcelField(title = "型号", type = 1, align = 1, sort = 2)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@ExcelField(title = "配件名", type = 1, align = 1, sort = 3)
	public String getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

	@ExcelField(title = "数量", type = 1, align = 3, sort = 4)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@ExcelField(title = "有无偿", type = 1, align = 1, sort = 5)
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@ExcelField(title = "单价", type = 1, align = 3, sort = 6)
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@ExcelField(title = "合计", type = 1, align = 3, sort = 7)
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@ExcelField(title = "维修编号", type = 1, align = 1, sort = 8)
	public String getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}

	
}