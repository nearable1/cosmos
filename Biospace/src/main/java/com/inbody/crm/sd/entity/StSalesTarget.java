/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 销售目标Entity
 * @author zhangulu
 * @version 2017-10-12
 */
public class StSalesTarget extends DataEntity<StSalesTarget> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String employeeNo;		// 员工号
	private String year;		// 年度
	private String organize;		// 组别
	private String ifAutoApport;		// 是否自动分摊
	private String totalAmount;		// 总金额
	private String firstQuarter;		// 第一季度
	private String secondQuarter;		// 第二季度
	private String thirdQuarter;		// 第三季度
	private String fourthQuarter;		// 第四季度
	
	public StSalesTarget() {
		super();
	}

	public StSalesTarget(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=100, message="员工号长度必须介于 0 和 100 之间")
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	
	@Length(min=1, max=4, message="年度长度必须介于 1 和 4 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=0, max=100, message="组别长度必须介于 0 和 100 之间")
	public String getOrganize() {
		return organize;
	}

	public void setOrganize(String organize) {
		this.organize = organize;
	}
	
	@Length(min=1, max=1, message="是否自动分摊长度必须介于 1 和 1 之间")
	public String getIfAutoApport() {
		return ifAutoApport;
	}

	public void setIfAutoApport(String ifAutoApport) {
		this.ifAutoApport = ifAutoApport;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getFirstQuarter() {
		return firstQuarter;
	}

	public void setFirstQuarter(String firstQuarter) {
		this.firstQuarter = firstQuarter;
	}
	
	public String getSecondQuarter() {
		return secondQuarter;
	}

	public void setSecondQuarter(String secondQuarter) {
		this.secondQuarter = secondQuarter;
	}
	
	public String getThirdQuarter() {
		return thirdQuarter;
	}

	public void setThirdQuarter(String thirdQuarter) {
		this.thirdQuarter = thirdQuarter;
	}
	
	public String getFourthQuarter() {
		return fourthQuarter;
	}

	public void setFourthQuarter(String fourthQuarter) {
		this.fourthQuarter = fourthQuarter;
	}
	
}