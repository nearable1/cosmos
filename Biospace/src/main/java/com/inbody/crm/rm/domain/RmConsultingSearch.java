/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import java.util.Date;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * 
 * @author yangj
 * @version 2017-08-16
 */
public class RmConsultingSearch extends DataEntity<RmConsultingSearch> {

	private static final long serialVersionUID = 3916260550683697122L;

	private String id;
	private String consultingNo; // 咨询编号 :
	private String consultingType; // 问题分类 : 代码
	private String materialNo; // 物料号
	private String materialName; // 物料名
	private Date consultingDate; // 咨询日期
	private String consultingStatus; // 处理状态 : 代码
	private String customerName; // 客户
	private String contactsName; // 联系人
	private String telephone; // 电话
	private Date consultingDateFrom; // 咨询日期from
	private Date consultingDateTo; // 咨询日期to

	private String responsiblePersonId; // 负责工程师
	private String responsiblePersonName; // 负责工程师名称

	public RmConsultingSearch() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getConsultingDate() {
		return consultingDate;
	}

	public void setConsultingDate(Date consultingDate) {
		this.consultingDate = consultingDate;
	}

	public String getConsultingStatus() {
		return consultingStatus;
	}

	public void setConsultingStatus(String consultingStatus) {
		this.consultingStatus = consultingStatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getConsultingDateFrom() {
		return consultingDateFrom;
	}

	public void setConsultingDateFrom(Date consultingDateFrom) {
		this.consultingDateFrom = consultingDateFrom;
	}

	public Date getConsultingDateTo() {
		return consultingDateTo;
	}

	public void setConsultingDateTo(Date consultingDateTo) {
		this.consultingDateTo = consultingDateTo;
	}

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