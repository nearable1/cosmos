package com.inbody.crm.ps.entity;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

public class PsApplyPriceDtlExport extends PsApplyPriceDtl {

	private static final long serialVersionUID = 1L;
	
	@ExcelField(title="物料号", type=1, align=2, sort=1)
	public String getMaterialNo() {
		return materialNo;
	}
	
	@ExcelField(title="物料名称", type=1, align=2, sort=2)
	public String getMaterialName() {
		return materialName;
	}
	
	@ExcelField(title="销售方式", type=1, align=2, sort=3)
	public String getPriceSystemName() {
		return priceSystemName;
	}
	
	@ExcelField(title="协议方 ", type=1, align=2, sort=4)
	public String getCutomerName() {
		return cutomerName;
	}
	
	@ExcelField(title="地区", type=1, align=2, sort=5)
	public String getRegionName() {
		return regionName;
	}
	
	@ExcelField(title="行业", type=1, align=2, sort=6)
	public String getIndustryName() {
		return industryName;
	}
	
	@ExcelField(title="单价", type=1, align=2, sort=7)
	public String getUnitPrice() {
		return unitPrice;
	}
	
	@ExcelField(title="申请人", type=1, align=2, sort=8)
	public String getApplyUserName() {
		return createBy.getName();
	}
	
	@ExcelField(title="有效开始日", type=1, align=2, sort=9)
	public String getStartDays() {
		return startDays;
	}
	
	@ExcelField(title="有效终止日", type=1, align=2, sort=10)
	public String getEndDays() {
		return endDays;
	}
}
