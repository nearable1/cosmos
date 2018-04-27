/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author yangj
 * @version 2017-08-16
 */
public class RmConsultingInfo extends DataEntity<RmConsultingInfo> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String procInsGroupid;		// 流程组号
	private String consultingNo;		// 咨询编号 :
	private String consultingType;		// 问题分类 : 代码
	private String materialNo;		// 物料号
	private String materialName;		// 物料名
	private Date consultingDate;		// 咨询日期
	private String consultingStatus;		// 处理状态 : 代码
	private String customerName;		// 客户
	private String contactsName;		// 联系人
	private String telephone;		// 电话
	private String newRemarks;		// 备注说明

	private String responsiblePersonId; // 负责工程师
	private String responsiblePersonName; // 负责工程师名称
	
	public RmConsultingInfo() {
		super();
	}

	public RmConsultingInfo(String id){
		super(id);
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getProcInsGroupid() {
		return procInsGroupid;
	}

	public void setProcInsGroupid(String procInsGroupid) {
		this.procInsGroupid = procInsGroupid;
	}

	public String getConsultingNo() {
		return consultingNo;
	}

	public void setConsultingNo(String consultingNo) {
		this.consultingNo = consultingNo;
	}

	public String getConsultingType() {
		return consultingType;
	}

	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
	}
	
	@NotBlank
	@Length(min=0, max=50, message="物料号长度必须介于 0 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@NotNull(message="咨询日期不能为空")
	@Past(message="咨询日期不能晚于当前日期")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getConsultingDate() {
		return consultingDate;
	}

	public void setConsultingDate(Date consultingDate) {
		this.consultingDate = consultingDate;
	}
	
	@Length(min=0, max=2, message="处理状态 : 代码长度必须介于 0 和 2 之间")
	public String getConsultingStatus() {
		return consultingStatus;
	}

	public void setConsultingStatus(String consultingStatus) {
		this.consultingStatus = consultingStatus;
	}
	
	@Length(min=0, max=100, message="客户长度必须介于 0 和 100 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=100, message="联系人长度必须介于 0 和 100 之间")
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}

	@Length(min=0, max=100, message="备注说明长度必须介于 0 和 100 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}

	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}
	
}