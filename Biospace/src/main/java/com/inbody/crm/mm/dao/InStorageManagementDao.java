/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.mm.entity.SmLendingMat;
import com.inbody.crm.mm.entity.SmStorageApp;
import com.inbody.crm.mm.entity.SmStorageAppDtl;
import com.inbody.crm.mm.entity.SmStorageInfo;
import com.inbody.crm.mm.entity.SmSnInfo;
import com.inbody.crm.mm.entity.SmWarehouseInfo;

/**
 * 主子表DAO接口
 * @author zhanglulu
 * @version 2017-08-15
 */
@MyBatisDao
public interface InStorageManagementDao {

	/**
	 * 根据ID取得出入库申请信息
	 * 
	 * @param id
	 * @return
	 */
	public SmStorageApp getSmStorageAppInfoById(String id);

	/**
	 * 更新出入库申请信息
	 * 
	 * @param smStorageApp
	 * @return
	 */
	public int updateSmStorageApp(SmStorageApp smStorageApp);

	/**
	 * 追加出入库申请信息
	 * 
	 * @param smStorageApp
	 * @return
	 */
	public int insertSmStorageApp(SmStorageApp smStorageApp);

	/**
	 * 追加出入库明细申请信息
	 * 
	 * @param smStorageAppDtl
	 * @return
	 */
	public int insertSmStorageAppDtl(SmStorageAppDtl smStorageAppDtl);
	
	/**
	 * 追加出入库信息
	 * 
	 * @param smStorageInfo
	 * @return
	 */
	int insertSmStorageInfo(SmStorageInfo smStorageInfo);

	/**
	 * 删除出入库明细申请信息
	 * 
	 * @param appId
	 * @return
	 */
	public int deleteSmStorageAppDtlByAppId(String appId);

	/**
	 * 其它入库申请(归还)申请画面显示结果取得
	 * 
	 * @param searchSmStorageApp
	 * @return
	 */
	public List<SmStorageAppDtl> getSendBackSmStorageAppDtl(SmStorageApp searchSmStorageApp);

	/**
	 * 其它入库申请(退货)申请画面显示结果取得
	 * 
	 * @param searchSmStorageApp
	 * @return
	 */
	public List<SmStorageAppDtl> getRefundSmStorageAppDtl(SmStorageApp searchSmStorageApp);

	/**
	 * 其它入库申请(换货)申请画面显示结果取得
	 * 
	 * @param searchSmStorageApp
	 * @return
	 */
	public List<SmStorageAppDtl> getExchangeSmStorageAppDtl(SmStorageApp searchSmStorageApp);

	/**
	 * 其它入库申请(其他)申请画面显示结果取得
	 * 
	 * @param searchSmStorageApp
	 * @return
	 */
	public List<SmStorageAppDtl> getOtherSmStorageAppDtl(SmStorageApp searchSmStorageApp);

	/**
	 * 其它入库申请(归还)申请画面初始化结果取得
	 * 
	 * @param searchSmStorageApp
	 * @return
	 */
	public List<SmStorageAppDtl> initSendBackSmStorageApp(SmStorageApp searchSmStorageApp);

	/**
	 * 其它入库申请(退货)申请画面查询结果取得
	 * 
	 * @param searchSmStorageApp
	 * @return
	 */
	public List<SmStorageAppDtl> searchRefundSmStorageAppDtl(SmStorageApp searchSmStorageApp);

	/**
	 * 其它入库申请(换货)申请画面查询结果取得
	 * 
	 * @param searchSmStorageApp
	 * @return
	 */
	public List<SmStorageAppDtl> searchExchangeSmStorageAppDtl(SmStorageApp searchSmStorageApp);

	/**
	 * 其它入库申请(其他)申请画面查询结果取得
	 * 
	 * @param searchSmStorageApp
	 * @return
	 */
	public List<SmStorageAppDtl> searchOtherSmStorageAppDtl(SmStorageApp searchSmStorageApp);

	/**
	 * 其它入库申请(其他)申请画面SN号码取得
	 * 
	 * @param smSnInfo
	 * @return
	 */
	public SmSnInfo getSmSnInfo(SmSnInfo smSnInfo);

	/**
	 * SN管理追加
	 * 
	 * @param smSnInfo
	 * @return
	 */
	public int insertSmSnInfo(SmSnInfo smSnInfo);

	/**
	 * SN管理更新
	 * 
	 * @param smSnInfo
	 * @return
	 */
	public int updateSmSnInfo(SmSnInfo smSnInfo);

	/**
	 * 其它入库申请画面在库信息取得
	 * 
	 * @param smWarehouseInfo
	 * @return
	 */
	List<SmWarehouseInfo> getSmWarehouseInfo(SmWarehouseInfo smWarehouseInfo);

	/**
	 * 库存管理追加
	 * 
	 * @param smWarehouseInfo
	 * @return
	 */
	public int insertSmWarehouseInfo(SmWarehouseInfo smWarehouseInfo);

	/**
	 * 库存管理更新
	 * 
	 * @param smWarehouseInfo
	 * @return
	 */
	public int updateSmWarehouseInfo(SmWarehouseInfo smWarehouseInfo);

	/**
	 * 借出物料管理取得
	 * 
	 * @param id
	 * @return
	 */
	public SmLendingMat getSmLendingMatById(String id);
	/**
	 * 借出物料管理更新
	 * 
	 * @param smLendingMat
	 * @return
	 */
	public int updateSmLendingMat(SmLendingMat smLendingMat);

	/**
	 * 借出物料管理删除
	 * 
	 * @param smLendingMat
	 * @return
	 */
	public int deleteSmLendingMat(SmLendingMat smLendingMat);

}