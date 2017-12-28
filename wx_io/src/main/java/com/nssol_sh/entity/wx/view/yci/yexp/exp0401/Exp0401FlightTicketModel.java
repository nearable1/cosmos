package com.nssol_sh.entity.wx.view.yci.yexp.exp0401;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 出差报销-交通（航空）
 * 
 * @author S1mple
 *
 */
public class Exp0401FlightTicketModel {

	/**
	 * 序号.
	 */
	public BigDecimal sortKey;

	/**
	 * 日期.
	 */
	public String departDate;

	/**
	 * 交通工具代码.
	 */
	public String transportType;

	/**
	 * 交通工具名称.
	 */
	public String transportTypeName;

	/**
	 * 交通工具一览.
	 */
	public List<Map<String, Object>> transportTypeList;

	/**
	 * 班次.
	 */
	public String ticketNumber;

	/**
	 * 出发时间（时）.
	 */
	public String trafficStartHour;

	/**
	 * 出发时间（分）.
	 */
	public String trafficStartMinute;

	/**
	 * 到达时间（时）.
	 */
	public String trafficEndHour;

	/**
	 * 到达时间（分）.
	 */
	public String trafficEndMinute;

	/**
	 * 出发地.
	 */
	public String transportFrom;

	/**
	 * 到达地.
	 */
	public String transportTo;

	/**
	 * 数量.
	 */
	public BigDecimal ticketQuantity;

	/**
	 * 单价.
	 */
	public BigDecimal ticketPrice;

	/**
	 * 预算金额.
	 */
	public BigDecimal ticketAmount;

	/**
	 * 预算金额（人民币）.
	 */
	public String ticketAmountRMB;

	/**
	 * 币种代码.
	 */
	public String ticketCurrencyCd;

	/**
	 * 币种名称.
	 */
	public String ticketCurrencyName;

	/**
	 * 币种一览.
	 */
	public List<Map<String, Object>> currencyList;

	/**
	 * 汇率.
	 */
	public BigDecimal ticketExchangeRate;

	/**
	 * @return the sortKey
	 */
	public BigDecimal getSortKey() {
		return sortKey;
	}

	/**
	 * @return the departDate
	 */
	public String getDepartDate() {
		return departDate;
	}

	/**
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * @return the transportTypeName
	 */
	public String getTransportTypeName() {
		return transportTypeName;
	}

	/**
	 * @return the transportTypeList
	 */
	public List<Map<String, Object>> getTransportTypeList() {
		return transportTypeList;
	}

	/**
	 * @return the ticketNumber
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @return the trafficStartHour
	 */
	public String getTrafficStartHour() {
		return trafficStartHour;
	}

	/**
	 * @return the trafficStartMinute
	 */
	public String getTrafficStartMinute() {
		return trafficStartMinute;
	}

	/**
	 * @return the trafficEndHour
	 */
	public String getTrafficEndHour() {
		return trafficEndHour;
	}

	/**
	 * @return the trafficEndMinute
	 */
	public String getTrafficEndMinute() {
		return trafficEndMinute;
	}

	/**
	 * @return the transportFrom
	 */
	public String getTransportFrom() {
		return transportFrom;
	}

	/**
	 * @return the transportTo
	 */
	public String getTransportTo() {
		return transportTo;
	}

	/**
	 * @return the ticketQuantity
	 */
	public BigDecimal getTicketQuantity() {
		return ticketQuantity;
	}

	/**
	 * @return the ticketPrice
	 */
	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}

	/**
	 * @return the ticketAmount
	 */
	public BigDecimal getTicketAmount() {
		return ticketAmount;
	}

	/**
	 * @return the ticketAmountRMB
	 */
	public String getTicketAmountRMB() {
		return ticketAmountRMB;
	}

	/**
	 * @return the ticketCurrencyCd
	 */
	public String getTicketCurrencyCd() {
		return ticketCurrencyCd;
	}

	/**
	 * @return the ticketCurrencyName
	 */
	public String getTicketCurrencyName() {
		return ticketCurrencyName;
	}

	/**
	 * @return the currencyList
	 */
	public List<Map<String, Object>> getCurrencyList() {
		return currencyList;
	}

	/**
	 * @return the ticketExchangeRate
	 */
	public BigDecimal getTicketExchangeRate() {
		return ticketExchangeRate;
	}

}
