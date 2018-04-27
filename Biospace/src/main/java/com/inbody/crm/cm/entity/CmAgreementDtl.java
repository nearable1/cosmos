package com.inbody.crm.cm.entity;

import java.util.Date;

import com.inbody.crm.common.persistence.ActEntity;

public class CmAgreementDtl extends ActEntity<CmAgreementDtl>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8551057021696894737L;
	private String procInsId;//流程实例
	private String agreementId;//协议编码   协议信息-ID
	private Integer lineNo;//行号
	private String period;//阶段
	private Date periodDateFrom;
	private Date periodDateTo;
	private String createId;
	private String updateId;
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getAgreementId() {
		return agreementId;
	}
	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	public Integer getLineNo() {
		return lineNo;
	}
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Date getPeriodDateFrom() {
		return periodDateFrom;
	}
	public void setPeriodDateFrom(Date periodDateFrom) {
		this.periodDateFrom = periodDateFrom;
	}
	public Date getPeriodDateTo() {
		return periodDateTo;
	}
	public void setPeriodDateTo(Date periodDateTo) {
		this.periodDateTo = periodDateTo;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	
	
}
