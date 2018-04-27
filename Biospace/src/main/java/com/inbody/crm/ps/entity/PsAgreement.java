/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ps.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author NSSOL
 * @version 2017-07-21
 */
public class PsAgreement extends DataEntity<PsAgreement> {
	
	private static final long serialVersionUID = 1L;
	private String agreementPatiesId;		// 协议方 : 客户代码
	private Date validityDateFrom;		// 协议有效期起
	private Date validityDateTo;		// 协议有效期止
	private String newRemarks;		// 备注说明
	
	public PsAgreement() {
		super();
	}

	public PsAgreement(String id){
		super(id);
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
	public Date getValidityDateFrom() {
		return validityDateFrom;
	}

	public void setValidityDateFrom(Date validityDateFrom) {
		this.validityDateFrom = validityDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="协议有效期止不能为空")
	public Date getValidityDateTo() {
		return validityDateTo;
	}

	public void setValidityDateTo(Date validityDateTo) {
		this.validityDateTo = validityDateTo;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
}