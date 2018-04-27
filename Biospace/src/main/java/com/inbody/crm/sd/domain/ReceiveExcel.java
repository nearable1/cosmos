/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;


/**
 * 应收账款导出Entity
 * 
 * @author zhanglulu
 * @version 2017-08-24
 */
public class ReceiveExcel {

	private String orderNo; // 合同号
	private String type; // 类型
	private String customerName; // 客户名
	private String year; // 年度
	private String invoiceStatus; // 发票
	private String model; // 型号
	private String totalAmount; // 合同总金额
	private String receiveTotalAmount; // 应收款总金额
	private String deliveryDate ; // 发货日期
	private String actDate; // 正常结账日期
	private String processDate; // 验收日期
	private String gatheringType; // 付款方式
	private String aging; // 账龄
	private String employeeName; // 业务员
	
	@ExcelField(title="合同号", type=1, align=2, sort=1)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ExcelField(title="类型", type=1, align=3, sort=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ExcelField(title="客户名", type=1, align=3, sort=3)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@ExcelField(title="年度", type=1, align=3, sort=4)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@ExcelField(title="发票", dictType="DM0043", type=1, align=3, sort=5)
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@ExcelField(title="型号", type=1, align=3, sort=6)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@ExcelField(title="合同总金额", type=1, align=3, sort=7)
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@ExcelField(title="应收款总金额", type=1, align=3, sort=8)
	public String getReceiveTotalAmount() {
		return receiveTotalAmount;
	}

	public void setReceiveTotalAmount(String receiveTotalAmount) {
		this.receiveTotalAmount = receiveTotalAmount;
	}

	@ExcelField(title="发货日期", type=1, align=3, sort=9)
	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@ExcelField(title="正常结账日期", type=1, align=3, sort=10)
	public String getActDate() {
		return actDate;
	}

	public void setActDate(String actDate) {
		this.actDate = actDate;
	}

	@ExcelField(title="验收日期", type=1, align=3, sort=11)
	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	@ExcelField(title="付款方式", dictType="DM0047", type=1, align=3, sort=12)
	public String getGatheringType() {
		return gatheringType;
	}

	public void setGatheringType(String gatheringType) {
		this.gatheringType = gatheringType;
	}

	@ExcelField(title="账龄", type=1, align=3, sort=13)
	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	@ExcelField(title="业务员", type=1, align=2, sort=14)
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}