/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.cm.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 添加Entity
 * @author Sea
 * @version 2017-07-26
 */
public class CmAgreement extends DataEntity<CmAgreement> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String agreementId;		// 协议编码 : 内部-
	private String agreementPatiesId;		// 协议方 : 客户代码
	private String validityDateFrom;		// 协议有效期起
	private String validityDateTo;		// 协议有效期止
	private String newRemarks;		// 备注说明
	private String createId;
	private String updateId;
	public CmAgreement() {
		super();
	}

	public CmAgreement(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="协议编码 : 内部-长度必须介于 1 和 15 之间")
	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	
	@Length(min=1, max=50, message="协议方 : 客户代码长度必须介于 1 和 50 之间")
	public String getAgreementPatiesId() {
		return agreementPatiesId;
	}

	public void setAgreementPatiesId(String agreementPatiesId) {
		this.agreementPatiesId = agreementPatiesId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="协议有效期起不能为空")
	public String getValidityDateFrom() {
		return validityDateFrom;
	}

	public void setValidityDateFrom(String validityDateFrom) {
		this.validityDateFrom = validityDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="协议有效期止不能为空")
	public String getValidityDateTo() {
		return validityDateTo;
	}

	public void setValidityDateTo(String validityDateTo) {
		this.validityDateTo = validityDateTo;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
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