/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * AX销售发票一览导出excel用
 * 
 * @author zhanglulu
 * @version 2017-08-15
 */
public class UnrestoredExcel {
	private String materialNo; // 物料号
	private String materialName; // 物料名称
	private String snNo; // S/N
	private String productionDate; // 生产日期
	private String num; // 数量
	private String lendingType; // 借出目的
	private String lendingName; // 客户名称
	private String industry; // 行业
	private String newRemarks; // 备注
	private String accessoriesRemarks; // 相关配件备注
	private String lendingDateFrom; // 借出时间
	private String lendingDateTo; // 借出到期日
	private String responsiblePersonName; // 借出负责人
	
	@ExcelField(title = "物料号", type = 1, align = 1, sort = 1)
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	@ExcelField(title = "物料名称", type = 1, align = 1, sort = 2)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@ExcelField(title = "S/N", type = 1, align = 1, sort = 3)
	public String getSnNo() {
		return snNo;
	}
	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	@ExcelField(title = "生产日期", type = 1, align = 1, sort = 4)
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	@ExcelField(title = "数量", type = 1, align = 3, sort = 5)
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@ExcelField(title = "借出目的", dictType="DM0036", type = 1, align = 1, sort = 6)
	public String getLendingType() {
		return lendingType;
	}
	public void setLendingType(String lendingType) {
		this.lendingType = lendingType;
	}
	@ExcelField(title = "客户名称", type = 1, align = 1, sort = 7)
	public String getLendingName() {
		return lendingName;
	}
	public void setLendingName(String lendingName) {
		this.lendingName = lendingName;
	}
	@ExcelField(title = "行业", dictType="DM0002", type = 1, align = 1, sort = 8)
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	@ExcelField(title = "备注", type = 1, align = 1, sort = 9)
	public String getNewRemarks() {
		return newRemarks;
	}
	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	@ExcelField(title = "相关配件备注", type = 1, align = 1, sort = 10)
	public String getAccessoriesRemarks() {
		return accessoriesRemarks;
	}
	public void setAccessoriesRemarks(String accessoriesRemarks) {
		this.accessoriesRemarks = accessoriesRemarks;
	}
	@ExcelField(title = "借出时间", type = 1, align = 1, sort = 11)
	public String getLendingDateFrom() {
		return lendingDateFrom;
	}
	public void setLendingDateFrom(String lendingDateFrom) {
		this.lendingDateFrom = lendingDateFrom;
	}
	@ExcelField(title = "借出到期日", type = 1, align = 1, sort = 12)
	public String getLendingDateTo() {
		return lendingDateTo;
	}
	public void setLendingDateTo(String lendingDateTo) {
		this.lendingDateTo = lendingDateTo;
	}
	@ExcelField(title = "借出负责人", type = 1, align = 1, sort = 13)
	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}
	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}
}