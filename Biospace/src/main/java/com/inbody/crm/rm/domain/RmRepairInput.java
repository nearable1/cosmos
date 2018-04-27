/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import java.util.List;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 维修查询维修信息
 * 
 * @author yangj
 * @version 2017-08-16
 */
public class RmRepairInput extends DataEntity<RmRepairInput> {

	private static final long serialVersionUID = -1614795382744562112L;
	
	private String snNo;

	/** sn情报 */
	private RmRepairSnInfo snInfo;

	/** 维修情报 */
	private RmRepairInfoEx repairInfo;

	/** 维修历史记录 */
	private List<RmRepairInfoEx> repairHisList;

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public RmRepairSnInfo getSnInfo() {
		return snInfo;
	}

	public void setSnInfo(RmRepairSnInfo snInfo) {
		this.snInfo = snInfo;
	}

	public RmRepairInfoEx getRepairInfo() {
		return repairInfo;
	}

	public void setRepairInfo(RmRepairInfoEx repairInfo) {
		this.repairInfo = repairInfo;
	}

	public List<RmRepairInfoEx> getRepairHisList() {
		return repairHisList;
	}

	public void setRepairHisList(List<RmRepairInfoEx> repairHisList) {
		this.repairHisList = repairHisList;
	}

}