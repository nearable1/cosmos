/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 采购订单导出excel用
 * @author yangj
 * @version 2017-07-24
 */
public class PmPurchaseOrdDtlExcel {

	private String materialNo; // 物料号
	private String materialName; // 物料名
	private Integer num; // 数量
	private String unitPrice; // 单价
	private String totalAmount; // 总金额

	//@SupCol(text="物料号", sort = 1, minWidth="125px")
	@ExcelField(title="物料号", type=1, align=1, sort=1)
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	//@SupCol(text="物料名称", sort = 2, minWidth="125px")
	@ExcelField(title="物料名称", type=1, align=1, sort=2)
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	//@SupCol(text="数量", sort = 3, minWidth="125px")
	@ExcelField(title="数量", type=1, align=3, sort=3)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	//@SupCol(text="单价", sort = 4, minWidth="125px")
	@ExcelField(title="单价", type=1, align=3, sort=4)
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	//@SupCol(text="合计", sort = 5, minWidth="125px")
	@ExcelField(title="合计", type=1, align=3, sort=5)
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

}