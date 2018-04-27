/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import java.util.List;

import com.inbody.crm.common.entity.SnInfo;
import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.domain.RmRepairInfoEx;
import com.inbody.crm.rm.domain.RmRepairListSearch;
import com.inbody.crm.rm.domain.RmRepairSnInfo;
import com.inbody.crm.rm.domain.RmRepairSnSearch;
import com.inbody.crm.rm.entity.RmRepairInfo;

/**
 * 维修信息DAO接口
 * @author yangj
 * @version 2017-08-22
 */
@MyBatisDao
public interface RmRepairInfoDao extends CrudDao<RmRepairInfo> {

	/** 获取sn查询合同信息 */
	List<RmRepairSnSearch> getRepairOrderInfo(RmRepairSnSearch param);

	/** 获取sn查询维修信息 */
	List<RmRepairSnSearch> getRepairInfo(RmRepairSnSearch param);

	/** 获取sn查询最新检测信息 */
	List<RmRepairSnSearch> getTestingInfo(RmRepairSnSearch param);
	
	/** 根据id取得维修录入信息 */
	RmRepairInfoEx getRepairInfoById(String id);

    /** 根据维修编号取得维修录入信息 */
    RmRepairInfoEx getRepairInfoByNo(String repairNo);

	/** 取得维修录入sn信息 */
	RmRepairSnInfo getRepairSnInfo(String snNo);

	/** 取得sn录入历史维修信息 */
	List<RmRepairInfoEx> getRepairHistory(String snNo);

	/** 取得需要录入维修信息的sn是否存在及是否存在合同 */
//	int getRepairSnCount(String snNo);
	
	/** 获取样机信息 */
	RmRepairInfoEx getPrototypeInfo(String snNo);
	
	/** 维修记录管理一览数据取得 */
	List<RmRepairListSearch> getRepairInfoList(RmRepairListSearch search);
	
	/** 维修记录管理excel导出数据取得 */
    List<RmRepairListSearch> getRepairInfoExportList(RmRepairListSearch search);

	/** 取得sn信息 */
    SnInfo getSnInfo(String snNo);
    
    int updateSnInfo(SnInfo snInfo);
}