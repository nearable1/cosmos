/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

//import java.util.List;
//
//import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.inbody.crm.common.persistence.DataEntity;
import com.inbody.crm.sd.entity.AtAgentTarget;

/**
 * 代理商目标查询结果Entity
 * 
 * @author zhanglulu
 * @version 2017-08-30
 */
public class AtAgentTargetSearch extends DataEntity<AtAgentTargetSearch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3272629723381153602L;
	private String customerId; // 代理商
	private String ifNew; // 仅显示最新协议
	
	private String customerName; // 代理商
	private String targetType;	// 目标类型
	private String agreementId;	// 协议编码
	private Date validityDateFrom;	// 协议有效期起
	
	private int listSize;	// 对比明细数
	private Map<String, List<AtAgentTarget>> periodList = new LinkedHashMap<String, List<AtAgentTarget>>();		// 对比明细
	private Map<String, BigDecimal> periodTotalAmount = new LinkedHashMap<String, BigDecimal>();		// 阶段目标总金额
	private Map<String, BigDecimal> periodTrackTotalAmount = new LinkedHashMap<String, BigDecimal>();		// 阶段业绩总金额
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getIfNew() {
		return ifNew;
	}
	public void setIfNew(String ifNew) {
		this.ifNew = ifNew;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getAgreementId() {
		return agreementId;
	}
	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	public Date getValidityDateFrom() {
		return validityDateFrom;
	}
	public void setValidityDateFrom(Date validityDateFrom) {
		this.validityDateFrom = validityDateFrom;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	public Map<String, List<AtAgentTarget>> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(Map<String, List<AtAgentTarget>> periodList) {
		this.periodList = periodList;
	}
	public Map<String, BigDecimal> getPeriodTotalAmount() {
		return periodTotalAmount;
	}
	public void setPeriodTotalAmount(Map<String, BigDecimal> periodTotalAmount) {
		this.periodTotalAmount = periodTotalAmount;
	}
	public Map<String, BigDecimal> getPeriodTrackTotalAmount() {
		return periodTrackTotalAmount;
	}
	public void setPeriodTrackTotalAmount(
			Map<String, BigDecimal> periodTrackTotalAmount) {
		this.periodTrackTotalAmount = periodTrackTotalAmount;
	}
}