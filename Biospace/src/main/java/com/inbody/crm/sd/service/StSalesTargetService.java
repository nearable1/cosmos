/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.dao.OfficeDao;
import com.inbody.crm.modules.sys.dao.UserDao;
import com.inbody.crm.modules.sys.entity.Office;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.sd.dao.StSalesTargetDao;
import com.inbody.crm.sd.domain.StSalesTargetCompare;
import com.inbody.crm.sd.domain.StSalesTargetCompareEmployeeExcel;
import com.inbody.crm.sd.domain.StSalesTargetCompareOrganizeExcel;
import com.inbody.crm.sd.domain.StSalesTargetExcel;
import com.inbody.crm.sd.domain.StSalesTargetSearch;
import com.inbody.crm.sd.entity.StSalesTarget;

/**
 * 销售目标Service
 * @author zhanglulu
 * @version 2017-08-22
 */
@Service
@Transactional(readOnly = true)
public class StSalesTargetService {

	@Autowired
	private StSalesTargetDao stSalesTargetDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private OfficeDao officeDao;

	/**
	 * 查询结果取得
	 * 
	 * @param stSalesTargetSearch
	 * @return
	 */
	public StSalesTargetSearch getStSalesTargetList(StSalesTargetSearch stSalesTargetSearch) {
		
		if (StringUtils.isEmptyNull(stSalesTargetSearch.getYear())) {

			stSalesTargetSearch = new StSalesTargetSearch();
		} else {

			StSalesTarget stSalesTarget = new StSalesTarget();
			stSalesTarget.setYear(stSalesTargetSearch.getYear());
			
			List<StSalesTarget> stSalesTargetList = stSalesTargetDao.getStSalesTargetList(stSalesTarget);
			
			if (stSalesTargetList != null && stSalesTargetList.size() > 0) {
				stSalesTargetSearch.setStSalesTargetList(stSalesTargetList);
			} else {
				stSalesTargetSearch.setStSalesTargetList(null);
			}
		}

		return stSalesTargetSearch;
	}

	/**
	 * 整合性验证
	 */
	public String validator(StSalesTargetSearch stSalesTargetSearch) {
		
		List<String> message = new ArrayList<String>();
		
		if (stSalesTargetSearch.getStSalesTargetList() == null || stSalesTargetSearch.getStSalesTargetList().size() == 0) {
			message.add("至少添加一条记录。");
		} else {
			
			if (!StringUtils.equals(stSalesTargetSearch.getYear(), stSalesTargetSearch.getYearSearch())) {
				message.add("当前显示年度与检索年度不符。");
			} else {

				Map<String, Map<String, String>> employeeMap = new LinkedHashMap<String, Map<String, String>>();
				Map<String, String> organizeMap = new LinkedHashMap<String, String>();
				
				for (StSalesTarget stSalesTarget : stSalesTargetSearch.getStSalesTargetList()) {

					if (!employeeMap.containsKey(stSalesTarget.getEmployeeNo())) {
						organizeMap = new LinkedHashMap<String, String>();
						organizeMap.put(stSalesTarget.getOrganize(), stSalesTarget.getOrganize());
						employeeMap.put(stSalesTarget.getEmployeeNo(), organizeMap);
					} else {
						organizeMap = employeeMap.get(stSalesTarget.getEmployeeNo());
						
						if (!organizeMap.containsKey(stSalesTarget.getOrganize())) {
							organizeMap.put(stSalesTarget.getOrganize(), stSalesTarget.getOrganize());
						} else {
							
							User user = userDao.getDefault(stSalesTarget.getEmployeeNo());
							Office office = officeDao.getDefault(stSalesTarget.getOrganize());
							String organizeName = office.getName();
							String employeeName = user.getName();
							message.add("组别：" + organizeName + "的销售员：" + employeeName + "的目标制定重复。");
						}
					}
				}
			}
		}
		
		StringBuilder sbMessage = new StringBuilder();
		if (message.size() > 0) {
			for (String str : message) {
				int index = 1;
				
				if (index == message.size()) {
					sbMessage.append(str);
				} else {
					sbMessage.append(str).append("<br/>");
				}
				
				index++;
			}
		} else {
			return null;
		}
		
		return sbMessage.toString();
	}

	/**
	 * 销售目标制定
	 */
	@Transactional(readOnly = false)
	public void save(StSalesTargetSearch stSalesTargetSearch) {
		
		String year = stSalesTargetSearch.getYear();
		
		StSalesTarget delStSalesTarget = new StSalesTarget();
		
		delStSalesTarget.setYear(year);
//		delStSalesTarget.setOrganize(stSalesTargetSearch.getOrganize());
		
		stSalesTargetDao.deleteStSalesTargetInfo(delStSalesTarget);

		for (StSalesTarget stSalesTarget : stSalesTargetSearch.getStSalesTargetList()) {
			
			stSalesTarget.setYear(year);
			
			stSalesTarget.preInsert();
			stSalesTargetDao.insert(stSalesTarget);
			
		}
	}
	public List<StSalesTargetExcel> export(StSalesTargetSearch stSalesTargetSearch) {
		
		List<StSalesTargetExcel> stSalesTargetExcellList = Lists.newArrayList();
		
		if (!StringUtils.isEmptyNull(stSalesTargetSearch.getYearSearch())) {

			StSalesTarget stSalesTargetSe = new StSalesTarget();
			stSalesTargetSe.setYear(stSalesTargetSearch.getYearSearch());
			
			List<StSalesTarget> stSalesTargetList = stSalesTargetDao.getStSalesTargetList(stSalesTargetSe);
			
			StSalesTargetExcel stSalesTargetExcel = null;
			NumberFormat nf = new DecimalFormat("#,##0.00");

			for (StSalesTarget stSalesTarget : stSalesTargetList) {
				stSalesTargetExcel = new StSalesTargetExcel();
				
				User user = userDao.getDefault(stSalesTarget.getEmployeeNo());
				Office office = officeDao.getDefault(stSalesTarget.getOrganize());
				String organizeName = office.getName();
				String employeeName = user.getName();
				
				stSalesTargetExcel.setYear(stSalesTarget.getYear());
				stSalesTargetExcel.setEmployeeNo(employeeName);
				stSalesTargetExcel.setOrganize(organizeName);
				stSalesTargetExcel.setIfAutoApport(stSalesTarget.getIfAutoApport());
				stSalesTargetExcel.setTotalAmount(nf.format(new BigDecimal(stSalesTarget.getTotalAmount())));
				stSalesTargetExcel.setFirstQuarter(nf.format(new BigDecimal(stSalesTarget.getFirstQuarter())));
				stSalesTargetExcel.setSecondQuarter(nf.format(new BigDecimal(stSalesTarget.getSecondQuarter())));
				stSalesTargetExcel.setThirdQuarter(nf.format(new BigDecimal(stSalesTarget.getThirdQuarter())));
				stSalesTargetExcel.setFourthQuarter(nf.format(new BigDecimal(stSalesTarget.getFourthQuarter())));
				
				stSalesTargetExcellList.add(stSalesTargetExcel);
			}
		}
		
		return stSalesTargetExcellList;
	}

	/**
	 * 销售业绩与目标查询-销售员一览取得
	 * 
	 * @param stSalesTargetSearch
	 * @return
	 */
	public StSalesTargetSearch getStSalesTargetCompareEmployeeList(StSalesTargetSearch stSalesTargetSearch) {
		
		if (StringUtils.isEmptyNull(stSalesTargetSearch.getYear())) {

			stSalesTargetSearch = new StSalesTargetSearch();
		} else {
			List<StSalesTargetCompare> stSalesTargetCompareList = getStSalesTargetCompareList(stSalesTargetSearch, "employee");
			
			if (stSalesTargetCompareList != null && stSalesTargetCompareList.size() > 0) {

				List<StSalesTargetCompare> stSalesTargetCompareTotalList = getStSalesTargetCompareTotalList(stSalesTargetCompareList);
				stSalesTargetCompareList.addAll(stSalesTargetCompareTotalList);
				
				stSalesTargetSearch.setStSalesTargetCompareList(stSalesTargetCompareList);
//				stSalesTargetSearch.setStSalesTargetCompareTotalList(stSalesTargetCompareTotalList);
			} else {

				stSalesTargetSearch.setStSalesTargetCompareList(null);
//				stSalesTargetSearch.setStSalesTargetCompareTotalList(null);
			}
		}

		return stSalesTargetSearch;
	}
	public List<StSalesTargetCompareEmployeeExcel> exportEmployeeList(StSalesTargetSearch stSalesTargetSearch) {
		
		List<StSalesTargetCompareEmployeeExcel> stSalesTargetCompareEmployeeExcelList = Lists.newArrayList();
		
		if (!StringUtils.isEmptyNull(stSalesTargetSearch.getYear())) {

			List<StSalesTargetCompare> stSalesTargetCompareList = getStSalesTargetCompareList(stSalesTargetSearch, "employee");
			
			if (stSalesTargetCompareList != null && stSalesTargetCompareList.size() > 0) {
				List<StSalesTargetCompare> stSalesTargetCompareTotalList = getStSalesTargetCompareTotalList(stSalesTargetCompareList);
				stSalesTargetCompareList.addAll(stSalesTargetCompareTotalList);

				StSalesTargetCompareEmployeeExcel stSalesTargetCompareEmployeeExcel = null;

				for (StSalesTargetCompare stSalesTargetCompare : stSalesTargetCompareList) {
					stSalesTargetCompareEmployeeExcel = new StSalesTargetCompareEmployeeExcel();

					stSalesTargetCompareEmployeeExcel.setEmployeeName(stSalesTargetCompare.getEmployeeName());
					stSalesTargetCompareEmployeeExcel.setOrganizeName(stSalesTargetCompare.getOrganizeName());
					
					if (StringUtils.isEmptyNull(stSalesTargetCompare.getEmployeeNo())) {
						stSalesTargetCompareEmployeeExcel.setTotalTitle("合计");
					}
					stSalesTargetCompareEmployeeExcel.setTotalActualFinish(StringUtils.numberFormat(stSalesTargetCompare.getTotalActualFinish()));
					stSalesTargetCompareEmployeeExcel.setTotalAmount(StringUtils.numberFormat(stSalesTargetCompare.getTotalAmount()));

					if (StringUtils.equals(stSalesTargetCompare.getType(), "1")) {

						stSalesTargetCompareEmployeeExcel.setType("税前");

						stSalesTargetCompareEmployeeExcel.setTotalMonthly1(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly1()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly2(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly2()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly3(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly3()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly4(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly4()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly5(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly5()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly6(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly6()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly7(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly7()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly8(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly8()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly9(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly9()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly10(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly10()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly11(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly11()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly12(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly12()));
						stSalesTargetCompareEmployeeExcel.setTotalUnDeliver(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalUndelivered()));
						stSalesTargetCompareEmployeeExcel.setTotalDelivered(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalDelivered()));

						stSalesTargetCompareEmployeeExcel.setTotalTarget(StringUtils.numberFormat(stSalesTargetCompare.getTotalTarget()));
						stSalesTargetCompareEmployeeExcel.setCommission(StringUtils.numberFormat(stSalesTargetCompare.getCommission()));
						stSalesTargetCompareEmployeeExcel.setCompletionRate(stSalesTargetCompare.getCompletionRate());
						stSalesTargetCompareEmployeeExcel.setCompletionRateActual(stSalesTargetCompare.getCompletionRateActual());
					} else if (StringUtils.equals(stSalesTargetCompare.getType(), "2")) {

						stSalesTargetCompareEmployeeExcel.setType("税后");

						stSalesTargetCompareEmployeeExcel.setTotalMonthly1(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly1()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly2(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly2()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly3(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly3()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly4(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly4()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly5(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly5()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly6(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly6()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly7(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly7()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly8(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly8()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly9(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly9()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly10(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly10()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly11(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly11()));
						stSalesTargetCompareEmployeeExcel.setTotalMonthly12(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly12()));
						stSalesTargetCompareEmployeeExcel.setTotalUnDeliver(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalUndelivered()));
						stSalesTargetCompareEmployeeExcel.setTotalDelivered(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalDelivered()));
					}

					stSalesTargetCompareEmployeeExcelList.add(stSalesTargetCompareEmployeeExcel);
				}
			}
		}
		
		return stSalesTargetCompareEmployeeExcelList;
	}

	/**
	 * 销售业绩与目标查询-组一览取得
	 * 
	 * @param stSalesTargetSearch
	 * @return
	 */
	public StSalesTargetSearch getStSalesTargetCompareOrganizeList(StSalesTargetSearch stSalesTargetSearch) {
		
		if (StringUtils.isEmptyNull(stSalesTargetSearch.getYear())) {

			stSalesTargetSearch = new StSalesTargetSearch();
		} else {
			List<StSalesTargetCompare> stSalesTargetCompareList = getStSalesTargetCompareList(stSalesTargetSearch, "organize");
			
			if (stSalesTargetCompareList != null && stSalesTargetCompareList.size() > 0) {

				List<StSalesTargetCompare> stSalesTargetCompareTotalList = getStSalesTargetCompareTotalList(stSalesTargetCompareList);
				stSalesTargetCompareList.addAll(stSalesTargetCompareTotalList);
				
				stSalesTargetSearch.setStSalesTargetCompareList(stSalesTargetCompareList);
//				stSalesTargetSearch.setStSalesTargetCompareTotalList(stSalesTargetCompareTotalList);
			} else {

				stSalesTargetSearch.setStSalesTargetCompareList(null);
//				stSalesTargetSearch.setStSalesTargetCompareTotalList(null);
			}
		}

		return stSalesTargetSearch;
	}
	public List<StSalesTargetCompareOrganizeExcel> exportOrganizeList(StSalesTargetSearch stSalesTargetSearch) {
		
		List<StSalesTargetCompareOrganizeExcel> stSalesTargetCompareOrganizeExcelList = Lists.newArrayList();
		
		if (!StringUtils.isEmptyNull(stSalesTargetSearch.getYear())) {

			List<StSalesTargetCompare> stSalesTargetCompareList = getStSalesTargetCompareList(stSalesTargetSearch, "organize");
			
			if (stSalesTargetCompareList != null && stSalesTargetCompareList.size() > 0) {
				List<StSalesTargetCompare> stSalesTargetCompareTotalList = getStSalesTargetCompareTotalList(stSalesTargetCompareList);
				stSalesTargetCompareList.addAll(stSalesTargetCompareTotalList);

				StSalesTargetCompareOrganizeExcel stSalesTargetCompareOrganizeExcel = null;

				for (StSalesTargetCompare stSalesTargetCompare : stSalesTargetCompareList) {
					stSalesTargetCompareOrganizeExcel = new StSalesTargetCompareOrganizeExcel();

					stSalesTargetCompareOrganizeExcel.setOrganizeName(stSalesTargetCompare.getOrganizeName());
					
					if (StringUtils.isEmptyNull(stSalesTargetCompare.getOrganize())) {
						stSalesTargetCompareOrganizeExcel.setTotalTitle("合计");
					}
					stSalesTargetCompareOrganizeExcel.setTotalActualFinish(StringUtils.numberFormat(stSalesTargetCompare.getTotalActualFinish()));
					stSalesTargetCompareOrganizeExcel.setTotalAmount(StringUtils.numberFormat(stSalesTargetCompare.getTotalAmount()));

					if (StringUtils.equals(stSalesTargetCompare.getType(), "1")) {

						stSalesTargetCompareOrganizeExcel.setType("税前");

						stSalesTargetCompareOrganizeExcel.setTotalMonthly1(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly1()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly2(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly2()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly3(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly3()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly4(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly4()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly5(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly5()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly6(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly6()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly7(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly7()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly8(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly8()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly9(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly9()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly10(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly10()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly11(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly11()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly12(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalMonthly12()));
						stSalesTargetCompareOrganizeExcel.setTotalUnDeliver(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalUndelivered()));
						stSalesTargetCompareOrganizeExcel.setTotalDelivered(StringUtils.numberFormat(stSalesTargetCompare.getPreTotalDelivered()));

						stSalesTargetCompareOrganizeExcel.setTotalTarget(StringUtils.numberFormat(stSalesTargetCompare.getTotalTarget()));
						stSalesTargetCompareOrganizeExcel.setCommission(StringUtils.numberFormat(stSalesTargetCompare.getCommission()));
						stSalesTargetCompareOrganizeExcel.setCompletionRate(stSalesTargetCompare.getCompletionRate());
						stSalesTargetCompareOrganizeExcel.setCompletionRateActual(stSalesTargetCompare.getCompletionRateActual());
					} else if (StringUtils.equals(stSalesTargetCompare.getType(), "2")) {

						stSalesTargetCompareOrganizeExcel.setType("税后");

						stSalesTargetCompareOrganizeExcel.setTotalMonthly1(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly1()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly2(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly2()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly3(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly3()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly4(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly4()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly5(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly5()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly6(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly6()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly7(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly7()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly8(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly8()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly9(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly9()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly10(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly10()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly11(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly11()));
						stSalesTargetCompareOrganizeExcel.setTotalMonthly12(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalMonthly12()));
						stSalesTargetCompareOrganizeExcel.setTotalUnDeliver(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalUndelivered()));
						stSalesTargetCompareOrganizeExcel.setTotalDelivered(StringUtils.numberFormat(stSalesTargetCompare.getAfterTotalDelivered()));
					}
					
					stSalesTargetCompareOrganizeExcelList.add(stSalesTargetCompareOrganizeExcel);
				}
			}
		}
		
		return stSalesTargetCompareOrganizeExcelList;
	}
	
	public List<StSalesTargetCompare> getStSalesTargetCompareList(StSalesTargetSearch stSalesTargetSearch, String type) {
		
		List<StSalesTargetCompare> stSalesTargetCompareList = null;
		StSalesTarget stSalesTarget = null;
		if (StringUtils.equals(type, "employee")) {
			
			stSalesTarget = new StSalesTarget();
			stSalesTarget.setYear(stSalesTargetSearch.getYear());
			stSalesTarget.setOrganize(stSalesTargetSearch.getOrganize());
			stSalesTargetCompareList = stSalesTargetDao.getEmployeeCompareStSalesTargetDtl(stSalesTarget);
		} else if (StringUtils.equals(type, "organize")) {
			
			stSalesTarget = new StSalesTarget();
			stSalesTarget.setYear(stSalesTargetSearch.getYear());
			stSalesTargetCompareList = stSalesTargetDao.getOrganizeCompareStSalesTargetDtl(stSalesTarget);
		}
		List<StSalesTargetCompare> resStSalesTargetCompareList = Lists.newArrayList();
		StSalesTargetCompare preStSalesTargetCompare = null;
		StSalesTargetCompare afterStSalesTargetCompare = null;
		for (StSalesTargetCompare stSalesTargetCompare : stSalesTargetCompareList) {
			preStSalesTargetCompare = SerializationUtils.clone(stSalesTargetCompare);
			preStSalesTargetCompare.setType("1");
			
			BigDecimal preTotalAmount = null;

			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly1(), preTotalAmount);
			
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly2(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly3(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly4(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly5(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly6(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly7(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly8(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly9(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly10(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly11(), preTotalAmount);
		
			preTotalAmount = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly12(), preTotalAmount);
		
			
			BigDecimal preTotalActualFinish = StringUtils.subtract(preTotalAmount, stSalesTargetCompare.getCommission());
			
			preStSalesTargetCompare.setCompletionRate(StringUtils.division(preTotalAmount, stSalesTargetCompare.getTotalTarget()));
			preStSalesTargetCompare.setCompletionRateActual(StringUtils.division(preTotalActualFinish, stSalesTargetCompare.getTotalTarget()));
			
			preStSalesTargetCompare.setTotalAmount(preTotalAmount);
			preStSalesTargetCompare.setTotalActualFinish(preTotalActualFinish);

			afterStSalesTargetCompare = SerializationUtils.clone(stSalesTargetCompare);
			afterStSalesTargetCompare.setType("2");
			
			BigDecimal afterTotalAmount = null;
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly1(), afterTotalAmount);
			
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly2(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly3(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly4(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly5(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly6(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly7(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly8(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly9(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly10(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly11(), afterTotalAmount);
		
			afterTotalAmount = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly12(), afterTotalAmount);
		
			BigDecimal afterTotalActualFinish = StringUtils.subtract(afterTotalAmount, stSalesTargetCompare.getCommission());
			
			afterStSalesTargetCompare.setTotalAmount(afterTotalAmount);
			afterStSalesTargetCompare.setTotalActualFinish(afterTotalActualFinish);
			
			resStSalesTargetCompareList.add(preStSalesTargetCompare);
			resStSalesTargetCompareList.add(afterStSalesTargetCompare);
		}
		
		return resStSalesTargetCompareList;
	}
	
	public List<StSalesTargetCompare> getStSalesTargetCompareTotalList(List<StSalesTargetCompare> stSalesTargetCompareList) {
		
		BigDecimal preTotalMonthly1 = null; // 1月份总额（税前）
		BigDecimal preTotalMonthly2 = null; // 2月份总额（税前）
		BigDecimal preTotalMonthly3 = null; // 3月份总额（税前）
		BigDecimal preTotalMonthly4 = null; // 4月份总额（税前）
		BigDecimal preTotalMonthly5 = null; // 5月份总额（税前）
		BigDecimal preTotalMonthly6 = null; // 6月份总额（税前）
		BigDecimal preTotalMonthly7 = null; // 7月份总额（税前）
		BigDecimal preTotalMonthly8 = null; // 8月份总额（税前）
		BigDecimal preTotalMonthly9 = null; // 9月份总额（税前）
		BigDecimal preTotalMonthly10 = null; // 10月份总额（税前）
		BigDecimal preTotalMonthly11 = null; // 11月份总额（税前）
		BigDecimal preTotalMonthly12 = null; // 12月份总额（税前）
		BigDecimal preTotalDelivered = null; // 已发货未开票（税前）
		BigDecimal preTotalUnDeliver = null; // 未发货未开票（税前）

		BigDecimal afterTotalMonthly1 = null; // 1月份总额（税后）
		BigDecimal afterTotalMonthly2 = null; // 2月份总额（税后）
		BigDecimal afterTotalMonthly3 = null; // 3月份总额（税后）
		BigDecimal afterTotalMonthly4 = null; // 4月份总额（税后）
		BigDecimal afterTotalMonthly5 = null; // 5月份总额（税后）
		BigDecimal afterTotalMonthly6 = null; // 6月份总额（税后）
		BigDecimal afterTotalMonthly7 = null; // 7月份总额（税后）
		BigDecimal afterTotalMonthly8 = null; // 8月份总额（税后）
		BigDecimal afterTotalMonthly9 = null; // 9月份总额（税后）
		BigDecimal afterTotalMonthly10 = null; // 10月份总额（税后）
		BigDecimal afterTotalMonthly11 = null; // 11月份总额（税后）
		BigDecimal afterTotalMonthly12 = null; // 12月份总额（税后）
		BigDecimal afterTotalDelivered = null; // 已发货未开票（税后）
		BigDecimal afterTotalUnDeliver = null; // 未发货未开票（税后）

		BigDecimal preTotalAmount = null; // 合计（税前）
		BigDecimal preTotalActualFinish = null; // 实际完成（税前）

		BigDecimal afterTotalAmount = null; // 合计（税后）
		BigDecimal afterTotalActualFinish = null; // 实际完成（税后）

		BigDecimal totalTarget = null; // 目标
		BigDecimal commission = null; // 佣金
		
		List<StSalesTargetCompare> resStSalesTargetCompareList = Lists.newArrayList();
		StSalesTargetCompare preStSalesTargetCompare = new StSalesTargetCompare();
		StSalesTargetCompare afterStSalesTargetCompare = new StSalesTargetCompare();
		
		for (StSalesTargetCompare stSalesTargetCompare : stSalesTargetCompareList) {
			if (StringUtils.equals(stSalesTargetCompare.getType(), "1")) {

				preTotalMonthly1 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly1(), preTotalMonthly1);
				
				preTotalMonthly2 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly2(), preTotalMonthly2);
			
				preTotalMonthly3 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly3(), preTotalMonthly3);
			
				preTotalMonthly4 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly4(), preTotalMonthly4);
			
				preTotalMonthly5 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly5(), preTotalMonthly5);
			
				preTotalMonthly6 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly6(), preTotalMonthly6);
			
				preTotalMonthly7 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly7(), preTotalMonthly7);
			
				preTotalMonthly8 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly8(), preTotalMonthly8);
			
				preTotalMonthly9 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly9(), preTotalMonthly9);
			
				preTotalMonthly10 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly10(), preTotalMonthly10);
			
				preTotalMonthly11 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly11(), preTotalMonthly11);
			
				preTotalMonthly12 = StringUtils.add(stSalesTargetCompare.getPreTotalMonthly12(), preTotalMonthly12);
			
				preTotalDelivered = StringUtils.add(stSalesTargetCompare.getPreTotalDelivered(), preTotalDelivered);
			
				preTotalUnDeliver = StringUtils.add(stSalesTargetCompare.getPreTotalUndelivered(), preTotalUnDeliver);
			
				preTotalAmount = StringUtils.add(stSalesTargetCompare.getTotalAmount(), preTotalAmount);
			
				preTotalActualFinish = StringUtils.add(stSalesTargetCompare.getTotalActualFinish(), preTotalActualFinish);
			
				totalTarget = StringUtils.add(stSalesTargetCompare.getTotalTarget(), totalTarget);
			
				commission = StringUtils.add(stSalesTargetCompare.getCommission(), commission);
			} else if (StringUtils.equals(stSalesTargetCompare.getType(), "2")) {

				afterTotalMonthly1 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly1(), afterTotalMonthly1);
				
				afterTotalMonthly2 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly2(), afterTotalMonthly2);
			
				afterTotalMonthly3 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly3(), afterTotalMonthly3);
			
				afterTotalMonthly4 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly4(), afterTotalMonthly4);
			
				afterTotalMonthly5 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly5(), afterTotalMonthly5);
			
				afterTotalMonthly6 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly6(), afterTotalMonthly6);
			
				afterTotalMonthly7 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly7(), afterTotalMonthly7);
			
				afterTotalMonthly8 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly8(), afterTotalMonthly8);
			
				afterTotalMonthly9 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly9(), afterTotalMonthly9);
			
				afterTotalMonthly10 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly10(), afterTotalMonthly10);
			
				afterTotalMonthly11 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly11(), afterTotalMonthly11);
			
				afterTotalMonthly12 = StringUtils.add(stSalesTargetCompare.getAfterTotalMonthly12(), afterTotalMonthly12);
			
				afterTotalDelivered = StringUtils.add(stSalesTargetCompare.getAfterTotalDelivered(), afterTotalDelivered);
			
				afterTotalUnDeliver = StringUtils.add(stSalesTargetCompare.getAfterTotalUndelivered(), afterTotalUnDeliver);
			
				afterTotalAmount = StringUtils.add(stSalesTargetCompare.getTotalAmount(), afterTotalAmount);
			
				afterTotalActualFinish = StringUtils.add(stSalesTargetCompare.getTotalActualFinish(), afterTotalActualFinish);
			}
		}
		preStSalesTargetCompare.setType("1");
		preStSalesTargetCompare.setPreTotalMonthly1(preTotalMonthly1);
		
		preStSalesTargetCompare.setPreTotalMonthly2(preTotalMonthly2);
		
		preStSalesTargetCompare.setPreTotalMonthly3(preTotalMonthly3);
		
		preStSalesTargetCompare.setPreTotalMonthly4(preTotalMonthly4);
		
		preStSalesTargetCompare.setPreTotalMonthly5(preTotalMonthly5);
		
		preStSalesTargetCompare.setPreTotalMonthly6(preTotalMonthly6);
		
		preStSalesTargetCompare.setPreTotalMonthly7(preTotalMonthly7);
		
		preStSalesTargetCompare.setPreTotalMonthly8(preTotalMonthly8);
		
		preStSalesTargetCompare.setPreTotalMonthly9(preTotalMonthly9);
		
		preStSalesTargetCompare.setPreTotalMonthly10(preTotalMonthly10);
		
		preStSalesTargetCompare.setPreTotalMonthly11(preTotalMonthly11);
		
		preStSalesTargetCompare.setPreTotalMonthly12(preTotalMonthly12);
		
		preStSalesTargetCompare.setPreTotalDelivered(preTotalDelivered);
		
		preStSalesTargetCompare.setPreTotalUndelivered(preTotalUnDeliver);
		
		preStSalesTargetCompare.setTotalAmount(preTotalAmount);
		
		preStSalesTargetCompare.setTotalActualFinish(preTotalActualFinish);
		
		preStSalesTargetCompare.setTotalTarget(totalTarget);
		
		preStSalesTargetCompare.setCommission(commission);
		
		preStSalesTargetCompare.setCompletionRate(StringUtils.division(preTotalAmount, totalTarget));
		preStSalesTargetCompare.setCompletionRateActual(StringUtils.division(preTotalActualFinish, totalTarget));

		resStSalesTargetCompareList.add(preStSalesTargetCompare);

		afterStSalesTargetCompare.setType("2");
		afterStSalesTargetCompare.setAfterTotalMonthly1(afterTotalMonthly1);

		afterStSalesTargetCompare.setAfterTotalMonthly2(afterTotalMonthly2);

		afterStSalesTargetCompare.setAfterTotalMonthly3(afterTotalMonthly3);

		afterStSalesTargetCompare.setAfterTotalMonthly4(afterTotalMonthly4);

		afterStSalesTargetCompare.setAfterTotalMonthly5(afterTotalMonthly5);

		afterStSalesTargetCompare.setAfterTotalMonthly6(afterTotalMonthly6);

		afterStSalesTargetCompare.setAfterTotalMonthly7(afterTotalMonthly7);

		afterStSalesTargetCompare.setAfterTotalMonthly8(afterTotalMonthly8);

		afterStSalesTargetCompare.setAfterTotalMonthly9(afterTotalMonthly9);

		afterStSalesTargetCompare.setAfterTotalMonthly10(afterTotalMonthly10);

		afterStSalesTargetCompare.setAfterTotalMonthly11(afterTotalMonthly11);

		afterStSalesTargetCompare.setAfterTotalMonthly12(afterTotalMonthly12);

		afterStSalesTargetCompare.setAfterTotalDelivered(afterTotalDelivered);

		afterStSalesTargetCompare.setAfterTotalUndelivered(afterTotalUnDeliver);

		afterStSalesTargetCompare.setTotalAmount(afterTotalAmount);

		afterStSalesTargetCompare.setTotalActualFinish(afterTotalActualFinish);
		
		resStSalesTargetCompareList.add(afterStSalesTargetCompare);
		return resStSalesTargetCompareList;
	}
}