/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 主子表Entity
 * @author yangj
 * @version 2017-07-27
 */
public class PmPurInvoice extends DataEntity<PmPurInvoice> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String purchaseNo;		// 采购订单号 父类
	private Integer lineNo2;		// 发票行号
	private String invoiceNo;		// 发票号码
	private Date invoiceDate;		// 开票日期
	private String newRemarks;		// 备注说明
	
	public PmPurInvoice() {
		super();
	}

	public PmPurInvoice(String purchaseNo){
		this.purchaseNo = purchaseNo;
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=50, message="采购订单号长度必须介于 1 和 50 之间")
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
	//@Length(min=1, max=2, message="发票行号长度必须介于 1 和 2 之间")
	public Integer getLineNo2() {
		return lineNo2;
	}

	public void setLineNo2(Integer lineNo2) {
		this.lineNo2 = lineNo2;
	}
	
	@NotNull
	@Length(min=1, max=50, message="发票号码长度必须介于 1 和 50 之间")
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开票日期不能为空")
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
}