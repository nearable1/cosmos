/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.im.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * AX销售发票一览导出excel用
 * 
 * @author zhanglulu
 * @version 2017-08-15
 */
public class AxImInvoiceDtlExcel {

	private String onlineOrderNo;	// 销售订单头编号
	private String customerAccount;	// 客户编号
	private String invoiceAccount;	// 开票编号
	private String requestedShipDate;	// 要求发货日期
	private String requestedReceiptDate;	// 要求收款日期
	private String currency;	// 币种
	private String payment;	// 付款条件
	private String paymentDueDate;	// 付款到期日
	private String itemNumber;	// 物料编号
	private String site;	// site
	private String warehouse;	// 库房
	private String set;	// 套
	private String orderlinetext;	// 行说明
	private String quantity;	// 数量
	private String unit;	// 单位
	private String unitprice;	// 单价
	private String discount;	// 折扣
	private String discountPercent;	// 折扣率
	private String deliveryaddress;	// 寄送地址
	private String deliverycontact;	// 收货联系人
	private String modeofdelivery;	// 寄送方式
	private String deliveryterms;	// 寄送条件
	private String businessUnit;	// 业务单位
	private String department;	// 部门
	private String worker;	// 销售员
	private String endCustomer;	// 最终客户
	
	@ExcelField(title = "Online Order No", type = 1, align = 1, sort = 1)
	public String getOnlineOrderNo() {
		return onlineOrderNo;
	}
	public void setOnlineOrderNo(String onlineOrderNo) {
		this.onlineOrderNo = onlineOrderNo;
	}
	
	@ExcelField(title = "Customer Account", type = 1, align = 2, sort = 2)
	public String getCustomerAccount() {
		return customerAccount;
	}
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}
	
	@ExcelField(title = "Invoice Account", type = 1, align = 2, sort = 3)
	public String getInvoiceAccount() {
		return invoiceAccount;
	}
	public void setInvoiceAccount(String invoiceAccount) {
		this.invoiceAccount = invoiceAccount;
	}
	
	@ExcelField(title = "Requested Ship Date", type = 1, align = 2, sort = 4)
	public String getRequestedShipDate() {
		return requestedShipDate;
	}
	public void setRequestedShipDate(String requestedShipDate) {
		this.requestedShipDate = requestedShipDate;
	}
	
	@ExcelField(title = "Requested Receipt Date", type = 1, align = 2, sort = 5)
	public String getRequestedReceiptDate() {
		return requestedReceiptDate;
	}
	public void setRequestedReceiptDate(String requestedReceiptDate) {
		this.requestedReceiptDate = requestedReceiptDate;
	}
	
	@ExcelField(title = "Currency", type = 1, align = 2, sort = 6)
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@ExcelField(title = "Payment", type = 1, align = 2, sort = 7)
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	@ExcelField(title = "Payment Due Date", type = 1, align = 2, sort = 8)
	public String getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	
	@ExcelField(title = "Item Number", type = 1, align = 2, sort = 9)
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	@ExcelField(title = "Site", type = 1, align = 2, sort = 10)
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	
	@ExcelField(title = "warehouse", type = 1, align = 2, sort = 11)
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@ExcelField(title = "Set", type = 1, align = 2, sort = 12)
	public String getSet() {
		return set;
	}
	public void setSet(String set) {
		this.set = set;
	}
	
	@ExcelField(title = "Order line text", type = 1, align = 2, sort = 13)
	public String getOrderlinetext() {
		return orderlinetext;
	}
	public void setOrderlinetext(String orderlinetext) {
		this.orderlinetext = orderlinetext;
	}
	
	@ExcelField(title = "Quantity", type = 1, align = 2, sort = 14)
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@ExcelField(title = "Unit", type = 1, align = 2, sort = 15)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title = "Unit price", type = 1, align = 2, sort = 16)
	public String getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}
	
	@ExcelField(title = "Discount", type = 1, align = 2, sort = 17)
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	@ExcelField(title = "Discount Percent", type = 1, align = 2, sort = 18)
	public String getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(String discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	@ExcelField(title = "Delivery address", type = 1, align = 1, sort = 19)
	public String getDeliveryaddress() {
		return deliveryaddress;
	}
	public void setDeliveryaddress(String deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
	}
	
	@ExcelField(title = "Delivery contact", type = 1, align = 2, sort = 20)
	public String getDeliverycontact() {
		return deliverycontact;
	}
	public void setDeliverycontact(String deliverycontact) {
		this.deliverycontact = deliverycontact;
	}
	
	@ExcelField(title = "Mode of delivery", type = 1, align = 2, sort = 21)
	public String getModeofdelivery() {
		return modeofdelivery;
	}
	public void setModeofdelivery(String modeofdelivery) {
		this.modeofdelivery = modeofdelivery;
	}
	
	@ExcelField(title = "Delivery terms", type = 1, align = 2, sort = 22)
	public String getDeliveryterms() {
		return deliveryterms;
	}
	public void setDeliveryterms(String deliveryterms) {
		this.deliveryterms = deliveryterms;
	}
	
	@ExcelField(title = "BusinessUnit", type = 1, align = 2, sort = 23)
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	
	@ExcelField(title = "Department", type = 1, align = 2, sort = 24)
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@ExcelField(title = "Worker", type = 1, align = 2, sort = 25)
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	
	@ExcelField(title = "EndCustomer", type = 1, align = 2, sort = 26)
	public String getEndCustomer() {
		return endCustomer;
	}
	public void setEndCustomer(String endCustomer) {
		this.endCustomer = endCustomer;
	}
}