/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

import java.util.List;

import com.google.common.collect.Lists;
import com.inbody.crm.sd.entity.SmStorageInfo;

/**
 * 合同出库查询
 * @author NSSOL
 * @version 2017-08-10
 */
public class SmStorageInfoSearch {
	private String orderNo;		// 合同号 :
	private String deliverId;		// 发货申请ID
	private String customerName;		// 签约方
	private List<SmStorageInfo> smStorageInfoList = Lists.newArrayList();
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getDeliverId() {
		return deliverId;
	}
	public void setDeliverId(String deliverId) {
		this.deliverId = deliverId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<SmStorageInfo> getSmStorageInfoList() {
		return smStorageInfoList;
	}
	public void setSmStorageInfoList(List<SmStorageInfo> smStorageInfoList) {
		this.smStorageInfoList = smStorageInfoList;
	}
}