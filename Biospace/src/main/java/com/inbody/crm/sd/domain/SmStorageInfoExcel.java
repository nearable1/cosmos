/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import java.util.Date;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;



/**
 * 合同出库明细导出Entity
 * 
 * @author zhanglulu
 * @version 2017-08-24
 */
public class SmStorageInfoExcel {

	// 文件头信息
	private String orderNo;		// 合同号 
	private String customerChName;		// 最终客户
	private String materialNo;		// 物料号
	private String materialName;		// 物料名称
	private String deliverNum;		// 发货数量
	private Date expectDate;		// 要求发货日
	private String deliverContactsName;		// 联系人
	private String deliverTelephone;		// 联系方式
	private String deliverAddress;		// 收件地址
	private String expressNo;		// 快递编号
	private String expressCompany;		// 快递公司
	private String snNo;		// SN
	private Date productionDate;		// 生产日期
	private String warehouse;		// 库房 : DM0050
	private Date processDate;		// 出库日期
	private String ifInstall;		// 是否需要安装: yes_no
	private Date latestInstallDate;		// 最晚安装日期
	
	@ExcelField(title="合同号", type=1, align=2, sort=1)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@ExcelField(title="最终客户", type=1, align=2, sort=2)
	public String getCustomerChName() {
		return customerChName;
	}
	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}
	@ExcelField(title="物料号", type=1, align=2, sort=3)
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	@ExcelField(title="物料名称", type=1, align=2, sort=4)
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	@ExcelField(title="发货数量", type=1, align=2, sort=5)
	public String getDeliverNum() {
		return deliverNum;
	}
	public void setDeliverNum(String deliverNum) {
		this.deliverNum = deliverNum;
	}
	@ExcelField(title="要求发货日", type=1, align=2, sort=6)
	public Date getExpectDate() {
		return expectDate;
	}
	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}
	@ExcelField(title="联系人", type=1, align=2, sort=7)
	public String getDeliverContactsName() {
		return deliverContactsName;
	}
	public void setDeliverContactsName(String deliverContactsName) {
		this.deliverContactsName = deliverContactsName;
	}
	@ExcelField(title="联系方式", type=1, align=2, sort=8)
	public String getDeliverTelephone() {
		return deliverTelephone;
	}
	public void setDeliverTelephone(String deliverTelephone) {
		this.deliverTelephone = deliverTelephone;
	}
	@ExcelField(title="收件地址", type=1, align=2, sort=9)
	public String getDeliverAddress() {
		return deliverAddress;
	}
	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}
	@ExcelField(title="快递编号", type=1, align=2, sort=10)
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	@ExcelField(title="快递公司", type=1, align=2, sort=11)
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	@ExcelField(title="S/N", type=1, align=2, sort=12)
	public String getSnNo() {
		return snNo;
	}
	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	@ExcelField(title="生产日期", type=1, align=2, sort=13)
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	@ExcelField(title="所在库房", type=1, align=2, dictType="DM0050", sort=14)
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	@ExcelField(title="出库日期", type=1, align=2, sort=15)
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	@ExcelField(title="是否需要安装", type=1, align=2, dictType="yes_no", sort=16)
	public String getIfInstall() {
		return ifInstall;
	}
	public void setIfInstall(String ifInstall) {
		this.ifInstall = ifInstall;
	}
	@ExcelField(title="最晚安装日期", type=1, align=2, sort=17)
	public Date getLatestInstallDate() {
		return latestInstallDate;
	}
	public void setLatestInstallDate(Date latestInstallDate) {
		this.latestInstallDate = latestInstallDate;
	}
}