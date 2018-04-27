/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author NSSOL
 * @version 2017-07-28
 */
public class SmMatInfo extends DataEntity<SmMatInfo> {
	
	private static final long serialVersionUID = 1L;
	private String materialNo;		// 物料号
	private String materialName;		// 物料名称
	private String model;       // 型号
	private String newRemarks;  // 备注
	private String unit;		// 单位
	private String ifSn;		// 是否有SN
	private String ifVirtualSn;		// 是否自动生成虚拟SN号
	private String materialType;		// 物料类型 : 代码
	private String oldMaterialNo;		// 原物料号
	private String safetyStock;		// 安全库存量
	
	public SmMatInfo() {
		super();
	}

	public SmMatInfo(String id){
		super(id);
	}
	
	@Length(min=1, max=50, message="物料号长度必须介于 1 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@Length(min=1, max=300, message="物料名称长度必须介于 1 和 300 之间")
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

	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}

	@Length(min=1, max=10, message="单位长度必须介于 1 和 10 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=1, max=1, message="是否有SN长度必须介于 1 和 1 之间")
	public String getIfSn() {
		return ifSn;
	}

	public void setIfSn(String ifSn) {
		this.ifSn = ifSn;
	}
	
	@Length(min=1, max=1, message="是否自动生成虚拟SN号长度必须介于 1 和 1 之间")
	public String getIfVirtualSn() {
		return ifVirtualSn;
	}

	public void setIfVirtualSn(String ifVirtualSn) {
		this.ifVirtualSn = ifVirtualSn;
	}
	
	@Length(min=1, max=2, message="物料类型 : 代码长度必须介于 1 和 2 之间")
	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	@Length(min=0, max=50, message="原物料号长度必须介于 0 和 50 之间")
	public String getOldMaterialNo() {
		return oldMaterialNo;
	}

	public void setOldMaterialNo(String oldMaterialNo) {
		this.oldMaterialNo = oldMaterialNo;
	}
	
	@Length(min=0, max=10, message="安全库存量长度必须介于 0 和 10 之间")
	public String getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(String safetyStock) {
		this.safetyStock = safetyStock;
	}
	
}