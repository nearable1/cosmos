/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.entity;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.ActEntity;

/**
 * 主子表Entity
 * @author yangj
 * @version 2017-07-24
 */
public class PmPurchaseOrd extends ActEntity<PmPurchaseOrd> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String purchaseNo;		// 采购订单号
	private String axNo;		    // AX订单号
	private String purchaseType;	// 采购类型 : 代码
	private String supplierId;		// 供应商
	private String supplierName;    // 供应商名称
	private String paymentStatus;	// 支付状态 : 代码
	private String arrivalStatus;	// 到货状态 : 代码
	private String newRemarks;		// 备注说明
	private String workflowStatus;  // 流程状态
	private String opt;             // 流程操作
	
	private List<PmPurInvoice> pmPurInvoiceList = Lists.newArrayList();		// 子表列表
	private List<PmPurchaseOrdDtl> pmPurchaseOrdDtlList = Lists.newArrayList();		// 子表列表

	private String createName; //创建者
	private List<RmQuotationDtl> rmQuotationDtlList = Lists.newArrayList();     // 配件采购报价单列表
	private List<PmPurchaseOrdDtl> pmFocPurchaseOrdDtlList = Lists.newArrayList();		// 子表列表
	
	public PmPurchaseOrd() {
		super();
	}

	public PmPurchaseOrd(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=50, message="采购订单号长度必须介于 1 和 50 之间")
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
	@Length(min=0, max=50, message="AX订单号长度必须介于 0 和 50 之间")
	public String getAxNo() {
		return axNo;
	}

	public void setAxNo(String axNo) {
		this.axNo = axNo;
	}
	
	@Length(min=1, max=2, message="采购类型 : 代码长度必须介于 1 和 2 之间")
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	@Length(min=0, max=50, message="供应商长度必须介于 0 和 50 之间")
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

	@Length(min=0, max=2, message="支付状态 : 代码长度必须介于 0 和 2 之间")
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	@Length(min=0, max=2, message="到货状态 : 代码长度必须介于 0 和 2 之间")
	public String getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(String arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	@Valid
	public List<PmPurInvoice> getPmPurInvoiceList() {
		return pmPurInvoiceList;
	}

	public void setPmPurInvoiceList(List<PmPurInvoice> pmPurInvoiceList) {
		this.pmPurInvoiceList = pmPurInvoiceList;
	}
	
	@Valid
	public List<PmPurchaseOrdDtl> getPmPurchaseOrdDtlList() {
		return pmPurchaseOrdDtlList;
	}

	public void setPmPurchaseOrdDtlList(List<PmPurchaseOrdDtl> pmPurchaseOrdDtlList) {
		this.pmPurchaseOrdDtlList = pmPurchaseOrdDtlList;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public List<RmQuotationDtl> getRmQuotationDtlList() {
		return rmQuotationDtlList;
	}

	public void setRmQuotationDtlList(List<RmQuotationDtl> rmQuotationDtlList) {
		this.rmQuotationDtlList = rmQuotationDtlList;
	}

	public List<PmPurchaseOrdDtl> getPmFocPurchaseOrdDtlList() {
		return pmFocPurchaseOrdDtlList;
	}

	public void setPmFocPurchaseOrdDtlList(
			List<PmPurchaseOrdDtl> pmFocPurchaseOrdDtlList) {
		this.pmFocPurchaseOrdDtlList = pmFocPurchaseOrdDtlList;
	}

}