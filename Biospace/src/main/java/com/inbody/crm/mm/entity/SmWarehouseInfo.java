/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.entity;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 入库申请库存信息Entity
 * @author zhanglulu
 * @version 2017-10-27
 */
@Alias("InSmWarehouseInfo")
public class SmWarehouseInfo extends DataEntity<SmWarehouseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String snNo;		// SN
	private String materialNo;		// 物料号
	private String warehouse;		// 库房 : 代码
	private String storageId;		// 出入库履历编号 : storage_type(2)+流水(8)
	private String inStockStatus;		// 在库状态 : 代码
	private String num;		// 数量
	private String occupationNo;		// 占用数量
	
	public SmWarehouseInfo() {
		super();
	}

	public SmWarehouseInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
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
	
	@Length(min=0, max=2, message="库房 : 代码长度必须介于 0 和 2 之间")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@Length(min=1, max=15, message="出入库履历编号 : storage_type(2)+流水(8)长度必须介于 1 和 15 之间")
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	
	@Length(min=1, max=5, message="在库状态 : 代码长度必须介于 1 和 5 之间")
	public String getInStockStatus() {
		return inStockStatus;
	}

	public void setInStockStatus(String inStockStatus) {
		this.inStockStatus = inStockStatus;
	}
	
	@Length(min=0, max=3, message="数量长度必须介于 0 和 3 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=5, message="占用数量长度必须介于 0 和 5 之间")
	public String getOccupationNo() {
		return occupationNo;
	}

	public void setOccupationNo(String occupationNo) {
		this.occupationNo = occupationNo;
	}
	
}