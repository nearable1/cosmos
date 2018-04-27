/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.im.dao;

import java.util.List;

import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.im.domain.ImInvoiceSearch;
import com.inbody.crm.modules.sys.entity.Dict;

/**
 * 主子表DAO接口
 * @author zhanglulu
 * @version 2017-08-15
 */
@MyBatisDao
public interface ImInvoiceDtlDao {

	/**
	 * 发票查询一览画面，发票list分页查询
	 * 
	 * @param imInvoiceSearch
	 *            发票查询条件
	 * @return 发票list
	 */
	List<ImInvoiceSearch> findPageList(
			ImInvoiceSearch imInvoiceSearch);

	List<ImInvoiceSearch> findAxPageList(
			ImInvoiceSearch imInvoiceSearch);
	
	//组下拉信息取得
//	List<Dict> getOrganizeList(String invoiceSource);
	
	//提成人下拉信息取得
	List<Dict> getCommissionPeisonList(String organize);

}