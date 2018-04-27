/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;


/**
 * 单表生成Entity
 * 
 * @author yangj
 * @version 2017-08-16
 */
public class RmConsultingExcel {

	private String consultingNo; // 咨询编号 :
	private String consultingType; // 咨询问题分类
	private String materialName; // 物料名
	private String consultingDate; // 咨询日期
	private String createName; // 记录人
	private String consultingStatus; // 处理状态 : 代码
	private String customerName; // 客户
	private String contactsName; // 联系人
	private String telephone; // 电话
	private String responsiblePersonName; // 负责工程师名称

	@ExcelField(title="咨询编号", type=1, align=1, sort=1)
	public String getConsultingNo() {
		return consultingNo;
	}

	public void setConsultingNo(String consultingNo) {
		this.consultingNo = consultingNo;
	}

	@ExcelField(title="咨询问题分类", type=1, align=1, sort=2, dictType="DM0019")
	public String getConsultingType() {
		return consultingType;
	}

	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
	}

	@ExcelField(title="咨询型号", type=1, align=1, sort=3)
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@ExcelField(title="咨询日期", type=1, align=1, sort=4)
	public String getConsultingDate() {
		return consultingDate;
	}

	public void setConsultingDate(String consultingDate) {
		this.consultingDate = consultingDate;
	}

	@ExcelField(title="记录人", type=1, align=1, sort=5)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@ExcelField(title="处理状态", type=1, align=1, sort=6, dictType="DM0029")
	public String getConsultingStatus() {
		return consultingStatus;
	}

	public void setConsultingStatus(String consultingStatus) {
		this.consultingStatus = consultingStatus;
	}

	@ExcelField(title="负责工程师", type=1, align=1, sort=7)
	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	@ExcelField(title="单位名称", type=1, align=1, sort=8)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@ExcelField(title="联系人", type=1, align=1, sort=9)
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	@ExcelField(title="电话", type=1, align=1, sort=10)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}