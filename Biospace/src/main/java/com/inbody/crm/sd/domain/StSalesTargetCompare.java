/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import java.math.BigDecimal;

import com.inbody.crm.common.persistence.DataEntity;

//import java.util.List;
//
//import com.google.common.collect.Lists;

/**
 * 销售目标与业绩查询结果Entity
 * 
 * @author zhanglulu
 * @version 2017-08-30
 */
public class StSalesTargetCompare extends DataEntity<StSalesTargetCompare> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1497702290794557723L;
	private String employeeName; // 销售员
	private String employeeNo; // 销售员
	private String organizeName; // 组别
	private String organize; // 组别
	private String type; // 1：税前；2：税后

	private BigDecimal preTotalMonthly1; // 1月份总额（税前）
	private BigDecimal preTotalMonthly2; // 2月份总额（税前）
	private BigDecimal preTotalMonthly3; // 3月份总额（税前）
	private BigDecimal preTotalMonthly4; // 4月份总额（税前）
	private BigDecimal preTotalMonthly5; // 5月份总额（税前）
	private BigDecimal preTotalMonthly6; // 6月份总额（税前）
	private BigDecimal preTotalMonthly7; // 7月份总额（税前）
	private BigDecimal preTotalMonthly8; // 8月份总额（税前）
	private BigDecimal preTotalMonthly9; // 9月份总额（税前）
	private BigDecimal preTotalMonthly10; // 10月份总额（税前）
	private BigDecimal preTotalMonthly11; // 11月份总额（税前）
	private BigDecimal preTotalMonthly12; // 12月份总额（税前）
	private BigDecimal preTotalDelivered; // 已发货未开票（税前）
	private BigDecimal preTotalUndelivered; // 未发货未开票（税前）

	private BigDecimal afterTotalMonthly1; // 1月份总额（税后）
	private BigDecimal afterTotalMonthly2; // 2月份总额（税后）
	private BigDecimal afterTotalMonthly3; // 3月份总额（税后）
	private BigDecimal afterTotalMonthly4; // 4月份总额（税后）
	private BigDecimal afterTotalMonthly5; // 5月份总额（税后）
	private BigDecimal afterTotalMonthly6; // 6月份总额（税后）
	private BigDecimal afterTotalMonthly7; // 7月份总额（税后）
	private BigDecimal afterTotalMonthly8; // 8月份总额（税后）
	private BigDecimal afterTotalMonthly9; // 9月份总额（税后）
	private BigDecimal afterTotalMonthly10; // 10月份总额（税后）
	private BigDecimal afterTotalMonthly11; // 11月份总额（税后）
	private BigDecimal afterTotalMonthly12; // 12月份总额（税后）
	private BigDecimal afterTotalDelivered; // 已发货未开票（税后）
	private BigDecimal afterTotalUndelivered; // 未发货未开票（税后）

	private BigDecimal totalAmount; // 合计
	private BigDecimal totalActualFinish; // 实际完成
	private BigDecimal totalTarget; // 目标
	private BigDecimal commission; // 佣金
	private String completionRateActual; // 实际完成率
	private String completionRate; // 完成率

	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getOrganizeName() {
		return organizeName;
	}
	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	public String getOrganize() {
		return organize;
	}
	public void setOrganize(String organize) {
		this.organize = organize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getPreTotalMonthly1() {
		return preTotalMonthly1;
	}
	public void setPreTotalMonthly1(BigDecimal preTotalMonthly1) {
		this.preTotalMonthly1 = preTotalMonthly1;
	}
	public BigDecimal getPreTotalMonthly2() {
		return preTotalMonthly2;
	}
	public void setPreTotalMonthly2(BigDecimal preTotalMonthly2) {
		this.preTotalMonthly2 = preTotalMonthly2;
	}
	public BigDecimal getPreTotalMonthly3() {
		return preTotalMonthly3;
	}
	public void setPreTotalMonthly3(BigDecimal preTotalMonthly3) {
		this.preTotalMonthly3 = preTotalMonthly3;
	}
	public BigDecimal getPreTotalMonthly4() {
		return preTotalMonthly4;
	}
	public void setPreTotalMonthly4(BigDecimal preTotalMonthly4) {
		this.preTotalMonthly4 = preTotalMonthly4;
	}
	public BigDecimal getPreTotalMonthly5() {
		return preTotalMonthly5;
	}
	public void setPreTotalMonthly5(BigDecimal preTotalMonthly5) {
		this.preTotalMonthly5 = preTotalMonthly5;
	}
	public BigDecimal getPreTotalMonthly6() {
		return preTotalMonthly6;
	}
	public void setPreTotalMonthly6(BigDecimal preTotalMonthly6) {
		this.preTotalMonthly6 = preTotalMonthly6;
	}
	public BigDecimal getPreTotalMonthly7() {
		return preTotalMonthly7;
	}
	public void setPreTotalMonthly7(BigDecimal preTotalMonthly7) {
		this.preTotalMonthly7 = preTotalMonthly7;
	}
	public BigDecimal getPreTotalMonthly8() {
		return preTotalMonthly8;
	}
	public void setPreTotalMonthly8(BigDecimal preTotalMonthly8) {
		this.preTotalMonthly8 = preTotalMonthly8;
	}
	public BigDecimal getPreTotalMonthly9() {
		return preTotalMonthly9;
	}
	public void setPreTotalMonthly9(BigDecimal preTotalMonthly9) {
		this.preTotalMonthly9 = preTotalMonthly9;
	}
	public BigDecimal getPreTotalMonthly10() {
		return preTotalMonthly10;
	}
	public void setPreTotalMonthly10(BigDecimal preTotalMonthly10) {
		this.preTotalMonthly10 = preTotalMonthly10;
	}
	public BigDecimal getPreTotalMonthly11() {
		return preTotalMonthly11;
	}
	public void setPreTotalMonthly11(BigDecimal preTotalMonthly11) {
		this.preTotalMonthly11 = preTotalMonthly11;
	}
	public BigDecimal getPreTotalMonthly12() {
		return preTotalMonthly12;
	}
	public void setPreTotalMonthly12(BigDecimal preTotalMonthly12) {
		this.preTotalMonthly12 = preTotalMonthly12;
	}
	public BigDecimal getPreTotalDelivered() {
		return preTotalDelivered;
	}
	public void setPreTotalDelivered(BigDecimal preTotalDelivered) {
		this.preTotalDelivered = preTotalDelivered;
	}
	public BigDecimal getAfterTotalMonthly1() {
		return afterTotalMonthly1;
	}
	public void setAfterTotalMonthly1(BigDecimal afterTotalMonthly1) {
		this.afterTotalMonthly1 = afterTotalMonthly1;
	}
	public BigDecimal getAfterTotalMonthly2() {
		return afterTotalMonthly2;
	}
	public void setAfterTotalMonthly2(BigDecimal afterTotalMonthly2) {
		this.afterTotalMonthly2 = afterTotalMonthly2;
	}
	public BigDecimal getAfterTotalMonthly3() {
		return afterTotalMonthly3;
	}
	public void setAfterTotalMonthly3(BigDecimal afterTotalMonthly3) {
		this.afterTotalMonthly3 = afterTotalMonthly3;
	}
	public BigDecimal getAfterTotalMonthly4() {
		return afterTotalMonthly4;
	}
	public void setAfterTotalMonthly4(BigDecimal afterTotalMonthly4) {
		this.afterTotalMonthly4 = afterTotalMonthly4;
	}
	public BigDecimal getAfterTotalMonthly5() {
		return afterTotalMonthly5;
	}
	public void setAfterTotalMonthly5(BigDecimal afterTotalMonthly5) {
		this.afterTotalMonthly5 = afterTotalMonthly5;
	}
	public BigDecimal getAfterTotalMonthly6() {
		return afterTotalMonthly6;
	}
	public void setAfterTotalMonthly6(BigDecimal afterTotalMonthly6) {
		this.afterTotalMonthly6 = afterTotalMonthly6;
	}
	public BigDecimal getAfterTotalMonthly7() {
		return afterTotalMonthly7;
	}
	public void setAfterTotalMonthly7(BigDecimal afterTotalMonthly7) {
		this.afterTotalMonthly7 = afterTotalMonthly7;
	}
	public BigDecimal getAfterTotalMonthly8() {
		return afterTotalMonthly8;
	}
	public void setAfterTotalMonthly8(BigDecimal afterTotalMonthly8) {
		this.afterTotalMonthly8 = afterTotalMonthly8;
	}
	public BigDecimal getAfterTotalMonthly9() {
		return afterTotalMonthly9;
	}
	public void setAfterTotalMonthly9(BigDecimal afterTotalMonthly9) {
		this.afterTotalMonthly9 = afterTotalMonthly9;
	}
	public BigDecimal getAfterTotalMonthly10() {
		return afterTotalMonthly10;
	}
	public void setAfterTotalMonthly10(BigDecimal afterTotalMonthly10) {
		this.afterTotalMonthly10 = afterTotalMonthly10;
	}
	public BigDecimal getAfterTotalMonthly11() {
		return afterTotalMonthly11;
	}
	public void setAfterTotalMonthly11(BigDecimal afterTotalMonthly11) {
		this.afterTotalMonthly11 = afterTotalMonthly11;
	}
	public BigDecimal getAfterTotalMonthly12() {
		return afterTotalMonthly12;
	}
	public void setAfterTotalMonthly12(BigDecimal afterTotalMonthly12) {
		this.afterTotalMonthly12 = afterTotalMonthly12;
	}
	public BigDecimal getAfterTotalDelivered() {
		return afterTotalDelivered;
	}
	public void setAfterTotalDelivered(BigDecimal afterTotalDelivered) {
		this.afterTotalDelivered = afterTotalDelivered;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getTotalActualFinish() {
		return totalActualFinish;
	}
	public void setTotalActualFinish(BigDecimal totalActualFinish) {
		this.totalActualFinish = totalActualFinish;
	}
	public BigDecimal getTotalTarget() {
		return totalTarget;
	}
	public void setTotalTarget(BigDecimal totalTarget) {
		this.totalTarget = totalTarget;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public String getCompletionRateActual() {
		return completionRateActual;
	}
	public void setCompletionRateActual(String completionRateActual) {
		this.completionRateActual = completionRateActual;
	}
	public String getCompletionRate() {
		return completionRate;
	}
	public void setCompletionRate(String completionRate) {
		this.completionRate = completionRate;
	}
	public BigDecimal getPreTotalUndelivered() {
		return preTotalUndelivered;
	}
	public void setPreTotalUndelivered(BigDecimal preTotalUndelivered) {
		this.preTotalUndelivered = preTotalUndelivered;
	}
	public BigDecimal getAfterTotalUndelivered() {
		return afterTotalUndelivered;
	}
	public void setAfterTotalUndelivered(BigDecimal afterTotalUndelivered) {
		this.afterTotalUndelivered = afterTotalUndelivered;
	}
}