/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;


/**
 * 合同明细导出Entity
 * 
 * @author zhanglulu
 * @version 2017-08-24
 */
public class SoOrderDtlExcel {

	private String index; // 序号
	private String materialName; // 物料名
	private String unitPrice; // 单价
	private String num; // 数量
	private String totalAmount; // 总金额
	private String model; // 型号
	private String total; // 合计
	private String ucTotal; // 大写合计

	@ExcelField(title="序号", type=1, align=2, sort=1)
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	@ExcelField(title="产品名称", type=1, align=1, sort=2)
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@ExcelField(title="型号", type=1, align=1, sort=3)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@ExcelField(title="单价（元）", type=1, align=3, sort=4)
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@ExcelField(title="数量", type=1, align=3, sort=5)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@ExcelField(title="总价（元）", type=1, align=3, sort=6)
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@ExcelField(title="", mergedRegion=true, firstCol=0, lastCol=1, type=1, align=2, sort=7)
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@ExcelField(title="", mergedRegion=true, firstCol=2, lastCol=5, type=1, align=2, sort=8)
	public String getUcTotal() {
		return ucTotal;
	}

	public void setUcTotal(String ucTotal) {
		this.ucTotal = ucTotal;
	}

}