/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.im.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.im.dao.ImInvoiceDtlDao;
import com.inbody.crm.im.domain.AxImInvoiceDtlExcel;
import com.inbody.crm.im.domain.ImInvoiceDtlExcel;
import com.inbody.crm.im.domain.ImInvoiceSearch;
import com.inbody.crm.modules.sys.entity.Dict;

/**
 * 主子表Service
 * @author zhanglulu
 * @version 2017-08-15
 */
@Service
@Transactional(readOnly = true)
public class ImInvoiceService {

	@Autowired
	private ImInvoiceDtlDao imInvoiceDtlDao;

	/**
	 * 发票查询结果取得
	 * 
	 * @param page
	 * @param imInvoiceSearch
	 * @return
	 */
	public Page<ImInvoiceSearch> findImInvoicePage(
			Page<ImInvoiceSearch> page,
			ImInvoiceSearch imInvoiceSearch) {
		
		imInvoiceSearch.setPage(page);
		List<ImInvoiceSearch> imInSearchList = imInvoiceDtlDao
				.findPageList(imInvoiceSearch);
		page.setList(imInSearchList);
		return page;
	}
	
	/**
	 * 组信息取得
	 * 
	 * @param invoiceSource
	 * @return
	 * 
	 */
//	public List<Dict> getOrganizeList(String invoiceSource) {
//		
//		List<Dict> organizeList = imInvoiceDtlDao.getOrganizeList(invoiceSource);
//		
//		if (organizeList == null) {
//			organizeList = Lists.newArrayList();
//		}
//		
//		return organizeList;
//		
//	}
	
	/**
	 * 提成人信息取得
	 * 
	 * @param organize
	 * @return
	 * 
	 */
	public List<Dict> getCommissionPeisonList(String organize) {
		
		List<Dict> commissionPeisonList = imInvoiceDtlDao.getCommissionPeisonList(organize);
		
		if (commissionPeisonList == null) {
			commissionPeisonList = Lists.newArrayList();
		}
		
		return commissionPeisonList;
		
	}
	
	/**
	 * 导出发票一览用发票数据取得
	 * 
	 * @param imInvoiceSearch
	 *            导出发票一览查询条件
	 * @return 发票一览
	 */
	public List<ImInvoiceDtlExcel> exportImInvoiceDtlList(
			ImInvoiceSearch imInvoiceSearch) {
		List<ImInvoiceSearch> imInSearchList = imInvoiceDtlDao
				.findPageList(imInvoiceSearch);
		
		ImInvoiceDtlExcel imInvoiceDtlExcel = null;
		List<ImInvoiceDtlExcel> imInvoiceDtlExcelList = Lists.newArrayList();

		for (ImInvoiceSearch imIn : imInSearchList) {
			imInvoiceDtlExcel = new ImInvoiceDtlExcel();

			imInvoiceDtlExcel.setAddress(imIn.getAddress());
			imInvoiceDtlExcel.setCommissionPeison(imIn.getCommissionPeison());
			imInvoiceDtlExcel.setCustomerName(imIn.getCustomerName());
			imInvoiceDtlExcel.setExpressCompany(imIn.getExpressCompany());
			imInvoiceDtlExcel.setExpressNo(imIn.getExpressNo());
			imInvoiceDtlExcel.setInvoiceAmount(StringUtils.numberFormat(imIn.getInvoiceAmount()));
			imInvoiceDtlExcel.setInvoiceDate(imIn.getInvoiceDate());
			imInvoiceDtlExcel.setInvoiceNo(imIn.getInvoiceNo());
			imInvoiceDtlExcel.setInvoiceSource(imIn.getInvoiceSource());
			imInvoiceDtlExcel.setInvoiceTitle(imIn.getInvoiceTitle());
			imInvoiceDtlExcel.setInvoiceType(imIn.getInvoiceType());
			imInvoiceDtlExcel.setLineNo(imIn.getLineNo());
			imInvoiceDtlExcel.setMaterialName(imIn.getMaterialName());
			imInvoiceDtlExcel.setNum(String.valueOf(imIn.getNum()));
			imInvoiceDtlExcel.setOrderNo(imIn.getOrderNo());
			imInvoiceDtlExcel.setOrganize(imIn.getOrganize());
			imInvoiceDtlExcel.setRecipients(imIn.getRecipients());
			imInvoiceDtlExcel.setRepTelephone(imIn.getRepTelephone());
			imInvoiceDtlExcel.setEmployeeName(imIn.getEmployeeName());
			imInvoiceDtlExcel.setResponsiblePersonName(imIn.getResponsiblePersonName());
			imInvoiceDtlExcel.setTax(StringUtils.numberFormat(imIn.getTax()));
			imInvoiceDtlExcel.setTicketMethod(imIn.getTicketMethod());
			imInvoiceDtlExcel.setUnitPrice(StringUtils.numberFormat(imIn.getUnitPrice()));

			imInvoiceDtlExcelList.add(imInvoiceDtlExcel);
		}
		return imInvoiceDtlExcelList;
	}
	
	/**
	 * 导出AX销售发票一览用发票数据取得
	 * 
	 * @param imInvoiceSearch
	 *            导出发票一览查询条件
	 * @return 发票一览
	 */
	public List<AxImInvoiceDtlExcel> exportAxImInvoiceDtlList(
			ImInvoiceSearch imInvoiceSearch) {
		List<ImInvoiceSearch> imInSearchList = imInvoiceDtlDao
				.findAxPageList(imInvoiceSearch);
		
		AxImInvoiceDtlExcel imInvoiceDtlExcel = null;
		List<AxImInvoiceDtlExcel> imInvoiceDtlExcelList = Lists.newArrayList();

		for (ImInvoiceSearch imIn : imInSearchList) {
			imInvoiceDtlExcel = new AxImInvoiceDtlExcel();

			imInvoiceDtlExcel.setBusinessUnit("ICNBU001");
			imInvoiceDtlExcel.setCurrency("CNY");
			if (StringUtils.isEmptyNull(imIn.getAxCusNo())) {

				imInvoiceDtlExcel.setCustomerAccount(imIn.getCustomerName());
				imInvoiceDtlExcel.setEndCustomer(imIn.getCustomerName());
				imInvoiceDtlExcel.setInvoiceAccount(imIn.getCustomerName());
			} else {

				imInvoiceDtlExcel.setCustomerAccount(imIn.getAxCusNo());
				imInvoiceDtlExcel.setEndCustomer(imIn.getAxCusNo());
				imInvoiceDtlExcel.setInvoiceAccount(imIn.getAxCusNo());
			}
			imInvoiceDtlExcel.setDeliveryaddress(imIn.getCustomerName());
			imInvoiceDtlExcel.setDeliverycontact(null);
			imInvoiceDtlExcel.setDeliveryterms(null);
			imInvoiceDtlExcel.setDepartment(imIn.getOrganize());
			imInvoiceDtlExcel.setDiscount(null);
			imInvoiceDtlExcel.setDiscountPercent(null);
			imInvoiceDtlExcel.setItemNumber(imIn.getMaterialNo());
			imInvoiceDtlExcel.setModeofdelivery(null);
			imInvoiceDtlExcel.setOnlineOrderNo(imIn.getOrderNo());
			imInvoiceDtlExcel.setOrderlinetext(null);
			if (StringUtils.equals(imIn.getPaymaentCon(), "1")) {

				imInvoiceDtlExcel.setPayment("InAdvance");
			} else {

				imInvoiceDtlExcel.setPayment("Other");
			}
			imInvoiceDtlExcel.setPaymentDueDate(null);
			imInvoiceDtlExcel.setQuantity(Integer.toString(imIn.getNum()));
			imInvoiceDtlExcel.setRequestedReceiptDate(imIn.getOrderDate());
			imInvoiceDtlExcel.setRequestedShipDate(imIn.getOrderDate());
			imInvoiceDtlExcel.setSet(null);
			imInvoiceDtlExcel.setSite("ICN");
			imInvoiceDtlExcel.setUnit("EA");
			imInvoiceDtlExcel.setUnitprice(StringUtils.numberFormat(imIn.getUnitPrice()));
			imInvoiceDtlExcel.setWarehouse("CN001");
			imInvoiceDtlExcel.setWorker(imIn.getEmployeeName());

			imInvoiceDtlExcelList.add(imInvoiceDtlExcel);
		}
		return imInvoiceDtlExcelList;
	}
}