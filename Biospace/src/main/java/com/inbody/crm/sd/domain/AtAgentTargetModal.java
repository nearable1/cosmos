/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

//import java.util.List;
//
//import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.sd.entity.AtAgentTarget;

/**
 * 代理商目标制定
 * 
 * @author zhanglulu
 * @version 2017-08-30
 */
public class AtAgentTargetModal {

	private String agreementPatiesId; // 代理商
	private String agreementPatiesName; // 代理商
	private String targetType;	// 目标类型
	private String agreementId;	// 协议编码
	private Date validityDateFrom;	// 协议有效期起

	private List<AtAgentTarget> atAgentTargetDtlList = Lists.newArrayList();
	
	public String getAgreementPatiesId() {
		return agreementPatiesId;
	}
	public void setAgreementPatiesId(String agreementPatiesId) {
		this.agreementPatiesId = agreementPatiesId;
	}
	public String getAgreementPatiesName() {
		return agreementPatiesName;
	}
	public void setAgreementPatiesName(String agreementPatiesName) {
		this.agreementPatiesName = agreementPatiesName;
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
	public List<AtAgentTarget> getAtAgentTargetDtlList() {
		return atAgentTargetDtlList;
	}
	public void setAtAgentTargetDtlList(List<AtAgentTarget> atAgentTargetDtlList) {
		this.atAgentTargetDtlList = atAgentTargetDtlList;
	}
	
	public String getValidityDateFromForJsp() {
		if (this.validityDateFrom != null) {

			return DateUtils.formatDate(this.validityDateFrom, "yyyy-MM");
		} else {
			
			return null;
		}
	}
}