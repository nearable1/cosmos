/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 已发货合同明细Entity
 * @author zhanglulu
 * @version 2017-09-11
 */
@Alias("InSoOrderDtl")
public class SoOrderDtl extends DataEntity<SoOrderDtl> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String orderId;		// 合同行ID : so_order.id,仅合同明细表以此与合同头表关联 父类
	private String lineNo;		// 行号
	private String responsiblePersonId;		// 负责人
	private String responsiblePersonName;		// 负责人
	private String commissionPeisonId;		// 提成人
	private String commissionPeisonName;		// 提成人
	private String organize;		// 组别
	private String organizeName;		// 组别
	private String materialNo;		// 物料号
	private String materialName;		// 物料号
	private String model;		// 物料型号
	private String materialType;		// 物料类型
	private String packageMertiralNo;		// 套餐物料
	private String num;		// 数量
	private String deliverNum;		// 已发货量
	private String standardPrice;		// 标准价
	private String unitPrice;		// 单价
	private String totalAmount;		// 总金额
	private String customerSegmentation;		// 客户细分
	private String warrantyPeriod;		// 质保期 : 单位-年
	private String extendedWarrPeriod;		// 延保期 : 单位-年
	private String ifEffective;		// 是否有效
	private String ifReturn;		// 是否退货
	private String invoiceNum;		// 已开票数量

	private String orderNo;		// 合同号 :
	private String customerId;		// 签约方
	private String customerChName;		// 签约方
	private String endCustomerId;		// 最终客户
	private String endCustomerChName;		// 最终客户

	private String snNo;		// SN
	private Date productionDate; // 生产日期
	
	private Date processDate; // 发货日期
	private Date warrantyDateTo; // 保修到期日

	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
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
	public String getCommissionPeisonId() {
		return commissionPeisonId;
	}
	public void setCommissionPeisonId(String commissionPeisonId) {
		this.commissionPeisonId = commissionPeisonId;
	}
	public String getCommissionPeisonName() {
		return commissionPeisonName;
	}
	public void setCommissionPeisonName(String commissionPeisonName) {
		this.commissionPeisonName = commissionPeisonName;
	}
	public String getOrganize() {
		return organize;
	}
	public void setOrganize(String organize) {
		this.organize = organize;
	}
	public String getOrganizeName() {
		return organizeName;
	}
	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getPackageMertiralNo() {
		return packageMertiralNo;
	}
	public void setPackageMertiralNo(String packageMertiralNo) {
		this.packageMertiralNo = packageMertiralNo;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDeliverNum() {
		return deliverNum;
	}
	public void setDeliverNum(String deliverNum) {
		this.deliverNum = deliverNum;
	}
	public String getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCustomerSegmentation() {
		return customerSegmentation;
	}
	public void setCustomerSegmentation(String customerSegmentation) {
		this.customerSegmentation = customerSegmentation;
	}
	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}
	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}
	public String getExtendedWarrPeriod() {
		return extendedWarrPeriod;
	}
	public void setExtendedWarrPeriod(String extendedWarrPeriod) {
		this.extendedWarrPeriod = extendedWarrPeriod;
	}
	public String getIfEffective() {
		return ifEffective;
	}
	public void setIfEffective(String ifEffective) {
		this.ifEffective = ifEffective;
	}
	public String getIfReturn() {
		return ifReturn;
	}
	public void setIfReturn(String ifReturn) {
		this.ifReturn = ifReturn;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerChName() {
		return customerChName;
	}
	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}
	public String getEndCustomerId() {
		return endCustomerId;
	}
	public void setEndCustomerId(String endCustomerId) {
		this.endCustomerId = endCustomerId;
	}
	public String getEndCustomerChName() {
		return endCustomerChName;
	}
	public void setEndCustomerChName(String endCustomerChName) {
		this.endCustomerChName = endCustomerChName;
	}
	public String getSnNo() {
		return snNo;
	}
	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public Date getWarrantyDateTo() {
		return warrantyDateTo;
	}
	public void setWarrantyDateTo(Date warrantyDateTo) {
		this.warrantyDateTo = warrantyDateTo;
	}
}