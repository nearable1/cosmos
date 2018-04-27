/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 发货申请明细Entity
 * @author zhanglulu
 * @version 2017-09-11
 */
public class SoApplyDeliverDtl extends DataEntity<SoApplyDeliverDtl> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String appId;		// 申请ID
	private String orderNo;		// 合同号 :
	private String lineNo;		// 行号
	private String ifInstall;		// 是否安装
	private String num;		// 数量
	private String contactsName;		// 联系人
	private String telephone;		// 电话
	private String address;		// 地址

	private String soOrderDtlId;		// 合同明细ID
	private String customerChName;		// 最终客户
	private String materialNo;		// 物料号
	private String materialName;		// 物料名
	private String totalNum;		// 总数量
	private String deliverNum;		// 已发货数量

	private String packageMertiralNo;		// 套餐物料
	private String packageMertiralName;		// 套餐物料名称
	
	public SoApplyDeliverDtl() {
		super();
	}

	public SoApplyDeliverDtl(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=64, message="申请ID长度必须介于 1 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=1, max=50, message="合同号 :长度必须介于 1 和 50 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=1, max=1, message="是否安装长度必须介于 1 和 1 之间")
	public String getIfInstall() {
		return ifInstall;
	}

	public void setIfInstall(String ifInstall) {
		this.ifInstall = ifInstall;
	}
	
	@Length(min=1, max=3, message="数量长度必须介于 1 和 3 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=100, message="联系人长度必须介于 0 和 100 之间")
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSoOrderDtlId() {
		return soOrderDtlId;
	}

	public void setSoOrderDtlId(String soOrderDtlId) {
		this.soOrderDtlId = soOrderDtlId;
	}

	public String getCustomerChName() {
		return customerChName;
	}

	public void setCustomerChName(String customerChName) {
		this.customerChName = customerChName;
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

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getDeliverNum() {
		return deliverNum;
	}

	public void setDeliverNum(String deliverNum) {
		this.deliverNum = deliverNum;
	}

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
	
}