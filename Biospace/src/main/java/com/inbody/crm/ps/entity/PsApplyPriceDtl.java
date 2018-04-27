/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ps.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;
import com.inbody.crm.common.supcan.annotation.treelist.cols.SupCol;
import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 主子表生成Entity
 * @author Nssol
 * @version 2017-07-20
 */
public class PsApplyPriceDtl extends DataEntity<PsApplyPriceDtl> {
	
	private static final long serialVersionUID = 1L;
	private String appId;		// 申请ID 父类
	private String agreementPatiesId;		// 协议方 (客户代码)
	private String selectAgreementPatiesId;
	private String agreementId;				// 协议方 (协议ID)
	protected String cutomerName;			// 协议方 : 客户名称
	protected String startDays;				// 协议：有效开始日
	protected String endDays;				// 协议：有效截止日
	protected String materialNo;			// 物料号
	protected String materialName;			// 物料名称
	protected String unitPrice;				// 单价
	private String region;					// 地区 : 代码
	protected String regionName;			// 地区
	private String industry;				// 行业 : 代码
	protected String industryName;			// 行业
	private String priceSystem;				// 价格体系 : 代码
	protected String priceSystemName;		// 价格体系 : 代码
	
	public PsApplyPriceDtl() {
		super();
	}

	public PsApplyPriceDtl(String id){
		super(id);
	}

	public PsApplyPriceDtl(PsApplyPrice app){
		this.appId = app.getId();
	}
	
	@Length(min=0, max=64, message="申请ID长度必须介于 0 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(PsApplyPrice app) {
		this.appId = app.getId();
	}
	
	@ExcelField(title="最终客户Code|经销商Code|代理商Code", type=0, align=2, sort=2)
	@Length(min=1, max=50, message="协议方")
	public String getAgreementPatiesId() {
		return agreementPatiesId;
	}

	public void setAgreementPatiesId(String agreementPatiesId) {
		this.agreementPatiesId = agreementPatiesId;
	}
	
	@Length(min=1, max=50, message="物料号长度必须介于 1 和 50 之间")
	@SupCol(text="物料Code", sort = 30, minWidth="125px")
	@ExcelField(title="物料Code", type=0, align=2, sort=2)
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	
	@SupCol(text="单价", sort = 30, minWidth="125px")
	@ExcelField(title="单价", type=0, align=2, sort=2)
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Length(min=0, max=2, message="地区 : 代码长度必须介于 0 和 2 之间")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	@Length(min=0, max=2, message="行业 : 代码长度必须介于 0 和 2 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCutomerName() {
		return cutomerName;
	}

	public void setCutomerName(String cutomerName) {
		this.cutomerName = cutomerName;
	}

	public String getStartDays() {
		return startDays;
	}

	public void setStartDays(String startDays) {
		this.startDays = startDays;
	}

	public String getEndDays() {
		return endDays;
	}

	public void setEndDays(String endDays) {
		this.endDays = endDays;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	@SupCol(text="协议编号", sort = 30, minWidth="125px")
	@ExcelField(title="协议编号", type=0, align=2, sort=1)
	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	
	@SupCol(text="地区", sort = 30, minWidth="125px")
	@ExcelField(title="地区", type=0, align=2, sort=2)
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@SupCol(text="行业", sort = 30, minWidth="125px")
	@ExcelField(title="行业", type=0, align=2, sort=2)
	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getPriceSystem() {
		return priceSystem;
	}

	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}

	public String getPriceSystemName() {
		return priceSystemName;
	}

	public void setPriceSystemName(String priceSystemName) {
		this.priceSystemName = priceSystemName;
	}

	public String getSelectAgreementPatiesId() {
		return selectAgreementPatiesId;
	}

	public void setSelectAgreementPatiesId(String selectAgreementPatiesId) {
		this.selectAgreementPatiesId = selectAgreementPatiesId;
	}
	
	public String getApplyUserName() {
		if(createBy != null)
			return createBy.getName();
		else
			return null;
	}
}