/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.pm.domain.PmPurchaseOrdSearch;
import com.inbody.crm.pm.entity.PmPurchaseOrdDtl;

/**
 * 主子表DAO接口
 * @author yangj
 * @version 2017-07-24
 */
@MyBatisDao
public interface PmPurchaseOrdDtlDao extends CrudDao<PmPurchaseOrdDtl> {
	
	/**
	 * 通过采购订单号取得采购明细
	 * 
	 * @param purchaseNo
	 *            采购订单号
	 * 
	 * @return 采购明细记录
	 */
	PmPurchaseOrdDtl getByPurchaseNo(String purchaseNo);

	/**
	 * 查询foc配件采购明细
	 * 
	 * @param pmPurchaseOrdDtl
	 *            查询条件
	 * @return foc配件采购明细List
	 */
	List<PmPurchaseOrdDtl> findFocList(PmPurchaseOrdDtl pmPurchaseOrdDtl);

	/**
	 * 采购订单画面，采购订单list分页查询
	 * 
	 * @param pmPurchaseOrdSearch
	 *            采购订单查询条件
	 * @return 采购订单list
	 */
	List<PmPurchaseOrdSearch> findPageList(
			PmPurchaseOrdSearch pmPurchaseOrdSearch);
	
	/**
	 * 配件采购明细导出数据取得
	 * 
	 * @param pmPurchaseOrdDtl
	 *            查询条件
	 * @return 配件采购明细导出数据
	 */
	List<PmPurchaseOrdDtl> findAccPurDtlExportList(
			PmPurchaseOrdDtl pmPurchaseOrdDtl);
	
	/**
	 * 采购订单入库明细取得
	 */
	List<PmPurchaseOrdDtl> findStoragePurDtlList(
			PmPurchaseOrdDtl pmPurchaseOrdDtl);

}