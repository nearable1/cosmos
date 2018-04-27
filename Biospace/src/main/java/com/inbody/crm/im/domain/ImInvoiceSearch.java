/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.im.domain;

import java.math.BigDecimal;


//import java.util.List;
//
//import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 发票查询结果Entity
 * 
 * @author zhanglulu
 * @version 2017-08-15
 */
public class ImInvoiceSearch extends DataEntity<ImInvoiceSearch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4156751069073237797L;
	private String invoiceSource; // 发票来源
	private String orderNo; // 单据编号
	private String paymaentCon; // 支付条件
	private String orderDate; // 订立日期
	private String axCusNo; // AX编号
	private String materialNo; // 物料号
	private String materialName; // 物料名称
	private String invoiceType; // 发票类型
	private String invoiceDateBegin; // 开票日期开始
	private String invoiceDateEnd; // 开票日期结束
	private String invoiceTitle; // 发票抬头
	private String invoiceNo; // 发票编号
	private String organize; // 组
	private String commissionPeison; // 提成人
	private String selectCommissionPeison; // 提成人
	
	private String customerName; // 客户
	private String employeeName;	 // 业务员
	private String responsiblePersonName; // 地区负责人
	private String lineNo; // 明细行号
	private BigDecimal unitPrice; // 单价
	private Integer num; // 数量
	private BigDecimal invoiceAmount; // 开票金额
	private BigDecimal tax; // 税金
	private String invoiceDate; // 开票日期
	private String ticketMethod; // 取票方式
	private String recipients; // 收件人
	private String repTelephone; // 联系电话
	private String address; // 收件地址
	private String expressNo; // 快递编号
	private String expressCompany; // 快递公司
	public String getInvoiceSource() {
		return invoiceSource;
	}
	public void setInvoiceSource(String invoiceSource) {
		this.invoiceSource = invoiceSource;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPaymaentCon() {
		return paymaentCon;
	}
	public void setPaymaentCon(String paymaentCon) {
		this.paymaentCon = paymaentCon;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getAxCusNo() {
		return axCusNo;
	}
	public void setAxCusNo(String axCusNo) {
		this.axCusNo = axCusNo;
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
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceDateBegin() {
		return invoiceDateBegin;
	}
	public void setInvoiceDateBegin(String invoiceDateBegin) {
		this.invoiceDateBegin = invoiceDateBegin;
	}
	public String getInvoiceDateEnd() {
		return invoiceDateEnd;
	}
	public void setInvoiceDateEnd(String invoiceDateEnd) {
		this.invoiceDateEnd = invoiceDateEnd;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getOrganize() {
		return organize;
	}
	public void setOrganize(String organize) {
		this.organize = organize;
	}
	public String getCommissionPeison() {
		return commissionPeison;
	}
	public void setCommissionPeison(String commissionPeison) {
		this.commissionPeison = commissionPeison;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}
	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}
	public String getSelectCommissionPeison() {
		return selectCommissionPeison;
	}
	public void setSelectCommissionPeison(String selectCommissionPeison) {
		this.selectCommissionPeison = selectCommissionPeison;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getTicketMethod() {
		return ticketMethod;
	}
	public void setTicketMethod(String ticketMethod) {
		this.ticketMethod = ticketMethod;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public String getRepTelephone() {
		return repTelephone;
	}
	public void setRepTelephone(String repTelephone) {
		this.repTelephone = repTelephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

}