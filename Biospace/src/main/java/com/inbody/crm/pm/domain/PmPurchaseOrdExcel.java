/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;


/**
 * 采购订单查询结果Entity
 * 
 * @author yangj
 * @version 2017-07-24
 */
public class PmPurchaseOrdExcel {

	private String purchaseNo; // 采购订单号
	private String purchaseType; // 采购类型 : 代码
	private String ifFoc; // 是否FOC
	private String supplierName; // 供应商名称
	private String purStatus; // 采购订单状态
	private String paymentStatus; // 支付状态 : 代码
	private String arrivalStatus; // 到货状态 : 代码
	private String materialNo; // 物料号
	private String materialName; // 物料名
	private String unitPrice; // 单价
	private String num; // 数量
	private String unpayNum; // 未付数量
	private String unpayAmount; // 未付金额
	private String alArrivalNum; // 已到货数量
	private String unarrivalNum; // 未到货数量
	private String invoiceNo; // 发票号码
	private String axNo; // AX订单号

	@ExcelField(title="订单号", type=1, align=1, sort=1)
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	@ExcelField(title="采购类型", type=1, align=1, sort=2, dictType="DM0021")
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@ExcelField(title="FOC", type=1, align=1, sort=3, dictType="yes_no")
	public String getIfFoc() {
		return ifFoc;
	}

	public void setIfFoc(String ifFoc) {
		this.ifFoc = ifFoc;
	}

	@ExcelField(title="供应商", type=1, align=1, sort=4)
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

    @ExcelField(title="订单状态", type=1, align=1, sort=5, dictType="DM0015")
    public String getPurStatus() {
        return purStatus;
    }

    public void setPurStatus(String purStatus) {
        this.purStatus = purStatus;
    }

    @ExcelField(title="支付状态", type=1, align=1, sort=6, dictType="DM0022")
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@ExcelField(title="到货状态", type=1, align=1, sort=7, dictType="DM0023")
	public String getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(String arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	@ExcelField(title="物料号", type=1, align=1, sort=8)
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	@ExcelField(title="物料名称", type=1, align=1, sort=9)
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@ExcelField(title="单价", type=1, align=3, sort=10)
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@ExcelField(title="数量", type=1, align=3, sort=11)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@ExcelField(title="未付数量", type=1, align=3, sort=12)
	public String getUnpayNum() {
		return unpayNum;
	}

	public void setUnpayNum(String unpayNum) {
		this.unpayNum = unpayNum;
	}

	@ExcelField(title="未付金额", type=1, align=3, sort=13)
	public String getUnpayAmount() {
		return unpayAmount;
	}

	public void setUnpayAmount(String unpayAmount) {
		this.unpayAmount = unpayAmount;
	}

	@ExcelField(title="到货数量", type=1, align=3, sort=14)
	public String getAlArrivalNum() {
		return alArrivalNum;
	}

	public void setAlArrivalNum(String alArrivalNum) {
		this.alArrivalNum = alArrivalNum;
	}

	@ExcelField(title="未到货数量", type=1, align=3, sort=15)
	public String getUnarrivalNum() {
		return unarrivalNum;
	}

	public void setUnarrivalNum(String unarrivalNum) {
		this.unarrivalNum = unarrivalNum;
	}

	@ExcelField(title="发票号码", type=1, align=1, sort=16)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@ExcelField(title="AX编号", type=1, align=1, sort=17)
	public String getAxNo() {
		return axNo;
	}

	public void setAxNo(String axNo) {
		this.axNo = axNo;
	}

}