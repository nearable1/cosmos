/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 其他入库申请_出入库申请明细Entity
 * @author zhanglulu
 * @version 2017-10-23
 */
@Alias("InSmStorageAppDtl")
public class SmStorageAppDtl extends DataEntity<SmStorageAppDtl> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String lendingId;		// 借出ID
	private String appId;		// 申请ID
	private String purchaseNo;		// 采购订单号
	private String orderNo;		// 合同号 :
	private String lineNo;		// 行号
	private String snNo;		// SN
	private String materialNo;		// 物料号
	private String materialName;		// 物料名称
	private String newSnNo;		// 新SN
	private String num;		// 数量
	private String warehouse;		// 库房 : 代码
	private String address;		// 地址
	private String contactsName;		// 联系人
	private String telephone;		// 电话
	private String accessoriesRemarks;		// 配件备注 : 借出相关配件
	private Date lendingDateTo;		// 借出日期止
	private Date extendDateTo;		// 延长到期日
	private String extendReason;		// 延长原因
	private Date productionDate;		// 生产日期
	
	private SmLendingMat smLendingMat;	// 借出物料
	private SmStorageInfo smStorageInfo;	// 出入库信息
	
	private SoOrderDtl soOrderDtl;	// 发货信息（换货/退货用）

	private String ifSn;		// 是否有SN
	
	public String getIfSn() {
		return ifSn;
	}

	public void setIfSn(String ifSn) {
		this.ifSn = ifSn;
	}

	public SoOrderDtl getSoOrderDtl() {
		return soOrderDtl;
	}

	public void setSoOrderDtl(SoOrderDtl soOrderDtl) {
		this.soOrderDtl = soOrderDtl;
	}

	public SmLendingMat getSmLendingMat() {
		return smLendingMat;
	}

	public void setSmLendingMat(SmLendingMat smLendingMat) {
		this.smLendingMat = smLendingMat;
	}

	public SmStorageInfo getSmStorageInfo() {
		return smStorageInfo;
	}

	public void setSmStorageInfo(SmStorageInfo smStorageInfo) {
		this.smStorageInfo = smStorageInfo;
	}

	public SmStorageAppDtl() {
		super();
	}

	public SmStorageAppDtl(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	
	@Length(min=0, max=64, message="借出ID长度必须介于 0 和 64 之间")
	public String getLendingId() {
		return lendingId;
	}

	public void setLendingId(String lendingId) {
		this.lendingId = lendingId;
	}
	
	@Length(min=1, max=64, message="申请ID长度必须介于 1 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=50, message="采购订单号长度必须介于 0 和 50 之间")
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
	@Length(min=0, max=50, message="合同号 :长度必须介于 0 和 50 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=2, message="行号长度必须介于 0 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=0, max=50, message="SN长度必须介于 0 和 50 之间")
	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	
	@Length(min=0, max=50, message="物料号长度必须介于 0 和 50 之间")
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

	@Length(min=0, max=50, message="新SN长度必须介于 0 和 50 之间")
	public String getNewSnNo() {
		return newSnNo;
	}

	public void setNewSnNo(String newSnNo) {
		this.newSnNo = newSnNo;
	}
	
	@Length(min=0, max=3, message="数量长度必须介于 0 和 3 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=2, message="库房 : 代码长度必须介于 0 和 2 之间")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@Length(min=0, max=300, message="地址长度必须介于 0 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	@Length(min=0, max=300, message="配件备注 : 借出相关配件长度必须介于 0 和 300 之间")
	public String getAccessoriesRemarks() {
		return accessoriesRemarks;
	}

	public void setAccessoriesRemarks(String accessoriesRemarks) {
		this.accessoriesRemarks = accessoriesRemarks;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLendingDateTo() {
		return lendingDateTo;
	}

	public void setLendingDateTo(Date lendingDateTo) {
		this.lendingDateTo = lendingDateTo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExtendDateTo() {
		return extendDateTo;
	}

	public void setExtendDateTo(Date extendDateTo) {
		this.extendDateTo = extendDateTo;
	}
	
	@Length(min=0, max=300, message="延长原因长度必须介于 0 和 300 之间")
	public String getExtendReason() {
		return extendReason;
	}

	public void setExtendReason(String extendReason) {
		this.extendReason = extendReason;
	}
	
}