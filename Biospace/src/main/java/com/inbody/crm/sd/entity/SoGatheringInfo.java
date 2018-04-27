/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 收款信息管理Entity
 * @author zhanglulu
 * @version 2017-08-22
 */
public class SoGatheringInfo extends DataEntity<SoGatheringInfo> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String orderNo;		// 合同号 :
	private String lineNo;		// 行号
	private String gatheringType;		// 收款方式 : 代码
	private Date expDateFrom;		// 预定日期起
	private Date actDateFrom;		// 实际日期起
	private String invoiceTitle;		// 开票抬头
	private String totalAmount;		// 总金额
	private String newRemarks;		// 备注说明
	
	public SoGatheringInfo() {
		super();
	}

	public SoGatheringInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=50, message="合同号 :长度必须介于 1 和 50 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=1, max=2, message="收款方式 : 代码长度必须介于 0 和 5 之间")
	public String getGatheringType() {
		return gatheringType;
	}

	public void setGatheringType(String gatheringType) {
		this.gatheringType = gatheringType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpDateFrom() {
		return expDateFrom;
	}

	public void setExpDateFrom(Date expDateFrom) {
		this.expDateFrom = expDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getActDateFrom() {
		return actDateFrom;
	}

	public void setActDateFrom(Date actDateFrom) {
		this.actDateFrom = actDateFrom;
	}
	
	@Length(min=0, max=100, message="开票抬头长度必须介于 0 和 100 之间")
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Length(min=1, max=300, message="备注说明长度必须介于 1 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
}