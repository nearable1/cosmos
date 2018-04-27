/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 合同信息录入Entity
 * @author zhanglulu
 * @version 2017-08-22
 */
public class SoOrderDtl extends DataEntity<SoOrderDtl> {
	
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
	private String packageMertiralName;		// 套餐物料名称
	private String num;		// 数量
	private String deliverNum;		// 已发货量
	private String standardPrice;		// 标准价
	private String unitPrice;		// 单价
	private String totalAmount;		// 总金额
	private String endCustomerId;		// 最终客户
	private String customerSegmentation;		// 客户细分
	private String warrantyPeriod;		// 质保期 : 单位-年
	private String extendedWarrPeriod;		// 延保期 : 单位-年
	private String ifEffective;		// 是否有效
	private String ifReturn;		// 是否退货
	private String invoiceNum;		// 已开票数量
	
	//客户相关信息
	private String customerChName;		// 最终客户名
	private String industry;	// 行业
	private String customerDiff;	// 具体分类
	private String region;	// 地区
	private String province;	// 省市
	private String provinceName;	// 省市
	private String city;	// 城市
	private String cityName;	// 城市

	private Date invoiceDate;		// 开票日期
	
	public SoOrderDtl() {
		super();
	}

	public SoOrderDtl(String orderId){
		this.orderId = orderId;
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
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
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=1, max=50, message="负责人长度必须介于 1 和 50 之间")
	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
	@Length(min=1, max=50, message="提成人长度必须介于 1 和 50 之间")
	public String getCommissionPeisonId() {
		return commissionPeisonId;
	}

	public void setCommissionPeisonId(String commissionPeisonId) {
		this.commissionPeisonId = commissionPeisonId;
	}
	
	@Length(min=0, max=30, message="组别长度必须介于 0 和 30 之间")
	public String getOrganize() {
		return organize;
	}

	public void setOrganize(String organize) {
		this.organize = organize;
	}
	
	@Length(min=0, max=50, message="物料号长度必须介于 0 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@Length(min=0, max=50, message="套餐物料长度必须介于 0 和 50 之间")
	public String getPackageMertiralNo() {
		return packageMertiralNo;
	}

	public void setPackageMertiralNo(String packageMertiralNo) {
		this.packageMertiralNo = packageMertiralNo;
	}
	
	public String getPackageMertiralName() {
		return packageMertiralName;
	}

	public void setPackageMertiralName(String packageMertiralName) {
		this.packageMertiralName = packageMertiralName;
	}

	@Length(min=0, max=3, message="数量长度必须介于 0 和 3 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=5, message="已发货量长度必须介于 0 和 5 之间")
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
	
	@Length(min=0, max=50, message="最终客户长度必须介于 0 和 50 之间")
	public String getEndCustomerId() {
		return endCustomerId;
	}

	public void setEndCustomerId(String endCustomerId) {
		this.endCustomerId = endCustomerId;
	}
	
	@Length(min=0, max=4, message="客户细分长度必须介于 0 和 4 之间")
	public String getCustomerSegmentation() {
		return customerSegmentation;
	}

	public void setCustomerSegmentation(String customerSegmentation) {
		this.customerSegmentation = customerSegmentation;
	}
	
	@Length(min=0, max=2, message="质保期 : 单位-年长度必须介于 0 和 2 之间")
	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}
	
	@Length(min=0, max=2, message="延保期 : 单位-年长度必须介于 0 和 2 之间")
	public String getExtendedWarrPeriod() {
		return extendedWarrPeriod;
	}

	public void setExtendedWarrPeriod(String extendedWarrPeriod) {
		this.extendedWarrPeriod = extendedWarrPeriod;
	}
	
	@Length(min=1, max=1, message="是否有效长度必须介于 1 和 1 之间")
	public String getIfEffective() {
		return ifEffective;
	}

	public void setIfEffective(String ifEffective) {
		this.ifEffective = ifEffective;
	}
	
	@Length(min=1, max=1, message="是否退货长度必须介于 1 和 1 之间")
	public String getIfReturn() {
		return ifReturn;
	}

	public void setIfReturn(String ifReturn) {
		this.ifReturn = ifReturn;
	}
	
	@Length(min=0, max=3, message="已开票数量长度必须介于 0 和 3之间")
	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getCustomerChName() {
		return customerChName;
	}

	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCustomerDiff() {
		return customerDiff;
	}

	public void setCustomerDiff(String customerDiff) {
		this.customerDiff = customerDiff;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	public String getCommissionPeisonName() {
		return commissionPeisonName;
	}

	public void setCommissionPeisonName(String commissionPeisonName) {
		this.commissionPeisonName = commissionPeisonName;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
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

	private String invoiceNumMax;		// 最大已开票数量
	private String deliverNumMax;		// 最大已发货量

	public String getInvoiceNumMax() {
		return invoiceNumMax;
	}

	public void setInvoiceNumMax(String invoiceNumMax) {
		this.invoiceNumMax = invoiceNumMax;
	}

	public String getDeliverNumMax() {
		return deliverNumMax;
	}

	public void setDeliverNumMax(String deliverNumMax) {
		this.deliverNumMax = deliverNumMax;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
}