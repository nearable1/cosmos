/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;



/**
 * 合同管理一览明细导出Entity
 * 
 * @author zhanglulu
 * @version 2017-08-24
 */
public class SoOrderExcel {

	// 文件头信息
	private String orderNo; // 合同编号
	private String orderDate; // 订立日期
	private String priceSystem; // 销售方式：DM0005
	private String employeeName; // 业务员
	private String cityName; // 签约地
	private String customerName; // 签约方
	private String orderType; // 合同类型：DM0008
	private String currency;		// 币种：DM0009
	private String exchangeRate;		// 汇率
	private String commission;		// 佣金/扣除费用
	private String receiveStatus; // 收款状态：DM0011
	private String invoiceStatus; // 开票状态：DM0012
	private String deliverStatus; // 发货状态: DM0010
	private String invoiceDate;		// 开票月
	private String paymaentCon;		// 支付条件: DM0057
	private String conditionRemarks;		// 条件备注

	private String customerChName;		// 最终客户
	private String industry;	// 行业：DM0002
	private String customerDiff;	// 具体分类：DM0003
	private String customerSegmentation;		// 科室/系：DM0039
	private String provinceName;	// 省市
	private String dtlCityName;	// 城市
	private String region;	// 地区：DM0049
	private String responsiblePersonName;		// 地区负责人
	private String organizeName;		// 组
	private String materialName;		// 物料名称
	private String num;		// 数量
	private String unitPrice;		// 单价
	private String totalAmount;		// 金额
	private String warrantyPeriod;		// 质保期（年）
	private String extendedWarrPeriod;		// 延保（年）
	private String ifEffective;		// 行作废：yes_no
	private String invoiceNum;		// 已开票数量
	private String deliverNum;		// 已发货数量

	// 文件头信息
	@ExcelField(title="合同编号", type=1, align=2, sort=1)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ExcelField(title="地区", type=1, align=2, dictType="DM0049", sort=2)
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	@ExcelField(title="省市", type=1, align=2, sort=3)
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@ExcelField(title="城市", type=1, align=2, sort=4)
	public String getDtlCityName() {
		return dtlCityName;
	}
	public void setDtlCityName(String dtlCityName) {
		this.dtlCityName = dtlCityName;
	}

	@ExcelField(title="行业", type=1, align=2, dictType="DM0002", sort=5)
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@ExcelField(title="具体分类", type=1, align=2, dictType="DM0003", sort=6)
	public String getCustomerDiff() {
		return customerDiff;
	}
	public void setCustomerDiff(String customerDiff) {
		this.customerDiff = customerDiff;
	}

	@ExcelField(title="科室/系", type=1, align=2, dictType="DM0039", sort=7)
	public String getCustomerSegmentation() {
		return customerSegmentation;
	}
	public void setCustomerSegmentation(String customerSegmentation) {
		this.customerSegmentation = customerSegmentation;
	}

	@ExcelField(title="合同类型", type=1, align=2, dictType="DM0008", sort=8)
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@ExcelField(title="币种", type=1, align=2, dictType="DM0009", sort=9)
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@ExcelField(title="汇率", type=1, align=2, sort=10)
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@ExcelField(title="扣除部分", type=1, align=2, sort=11)
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}

	@ExcelField(title="销售方式", type=1, align=2, dictType="DM0005", sort=12)
	public String getPriceSystem() {
		return priceSystem;
	}
	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}

	@ExcelField(title="签约地", type=1, align=2, sort=13)
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@ExcelField(title="签约方", type=1, align=2, sort=14)
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@ExcelField(title="最终客户", type=1, align=2, sort=15)
	public String getCustomerChName() {
		return customerChName;
	}
	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}

	@ExcelField(title="物料名称", type=1, align=2, sort=16)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@ExcelField(title="数量", type=1, align=2, sort=17)
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

	@ExcelField(title="单价", type=1, align=2, sort=18)
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@ExcelField(title="金额", type=1, align=2, sort=19)
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@ExcelField(title="订立日期", type=1, align=2, sort=20)
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@ExcelField(title="开票月", type=1, align=2, sort=21)
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	@ExcelField(title="支付条件", type=1, align=2, dictType="DM0057", sort=22)
	public String getPaymaentCon() {
		return paymaentCon;
	}
	public void setPaymaentCon(String paymaentCon) {
		this.paymaentCon = paymaentCon;
	}
	
	@ExcelField(title="支付条件/备注", type=1, align=2, sort=23)
	public String getConditionRemarks() {
		return conditionRemarks;
	}
	public void setConditionRemarks(String conditionRemarks) {
		this.conditionRemarks = conditionRemarks;
	}

	@ExcelField(title="业务员", type=1, align=2, sort=24)
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@ExcelField(title="地区负责人", type=1, align=2, sort=25)
	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}
	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	@ExcelField(title="组", type=1, align=2, sort=26)
	public String getOrganizeName() {
		return organizeName;
	}
	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	@ExcelField(title="质保期（年）", type=1, align=2, sort=27)
	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}
	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	@ExcelField(title="延保（年）", type=1, align=2, sort=28)
	public String getExtendedWarrPeriod() {
		return extendedWarrPeriod;
	}
	public void setExtendedWarrPeriod(String extendedWarrPeriod) {
		this.extendedWarrPeriod = extendedWarrPeriod;
	}

	@ExcelField(title="开票状态", type=1, align=2, dictType="DM0012", sort=29)
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@ExcelField(title="收款状态", type=1, align=2, dictType="DM0011", sort=30)
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	@ExcelField(title="发货状态", type=1, align=2, dictType="DM0010", sort=31)
	public String getDeliverStatus() {
		return deliverStatus;
	}
	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	@ExcelField(title="已发货数量", type=1, align=2, sort=32)
	public String getDeliverNum() {
		return deliverNum;
	}
	public void setDeliverNum(String deliverNum) {
		this.deliverNum = deliverNum;
	}
	@ExcelField(title="行作废", type=1, align=2, dictType="yes_no", sort=33)
	public String getIfEffective() {
		return ifEffective;
	}
	public void setIfEffective(String ifEffective) {
		this.ifEffective = ifEffective;
	}

	@ExcelField(title="已开票数量", type=1, align=2, sort=34)
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
}