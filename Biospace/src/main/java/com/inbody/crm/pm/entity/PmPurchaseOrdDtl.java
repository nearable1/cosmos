/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 主子表Entity
 * @author yangj
 * @version 2017-07-24
 */
public class PmPurchaseOrdDtl extends DataEntity<PmPurchaseOrdDtl> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程编号
	private String procInsGroupid;		// 流程组号
	private String purchaseNo;		// 采购订单号 父类
	private Integer lineNo;		// 行号
	private String quotationNo;		// 报价单编号 : 维修编号+&ldquo;_&rdquo;+01(预报价单）/02（最终报价单）
	private String qutLineNo;		// 报价单行号
	private String materialNo;		// 物料号
	private String materialName;		// 物料名
	private Integer num;		// 数量
	private String ifFoc;		// 是否FOC
	private Integer alArrivalNum;		// 已到货数量
	private Integer arrivalNum;		// 本次到货数量
	private Integer unarrivalNum;    // 未到货数量
	private Integer unpayNum;		// 未付数量
	private BigDecimal unitPrice;		// 单价
	private BigDecimal totalAmount;		// 总金额
	private BigDecimal unpayAmount;		// 未付金额
	private String warehouse;		// 库房 : 代码
	private Date entryDate;		// 入库日期
	
	private String repairNo;    // 维修编号
	private String quotationDtlId; // 报价单明细id
	private String model;      // 型号（即为主机名称）
	private String ifSn;       // 是否有sn
	private String ifVirtualSn; // 是否生成虚拟sn
	private RmQuotationDtl rmQuotationDtl; // 报价单明细
	
	public PmPurchaseOrdDtl() {
		super();
	}

	public PmPurchaseOrdDtl(String purchaseNo){
		this.purchaseNo = purchaseNo;
	}

	@Length(min=0, max=64, message="流程编号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=64, message="流程组号长度必须介于 0 和 64 之间")
	public String getProcInsGroupid() {
		return procInsGroupid;
	}

	public void setProcInsGroupid(String procInsGroupid) {
		this.procInsGroupid = procInsGroupid;
	}
	
	@Length(min=1, max=50, message="采购订单号长度必须介于 1 和 50 之间")
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=0, max=15, message="报价单编号 : 维修编号长度必须介于 0 和 15 之间")
	public String getQuotationNo() {
		return quotationNo;
	}

	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}
	
	@Length(min=0, max=2, message="报价单行号长度必须介于 0 和 2 之间")
	public String getQutLineNo() {
		return qutLineNo;
	}

	public void setQutLineNo(String qutLineNo) {
		this.qutLineNo = qutLineNo;
	}
	
	//@NotNull(message="物料号不能为空")
	//@Length(min=0, max=50, message="物料号长度必须介于 0 和 50 之间")
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

	@NotNull(message="数量不能为空")
	@Min(value=1, message="数量不能小于1")
	@Max(value=999, message="数量不能大于999")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Length(min=1, max=1, message="是否FOC长度必须介于 1 和 1 之间")
	public String getIfFoc() {
		return ifFoc;
	}

	public void setIfFoc(String ifFoc) {
		this.ifFoc = ifFoc;
	}

	//@Min(value=0, message="已到货数量不能小于0")
	//@Max(value=Integer.MAX_VALUE, message="已到货数量不能大于2147483647")
	public Integer getAlArrivalNum() {
		return alArrivalNum;
	}

	public void setAlArrivalNum(Integer alArrivalNum) {
		this.alArrivalNum = alArrivalNum;
	}

	//@Min(value=0, message="未到货数量不能小于0")
	//@Max(value=Integer.MAX_VALUE, message="未到货数量不能大于2147483647")
	public Integer getUnarrivalNum() {
		return unarrivalNum;
	}

	public void setUnarrivalNum(Integer unarrivalNum) {
		this.unarrivalNum = unarrivalNum;
	}

	//@Min(value=0, message="本次到货数量不能小于0")
	//@Max(value=Integer.MAX_VALUE, message="本次到货数量不能大于2147483647")
	public Integer getArrivalNum() {
		return arrivalNum;
	}

	public void setArrivalNum(Integer arrivalNum) {
		this.arrivalNum = arrivalNum;
	}
	
	@Min(value=0, message="未付数量不能小于0")
	@Max(value=Integer.MAX_VALUE, message="未付数量不能大于2147483647")
	public Integer getUnpayNum() {
		return unpayNum;
	}

	public void setUnpayNum(Integer unpayNum) {
		this.unpayNum = unpayNum;
	}
	
	@NotNull(message="单价不能为空")
	@Min(value=0, message="单价不能小于0")
	@Digits(integer=8, fraction=2, message="单价必须8位整数，2位小数")
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	//@Digits(integer=8, fraction=2, message="总金额必须8位整数，2位小数")
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	//@Digits(integer=8, fraction=2, message="未付金额必须8位整数，2位小数")
	public BigDecimal getUnpayAmount() {
		return unpayAmount;
	}

	public void setUnpayAmount(BigDecimal unpayAmount) {
		this.unpayAmount = unpayAmount;
	}
	
	@Length(min=0, max=2, message="库房 : 代码长度必须介于 0 和 2 之间")
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

	public String getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}

	public String getQuotationDtlId() {
		return quotationDtlId;
	}

	public void setQuotationDtlId(String quotationDtlId) {
		this.quotationDtlId = quotationDtlId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getIfSn() {
		return ifSn;
	}

	public void setIfSn(String ifSn) {
		this.ifSn = ifSn;
	}

    public String getIfVirtualSn() {
        return ifVirtualSn;
    }

    public void setIfVirtualSn(String ifVirtualSn) {
        this.ifVirtualSn = ifVirtualSn;
    }

    public RmQuotationDtl getRmQuotationDtl() {
		return rmQuotationDtl;
	}

	public void setRmQuotationDtl(RmQuotationDtl rmQuotationDtl) {
		this.rmQuotationDtl = rmQuotationDtl;
	}

}