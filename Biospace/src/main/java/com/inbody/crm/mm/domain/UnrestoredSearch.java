/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.domain;

//import java.util.List;
//
//import com.google.common.collect.Lists;
import java.util.Date;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 待归还一览查询结果Entity
 * 
 * @author zhanglulu
 * @version 2017-08-30
 */
public class UnrestoredSearch extends DataEntity<UnrestoredSearch> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5044067381764131389L;
	private String snNo; // S/N
	private String materialNo; // 物料号
	private String materialName; // 物料名称
	private String lendingType; // 借出目的
	private String ifExpired; // 已过期
	
	private String lendingName; // 客户名称
	private Date productionDate; // 生产日期
	private String num; // 数量
	private String industry; // 行业
	private String newRemarks; // 备注
	private String accessoriesRemarks; // 相关配件备注
	private Date lendingDateFrom; // 借出时间
	private Date lendingDateTo; // 借出到期日
	private String responsiblePersonName; // 借出负责人

	public String getSnNo() {
		return snNo;
	}
	public void setSnNo(String snNo) {
		this.snNo = snNo;
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
	public String getLendingType() {
		return lendingType;
	}
	public void setLendingType(String lendingType) {
		this.lendingType = lendingType;
	}
	public String getIfExpired() {
		return ifExpired;
	}
	public void setIfExpired(String ifExpired) {
		this.ifExpired = ifExpired;
	}
	public String getLendingName() {
		return lendingName;
	}
	public void setLendingName(String lendingName) {
		this.lendingName = lendingName;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getNewRemarks() {
		return newRemarks;
	}
	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	public String getAccessoriesRemarks() {
		return accessoriesRemarks;
	}
	public void setAccessoriesRemarks(String accessoriesRemarks) {
		this.accessoriesRemarks = accessoriesRemarks;
	}
	public Date getLendingDateFrom() {
		return lendingDateFrom;
	}
	public void setLendingDateFrom(Date lendingDateFrom) {
		this.lendingDateFrom = lendingDateFrom;
	}
	public Date getLendingDateTo() {
		return lendingDateTo;
	}
	public void setLendingDateTo(Date lendingDateTo) {
		this.lendingDateTo = lendingDateTo;
	}
	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}
	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}
}