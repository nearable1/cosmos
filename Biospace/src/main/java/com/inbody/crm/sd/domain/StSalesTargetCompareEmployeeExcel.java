/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

//import java.util.List;
//
//import com.google.common.collect.Lists;

/**
 * 销售目标与业绩查询结果Entity
 * 
 * @author zhanglulu
 * @version 2017-08-30
 */
public class StSalesTargetCompareEmployeeExcel {

	private String employeeName; // 销售员
	private String organizeName; // 组别
	private String type; // 1：税前；2：税后
	private String totalMonthly1; // 1月份总额
	private String totalMonthly2; // 2月份总额
	private String totalMonthly3; // 3月份总额
	private String totalMonthly4; // 4月份总额
	private String totalMonthly5; // 5月份总额
	private String totalMonthly6; // 6月份总额
	private String totalMonthly7; // 7月份总额
	private String totalMonthly8; // 8月份总额
	private String totalMonthly9; // 9月份总额
	private String totalMonthly10; // 10月份总额
	private String totalMonthly11; // 11月份总额
	private String totalMonthly12; // 12月份总额
	private String totalAmount; // 合计
	private String totalDelivered; // 已发货未开票
	private String totalUnDeliver; // 未发货未开票
	private String totalActualFinish; // 实际完成
	private String totalTarget; // 目标
	private String commission; // 佣金
	private String completionRateActual; // 实际完成率
	private String completionRate; // 完成率

	private String totalTitle; // 总合计

	@ExcelField(title="销售员", type=1, align=2, sort=1)
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@ExcelField(title="组别", type=1, align=2, sort=2)
	public String getOrganizeName() {
		return organizeName;
	}
	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	@ExcelField(title="税前/后", type=1, align=2, sort=3)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ExcelField(title="1月", type=1, align=2, sort=4)
	public String getTotalMonthly1() {
		return totalMonthly1;
	}
	public void setTotalMonthly1(String totalMonthly1) {
		this.totalMonthly1 = totalMonthly1;
	}
	@ExcelField(title="2月", type=1, align=2, sort=5)
	public String getTotalMonthly2() {
		return totalMonthly2;
	}
	public void setTotalMonthly2(String totalMonthly2) {
		this.totalMonthly2 = totalMonthly2;
	}
	@ExcelField(title="3月", type=1, align=2, sort=6)
	public String getTotalMonthly3() {
		return totalMonthly3;
	}
	public void setTotalMonthly3(String totalMonthly3) {
		this.totalMonthly3 = totalMonthly3;
	}
	@ExcelField(title="4月", type=1, align=2, sort=7)
	public String getTotalMonthly4() {
		return totalMonthly4;
	}
	public void setTotalMonthly4(String totalMonthly4) {
		this.totalMonthly4 = totalMonthly4;
	}
	@ExcelField(title="5月", type=1, align=2, sort=8)
	public String getTotalMonthly5() {
		return totalMonthly5;
	}
	public void setTotalMonthly5(String totalMonthly5) {
		this.totalMonthly5 = totalMonthly5;
	}
	@ExcelField(title="6月", type=1, align=2, sort=9)
	public String getTotalMonthly6() {
		return totalMonthly6;
	}
	public void setTotalMonthly6(String totalMonthly6) {
		this.totalMonthly6 = totalMonthly6;
	}
	@ExcelField(title="7月", type=1, align=2, sort=10)
	public String getTotalMonthly7() {
		return totalMonthly7;
	}
	public void setTotalMonthly7(String totalMonthly7) {
		this.totalMonthly7 = totalMonthly7;
	}
	@ExcelField(title="8月", type=1, align=2, sort=11)
	public String getTotalMonthly8() {
		return totalMonthly8;
	}
	public void setTotalMonthly8(String totalMonthly8) {
		this.totalMonthly8 = totalMonthly8;
	}
	@ExcelField(title="9月", type=1, align=2, sort=12)
	public String getTotalMonthly9() {
		return totalMonthly9;
	}
	public void setTotalMonthly9(String totalMonthly9) {
		this.totalMonthly9 = totalMonthly9;
	}
	@ExcelField(title="10月", type=1, align=2, sort=13)
	public String getTotalMonthly10() {
		return totalMonthly10;
	}
	public void setTotalMonthly10(String totalMonthly10) {
		this.totalMonthly10 = totalMonthly10;
	}
	@ExcelField(title="11月", type=1, align=2, sort=14)
	public String getTotalMonthly11() {
		return totalMonthly11;
	}
	public void setTotalMonthly11(String totalMonthly11) {
		this.totalMonthly11 = totalMonthly11;
	}
	@ExcelField(title="12月", type=1, align=2, sort=15)
	public String getTotalMonthly12() {
		return totalMonthly12;
	}
	public void setTotalMonthly12(String totalMonthly12) {
		this.totalMonthly12 = totalMonthly12;
	}
	@ExcelField(title="合计", type=1, align=2, sort=16)
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	@ExcelField(title="已发货未开票", type=1, align=2, sort=17)
	public String getTotalDelivered() {
		return totalDelivered;
	}
	public void setTotalDelivered(String totalDelivered) {
		this.totalDelivered = totalDelivered;
	}
	@ExcelField(title="未发货未开票", type=1, align=2, sort=18)
	public String getTotalUnDeliver() {
		return totalUnDeliver;
	}
	public void setTotalUnDeliver(String totalUnDeliver) {
		this.totalUnDeliver = totalUnDeliver;
	}
	@ExcelField(title="实际完成", type=1, align=2, sort=19)
	public String getTotalActualFinish() {
		return totalActualFinish;
	}
	public void setTotalActualFinish(String totalActualFinish) {
		this.totalActualFinish = totalActualFinish;
	}
	@ExcelField(title="目标", type=1, align=2, sort=20)
	public String getTotalTarget() {
		return totalTarget;
	}
	public void setTotalTarget(String totalTarget) {
		this.totalTarget = totalTarget;
	}
	@ExcelField(title="佣金", type=1, align=2, sort=21)
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	@ExcelField(title="实际完成率", type=1, align=2, sort=22)
	public String getCompletionRateActual() {
		return completionRateActual;
	}
	public void setCompletionRateActual(String completionRateActual) {
		this.completionRateActual = completionRateActual;
	}
	@ExcelField(title="完成率", type=1, align=2, sort=23)
	public String getCompletionRate() {
		return completionRate;
	}
	public void setCompletionRate(String completionRate) {
		this.completionRate = completionRate;
	}
	@ExcelField(title="", mergedRegion=true, rows=2, firstCol=0, lastCol=1, type=1, align=2, sort=24)
	public String getTotalTitle() {
		return totalTitle;
	}
	public void setTotalTitle(String totalTitle) {
		this.totalTitle = totalTitle;
	}
}