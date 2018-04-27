/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.im.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 发票一览导出excel用
 * 
 * @author zhanglulu
 * @version 2017-08-15
 */
public class ImInvoiceDtlExcel {

	private String invoiceSource; // 发票来源
	private String orderNo; // 单据编号
	private String customerName; // 客户
	private String materialName; // 物料名称
	private String employeeName; // 业务员
	private String responsiblePersonName; // 地区负责人
	private String commissionPeison; // 提成人
	private String organize; // 组
	private String lineNo; // 明细行号
	private String unitPrice; // 单价
	private String num; // 数量
	private String invoiceType; // 发票类型
	private String invoiceAmount; // 开票金额
	private String tax; // 税金
	private String invoiceDate; // 开票日期
	private String invoiceNo; // 发票编号
	private String invoiceTitle; // 发票抬头
	private String ticketMethod; // 取票方式
	private String recipients; // 收件人
	private String repTelephone; // 联系电话
	private String address; // 收件地址
	private String expressNo; // 快递编号
	private String expressCompany; // 快递公司
	
	@ExcelField(title = "发票来源", type = 1, align = 1, sort = 1, dictType="DM0052")
	public String getInvoiceSource() {
		return invoiceSource;
	}
	public void setInvoiceSource(String invoiceSource) {
		this.invoiceSource = invoiceSource;
	}
	
	@ExcelField(title = "单据编号", type = 1, align = 1, sort = 2)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title = "客户", type = 1, align = 1, sort = 3)
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@ExcelField(title = "物料名称", type = 1, align = 1, sort = 4)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@ExcelField(title = " 业务员", type = 1, align = 1, sort = 5)
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@ExcelField(title = " 地区负责人", type = 1, align = 1, sort = 6)
	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}
	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}
	
	@ExcelField(title = "提成人", type = 1, align = 1, sort = 7)
	public String getCommissionPeison() {
		return commissionPeison;
	}
	public void setCommissionPeison(String commissionPeison) {
		this.commissionPeison = commissionPeison;
	}
	
	@ExcelField(title = "组", type = 1, align = 1, sort = 8)
	public String getOrganize() {
		return organize;
	}
	public void setOrganize(String organize) {
		this.organize = organize;
	}
	
	@ExcelField(title = "明细行号", type = 1, align = 1, sort = 9)
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@ExcelField(title = "单价", type = 1, align = 3, sort = 10)
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@ExcelField(title = "数量", type = 1, align = 3, sort = 11)
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	@ExcelField(title = "发票类型", type = 1, align = 1, sort = 12, dictType="DM0004")
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	@ExcelField(title = "开票金额", type = 1, align = 3, sort = 13)
	public String getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
	@ExcelField(title = "税金", type = 1, align = 3, sort = 14)
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	
	@ExcelField(title = "开票日期", type = 1, align = 1, sort = 15)
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	@ExcelField(title = "发票编号", type = 1, align = 1, sort = 16)
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	@ExcelField(title = "发票抬头", type = 1, align = 1, sort = 17)
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	
	@ExcelField(title = "取票方式", type = 1, align = 1, sort = 18, dictType="DM0048")
	public String getTicketMethod() {
		return ticketMethod;
	}
	public void setTicketMethod(String ticketMethod) {
		this.ticketMethod = ticketMethod;
	}
	
	@ExcelField(title = "收件人", type = 1, align = 1, sort = 19)
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	
	@ExcelField(title = "联系电话", type = 1, align = 1, sort = 20)
	public String getRepTelephone() {
		return repTelephone;
	}
	public void setRepTelephone(String repTelephone) {
		this.repTelephone = repTelephone;
	}
	
	@ExcelField(title = "收件地址", type = 1, align = 1, sort = 21)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title = "快递编号", type = 1, align = 1, sort = 22)
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	
	@ExcelField(title = "快递公司", type = 1, align = 1, sort = 23)
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
}