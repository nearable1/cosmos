/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author NSSOL
 * @version 2017-08-02
 */
public class RmQuotationDtl extends DataEntity<RmQuotationDtl> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String quotationNo;		// 报价单编号 : 维修编号+&ldquo;_&rdquo;+01(预报价单）/02（最终报价单）
	private String lineNo;		// 行号
	private String repairNo;		// 维修编号 : YYMMDD(6)+流水(4-年循环)
	private String itemType;		// 报价项目分类 : 代码
	private String materialNo;		// 物料号
	private String newSnNo;		// 新SN
	private String snNo;		// SN
	private BigDecimal unitPrice;		// 单价
	private Integer num;		// 数量
	private BigDecimal totalAmount;		// 总金额
	private String ifWarranty;		// 是否保内
	private BigDecimal actAmount;		// 实际金额
	private String ifPurchase;		// 是否已采购
	
	private Date repairDate;        // 维修时间
    private String statusRemarks;   // 问题说明
    private String materialName;    // 物料名称
    private Date productionDate;    // 生产日期
	
	public RmQuotationDtl() {
		super();
	}

	public RmQuotationDtl(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="报价单编号 : 维修编号+&ldquo;_&rdquo;+01(预报价单）/02（最终报价单）长度必须介于 1 和 15 之间")
	public String getQuotationNo() {
		return quotationNo;
	}

	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=1, max=15, message="维修编号 : YYMMDD(6)+流水(4-年循环)长度必须介于 1 和 15 之间")
	public String getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}
	
	@Length(min=0, max=2, message="报价项目分类 : 代码长度必须介于 0 和 2 之间")
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	@Length(min=1, max=50, message="物料号长度必须介于 1 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@Length(min=0, max=50, message="新SN长度必须介于 0 和 50 之间")
	public String getNewSnNo() {
		return newSnNo;
	}

	public void setNewSnNo(String newSnNo) {
		this.newSnNo = newSnNo;
	}
	
	@Length(min=1, max=50, message="SN长度必须介于 1 和 50 之间")
	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}
	
	@NotNull(message="单价不能为空")
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@NotNull(message="数量不能为空")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@NotNull(message="总金额不能为空")
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Length(min=0, max=1, message="是否保内长度必须介于 0 和 1 之间")
	public String getIfWarranty() {
		return ifWarranty;
	}

	public void setIfWarranty(String ifWarranty) {
		this.ifWarranty = ifWarranty;
	}
	
	public BigDecimal getActAmount() {
		return actAmount;
	}

	public void setActAmount(BigDecimal actAmount) {
		this.actAmount = actAmount;
	}
	
	@Length(min=0, max=1, message="是否已采购长度必须介于 0 和 1 之间")
	public String getIfPurchase() {
		return ifPurchase;
	}

	public void setIfPurchase(String ifPurchase) {
		this.ifPurchase = ifPurchase;
	}

	public Date getRepairDate() {
		return repairDate;
	}

	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}

	public String getStatusRemarks() {
		return statusRemarks;
	}

	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
}