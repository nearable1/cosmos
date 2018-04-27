/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.mm.dao.StorageManagementDao;
import com.inbody.crm.mm.domain.UnrestoredExcel;
import com.inbody.crm.mm.domain.UnrestoredSearch;

/**
 * 主子表Service
 * @author zhanglulu
 * @version 2017-08-15
 */
@Service
@Transactional(readOnly = true)
public class StorageManagementService {

	@Autowired
	private StorageManagementDao storageManagementDao;

	/**
	 * 待归还查询结果取得
	 * 
	 * @param page
	 * @param unrestoredSearch
	 * @return
	 */
	public Page<UnrestoredSearch> findUnrestoredPage(
			Page<UnrestoredSearch> page,
			UnrestoredSearch unrestoredSearch) {
		
		unrestoredSearch.setPage(page);
		List<UnrestoredSearch> unrestoredSearchList = storageManagementDao.findUnrestoredPageList(unrestoredSearch);
		page.setList(unrestoredSearchList);
		return page;
	}
	
	/**
	 * 导出待归还一览用待归还数据取得
	 * 
	 * @param unrestoredSearch
	 *            导出待归还一览查询条件
	 * @return 待归还一览
	 */
	public List<UnrestoredExcel> exportUnrestoredList(
			UnrestoredSearch unrestoredSearch) {
		List<UnrestoredSearch> unrestoredSearchList = storageManagementDao
				.findUnrestoredPageList(unrestoredSearch);
		
		UnrestoredExcel unrestoredExcel = null;
		List<UnrestoredExcel> unrestoredExcelList = Lists.newArrayList();

		for (UnrestoredSearch unSearch : unrestoredSearchList) {
			unrestoredExcel = new UnrestoredExcel();

			unrestoredExcel.setLendingDateFrom(DateUtils.formatDate(unSearch.getLendingDateFrom(), "yyyy-MM-dd"));
			unrestoredExcel.setLendingDateTo(DateUtils.formatDate(unSearch.getLendingDateTo(), "yyyy-MM-dd"));
			unrestoredExcel.setProductionDate(DateUtils.formatDate(unSearch.getProductionDate(), "yyyy-MM-dd"));
			unrestoredExcel.setAccessoriesRemarks(unSearch.getAccessoriesRemarks());
			unrestoredExcel.setIndustry(unSearch.getIndustry());
			unrestoredExcel.setLendingName(unSearch.getLendingName());
			unrestoredExcel.setLendingType(unSearch.getLendingType());
			unrestoredExcel.setMaterialName(unSearch.getMaterialName());
			unrestoredExcel.setMaterialNo(unSearch.getMaterialNo());
			unrestoredExcel.setNewRemarks(unSearch.getNewRemarks());
			unrestoredExcel.setNum(unSearch.getNum());
			unrestoredExcel.setResponsiblePersonName(unSearch.getResponsiblePersonName());
			unrestoredExcel.setSnNo(unSearch.getSnNo());

			unrestoredExcelList.add(unrestoredExcel);
		}
		return unrestoredExcelList;
	}
}