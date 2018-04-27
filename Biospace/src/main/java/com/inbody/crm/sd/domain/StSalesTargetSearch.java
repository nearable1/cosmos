/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.domain;

//import java.util.List;
//
//import com.google.common.collect.Lists;
import java.util.Calendar;
import java.util.List;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.sd.entity.StSalesTarget;

/**
 * 销售标查询结果Entity
 * 
 * @author zhanglulu
 * @version 2017-08-30
 */
public class StSalesTargetSearch {

	private String year; // 目标年度
	private String yearSearch; // 目标年度(检索用)
	private String organize; // 组别(检索用)

	private List<StSalesTarget> stSalesTargetList = Lists.newArrayList();		// 目标明细

	private List<StSalesTargetCompare> stSalesTargetCompareList = Lists.newArrayList();		// 对比明细
//	private List<StSalesTargetCompare> stSalesTargetCompareTotalList = Lists.newArrayList();		// 对比合计明细

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYearSearch() {
		return yearSearch;
	}

	public void setYearSearch(String yearSearch) {
		this.yearSearch = yearSearch;
	}

	public String getOrganize() {
		return organize;
	}

	public void setOrganize(String organize) {
		this.organize = organize;
	}

	public List<StSalesTarget> getStSalesTargetList() {
		return stSalesTargetList;
	}

	public void setStSalesTargetList(List<StSalesTarget> stSalesTargetList) {
		this.stSalesTargetList = stSalesTargetList;
	}

	public List<StSalesTargetCompare> getStSalesTargetCompareList() {
		return stSalesTargetCompareList;
	}

	public void setStSalesTargetCompareList(
			List<StSalesTargetCompare> stSalesTargetCompareList) {
		this.stSalesTargetCompareList = stSalesTargetCompareList;
	}

//	public List<StSalesTargetCompare> getStSalesTargetCompareTotalList() {
//		return stSalesTargetCompareTotalList;
//	}

//	public void setStSalesTargetCompareTotalList(
//			List<StSalesTargetCompare> stSalesTargetCompareTotalList) {
//		this.stSalesTargetCompareTotalList = stSalesTargetCompareTotalList;
//	}

	public String getIfPassYear() {
		
		if (!StringUtils.isEmptyNull(year)) {
			
			int intYear = Integer.parseInt(year);
			Calendar c = Calendar.getInstance();
			int currYear = c.get(Calendar.YEAR);
			
			if (intYear < currYear) {
				return CommonConstants.YES;
			} else {
				return CommonConstants.NO;
			}
		}
		return CommonConstants.YES;
	}
}