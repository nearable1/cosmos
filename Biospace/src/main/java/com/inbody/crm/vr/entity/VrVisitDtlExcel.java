/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.entity;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 主子表生成Entity
 * @author Nssol
 * @version 2017-07-20
 */
public class VrVisitDtlExcel extends VrVisitDtl {
	private static final long serialVersionUID = 1L;
	@ExcelField(title = "NO", type = 1, align = 1, sort = 1)
	public String getVisitNo() {
		return visitNo;
	}

	@ExcelField(title = "地点区分", type = 1, align = 1, sort = 2, dictType="DM0041")
	public String getIfLocal() {
		return ifLocal;
	}

	@ExcelField(title = "计划拜访对象", type = 1, align = 1, sort = 3)
	public String getCustomerChName() {
		return customerChName;
	}
	
	@ExcelField(title = "计划拜访时间", type = 1, align = 1, sort = 4)
	public String getExpDateFrom() {
		return expDateFrom;
	}
	
	@ExcelField(title = "实际拜访时间", type = 1, align = 1, sort = 5)
	public String getActDateFrom() {
		return actDateFrom;
	}
	
	@ExcelField(title = "已拜访", type = 1, align = 1, sort = 6)
	public String getIfVisitName() {
		return ifVisitName;
	}
	
	@ExcelField(title = "申请人", type = 1, align = 1, sort = 7)
	public String getCreateName() {
		return createName;
	}

	@ExcelField(title = "申请状态", type = 1, align = 1, sort = 8)
	public String getWorkflowStatusName() {
		return workflowStatusName;
	}
	
	@ExcelField(title = "报告状态", type = 1, align = 1, sort = 9)
	public String getWorkflowStatus2Name() {
		return workflowStatus2Name;
	}

	
}