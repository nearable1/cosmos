/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 采购入库查询结果Entity
 * 
 * @author yangj
 * @version 2017-07-24
 */
public class PmPurchaseStorage {

	private String id;         // id编号
	private String purchaseNo; // 采购订单号
	private String purchaseType; // 采购类型 : 代码
	private String materialNo; // 物料号
	private Integer num; // 数量
	private Integer arrivalNum; // 本次到货数量
	private String warehouse; // 库房
	private Date entryDate; // 入库日期
    private Date updateDate; // 更新日时

	public PmPurchaseStorage() {
		super();
	}
	
	public PmPurchaseStorage(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getArrivalNum() {
		return arrivalNum;
	}

	public void setArrivalNum(Integer arrivalNum) {
		this.arrivalNum = arrivalNum;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}