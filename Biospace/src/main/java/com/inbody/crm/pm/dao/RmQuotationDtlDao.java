/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.pm.entity.RmQuotationDtl;

/**
 * 单表生成DAO接口
 * @author NSSOL
 * @version 2017-08-02
 */
@MyBatisDao("PurchaseQuotaDtlDao")
public interface RmQuotationDtlDao extends CrudDao<RmQuotationDtl> {

	/**
	 * 通过选择条件取得报价单列表
	 */
	List<RmQuotationDtl> getBySelective(RmQuotationDtl rmQuotationDtl);

	/**
	 * 取得没有被采购的报价单明细列表
	 */
	List<RmQuotationDtl> findNoPuredQtnDtlList(RmQuotationDtl rmQuotationDtl);

	/**
	 * 更新报价单是采购状态 ,能过报价单编号和行号
	 */
	int updatePurchaseStatus(RmQuotationDtl rmQuotationDtl);
}