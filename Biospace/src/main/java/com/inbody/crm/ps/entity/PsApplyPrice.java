/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ps.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.ActEntity;

/**
 * 主子表生成Entity
 * @author Nssol
 * @version 2017-07-20
 */
public class PsApplyPrice extends ActEntity<PsApplyPrice> {
	
	private static final long serialVersionUID = 1L;
	private String priceSystem;		// 价格体系 : 代码
	private String selectPriceSystem;
	private String workflowStatus;		// 工作流状态 : 代码
	private String protocols;
	private String startdays;
	private List<PsApplyPriceDtl> psApplyPriceDtlList = Lists.newArrayList();		// 子表列表
	
	public PsApplyPrice() {
		super();
	}

	public PsApplyPrice(String id){
		super(id);
	}

	@Length(min=1, max=2, message="价格体系 : 代码长度必须介于 1 和 2 之间")
	public String getPriceSystem() {
		return priceSystem;
	}

	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}
	
	@Length(min=1, max=2, message="工作流状态 : 代码长度必须介于 1 和 2 之间")
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	public List<PsApplyPriceDtl> getPsApplyPriceDtlList() {
		return psApplyPriceDtlList;
	}

	public void setPsApplyPriceDtlList(List<PsApplyPriceDtl> psApplyPriceDtlList) {
		this.psApplyPriceDtlList = psApplyPriceDtlList;
	}

	public String getProtocols() {
		return protocols;
	}

	public void setProtocols(String protocols) {
		this.protocols = protocols;
	}

	public String getStartdays() {
		return startdays;
	}

	public void setStartdays(String startdays) {
		this.startdays = startdays;
	}

	public String getSelectPriceSystem() {
		return selectPriceSystem;
	}

	public void setSelectPriceSystem(String selectPriceSystem) {
		this.selectPriceSystem = selectPriceSystem;
	}
}