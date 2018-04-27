/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.pm.entity.SmMatInfo;

/**
 * 单表生成DAO接口
 * @author NSSOL
 * @version 2017-07-28
 */
@MyBatisDao
public interface SmMatInfoDao extends CrudDao<SmMatInfo> {
	
	/**
	 * 根据物料号和物料类型查询物料信息
	 * 
	 * @param smMatInfo 查询信息
	 * @return 物料信息
	 */
	SmMatInfo findByMaterialAndType(SmMatInfo smMatInfo);
	
	/**
	 * 根据物料号获取物料信息
	 * @param materialNo
	 * @return
	 */
	SmMatInfo getSmMatInfoByNo(String materialNo);
	
	/**
	 * 根据物料是否为配件取所有数据[价格申请中下载模板时使用]
	 * @param isPart
	 * @return
	 */
	List<SmMatInfo> getPartList(SmMatInfo smMatInfo);
	List<SmMatInfo> getNoPartList(SmMatInfo smMatInfo);
}