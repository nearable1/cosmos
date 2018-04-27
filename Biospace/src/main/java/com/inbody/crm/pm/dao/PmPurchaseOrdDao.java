/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.dao;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.pm.entity.PmPurchaseOrd;

/**
 * 主子表DAO接口
 * @author yangj
 * @version 2017-07-24
 */
@MyBatisDao
public interface PmPurchaseOrdDao extends CrudDao<PmPurchaseOrd> {

	/**
	 * 取得采购订单信息
	 */
	PmPurchaseOrd getByPurchaseNo(PmPurchaseOrd pmPurchaseOrd);

	/**
	 * 取得入库采购订单信息
	 */
	PmPurchaseOrd getStoragePurchaseOrd(PmPurchaseOrd pmPurchaseOrd);

}