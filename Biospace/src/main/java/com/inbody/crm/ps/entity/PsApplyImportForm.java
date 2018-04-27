package com.inbody.crm.ps.entity;

import com.inbody.crm.common.persistence.ActEntity;

public class PsApplyImportForm extends ActEntity<PsApplyPrice> {
	
	private static final long serialVersionUID = 1L;
	private String priceSystemCopy;		// 价格体系 : 代码
	private String workflowStatusCopy;		// 工作流状态 : 代码
	private String protocolsCopy;
	private String startdaysCopy;
	public String getPriceSystemCopy() {
		return priceSystemCopy;
	}
	public void setPriceSystemCopy(String priceSystemCopy) {
		this.priceSystemCopy = priceSystemCopy;
	}
	public String getWorkflowStatusCopy() {
		return workflowStatusCopy;
	}
	public void setWorkflowStatusCopy(String workflowStatusCopy) {
		this.workflowStatusCopy = workflowStatusCopy;
	}
	public String getProtocolsCopy() {
		return protocolsCopy;
	}
	public void setProtocolsCopy(String protocolsCopy) {
		this.protocolsCopy = protocolsCopy;
	}
	public String getStartdaysCopy() {
		return startdaysCopy;
	}
	public void setStartdaysCopy(String startdaysCopy) {
		this.startdaysCopy = startdaysCopy;
	}
	
}
