package com.nssol_sh.entity.wx.view.ysw.yexp.exp0601;

import java.math.BigDecimal;

/**
 * 交通一览
 * 
 * @author long.liming
 *
 */
public class Exp0601TrafficMoneyModel {
	/**
	 * 序号.
	 */
	public BigDecimal sortKey;

	/**
	 * 日期.
	 */
	public String occurredDate;
	/**
	 * 上車时间（时）.
	 */
	public String getOnfTimeStartHour;

	/**
	 * 上車时间（分）.
	 */
	public String getOnfTimeStartMinute;

	/**
	 * 下車时间（时）.
	 */
	public String getOffTimeEndHour;

	/**
	 * 下車时间（分）.
	 */
	public String getOffTimeEndMinute;

	/**
	 * 起始地.
	 */
	public String startPlace;

	/**
	 * 目的地.
	 */
	public String endPlace;

	/**
	 * 事由.
	 */
	public String reason;

	/**
	 * 金額.
	 */
	public BigDecimal transAmount;

	/**
	 * 備注（發票號碼）.
	 */
	public String transRemarks;

	/**
	 * @return the sortKey
	 */
	public BigDecimal getSortKey() {
		return sortKey;
	}

	/**
	 * @return the occurredDate
	 */
	public String getOccurredDate() {
		return occurredDate;
	}

	/**
	 * @return the getOnfTimeStartHour
	 */
	public String getGetOnfTimeStartHour() {
		return getOnfTimeStartHour;
	}

	/**
	 * @return the getOnfTimeStartMinute
	 */
	public String getGetOnfTimeStartMinute() {
		return getOnfTimeStartMinute;
	}

	/**
	 * @return the getOffTimeEndHour
	 */
	public String getGetOffTimeEndHour() {
		return getOffTimeEndHour;
	}

	/**
	 * @return the getOffTimeEndMinute
	 */
	public String getGetOffTimeEndMinute() {
		return getOffTimeEndMinute;
	}

	/**
	 * @return the startPlace
	 */
	public String getStartPlace() {
		return startPlace;
	}

	/**
	 * @return the endPlace
	 */
	public String getEndPlace() {
		return endPlace;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @return the transAmount
	 */
	public BigDecimal getTransAmount() {
		return transAmount;
	}

	/**
	 * @return the transRemarks
	 */
	public String getTransRemarks() {
		return transRemarks;
	}

}
