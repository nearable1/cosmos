/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;



/**
 * 销售目标导出Entity
 * 
 * @author zhanglulu
 * @version 2017-08-24
 */
public class StSalesTargetExcel {
	private String employeeNo;		// 员工号
	private String year;		// 年度
	private String organize;		// 组别
	private String ifAutoApport;		// 是否自动分摊
	private String totalAmount;		// 总金额
	private String firstQuarter;		// 第一季度
	private String secondQuarter;		// 第二季度
	private String thirdQuarter;		// 第三季度
	private String fourthQuarter;		// 第四季度
	
	@ExcelField(title="员工号", type=1, align=2, sort=2)
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	@ExcelField(title="年度", type=1, align=2, sort=1)
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@ExcelField(title="组别", type=1, align=2, sort=3)
	public String getOrganize() {
		return organize;
	}
	public void setOrganize(String organize) {
		this.organize = organize;
	}
	
	@ExcelField(title="是否自动分摊", type=1, align=2, dictType="yes_no", sort=4)
	public String getIfAutoApport() {
		return ifAutoApport;
	}
	public void setIfAutoApport(String ifAutoApport) {
		this.ifAutoApport = ifAutoApport;
	}

	@ExcelField(title="总金额", type=1, align=2, sort=5)
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@ExcelField(title="第一季度", type=1, align=2, sort=6)
	public String getFirstQuarter() {
		return firstQuarter;
	}
	public void setFirstQuarter(String firstQuarter) {
		this.firstQuarter = firstQuarter;
	}

	@ExcelField(title="第二季度", type=1, align=2, sort=7)
	public String getSecondQuarter() {
		return secondQuarter;
	}
	public void setSecondQuarter(String secondQuarter) {
		this.secondQuarter = secondQuarter;
	}

	@ExcelField(title="第三季度", type=1, align=2, sort=8)
	public String getThirdQuarter() {
		return thirdQuarter;
	}
	public void setThirdQuarter(String thirdQuarter) {
		this.thirdQuarter = thirdQuarter;
	}

	@ExcelField(title="第四季度", type=1, align=2, sort=9)
	public String getFourthQuarter() {
		return fourthQuarter;
	}
	public void setFourthQuarter(String fourthQuarter) {
		this.fourthQuarter = fourthQuarter;
	}
}