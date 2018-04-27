/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.domain;

import java.math.BigDecimal;
//import java.util.List;
//
//import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 采购订单查询结果Entity
 * 
 * @author yangj
 * @version 2017-07-24
 */
public class PmPurchaseOrdSearch extends DataEntity<PmPurchaseOrdSearch> {

	private static final long serialVersionUID = 1L;
	private String id;         // 采购订单id
	private String purchaseNo; // 采购订单号
	private String purchaseType; // 采购类型 : 代码
	private String ifFoc; // 是否FOC
	private String purStatus; // 采购订单状态
	private String supplierId; // 供应商id
	private String supplierName; // 供应商名称
	private String paymentStatus; // 支付状态 : 代码
	private String arrivalStatus; // 到货状态 : 代码
	private String materialNo; // 物料号
	private String materialName; // 物料名
	private BigDecimal unitPrice; // 单价
	private Integer num; // 数量
	private Integer unpayNum; // 未付数量
	private BigDecimal unpayAmount; // 未付金额
	private Integer alArrivalNum; // 已到货数量
	private Integer unarrivalNum; // 未到货数量
	private String invoiceNo; // 发票号码
	private String axNo; // AX订单号

	public PmPurchaseOrdSearch() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getIfFoc() {
		return ifFoc;
	}

	public void setIfFoc(String ifFoc) {
		this.ifFoc = ifFoc;
	}

    public String getPurStatus() {
        return purStatus;
    }

    public void setPurStatus(String purStatus) {
        this.purStatus = purStatus;
    }

    public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(String arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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

	public Integer getUnpayNum() {
		return unpayNum;
	}

	public void setUnpayNum(Integer unpayNum) {
		this.unpayNum = unpayNum;
	}

	public BigDecimal getUnpayAmount() {
		return unpayAmount;
	}

	public void setUnpayAmount(BigDecimal unpayAmount) {
		this.unpayAmount = unpayAmount;
	}

	public Integer getAlArrivalNum() {
		return alArrivalNum;
	}

	public void setAlArrivalNum(Integer alArrivalNum) {
		this.alArrivalNum = alArrivalNum;
	}

	public Integer getUnarrivalNum() {
		return unarrivalNum;
	}

	public void setUnarrivalNum(Integer unarrivalNum) {
		this.unarrivalNum = unarrivalNum;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getAxNo() {
		return axNo;
	}

	public void setAxNo(String axNo) {
		this.axNo = axNo;
	}
}