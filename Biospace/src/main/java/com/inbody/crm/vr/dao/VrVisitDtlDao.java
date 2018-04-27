/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.vr.entity.VrVisitDtl;

/**
 * 主子表生成DAO接口
 * @author Nssol
 * @version 2017-07-20
 */
@MyBatisDao
public interface VrVisitDtlDao extends CrudDao<VrVisitDtl> {
	
	/**
	 * 查询拜访记录父子表
	 * @param entity
	 * @return
	 */
	public List<VrVisitDtl> findAllParentList(VrVisitDtl entity);
	
	/**
	 * 根据主键查询
	 * @param entity
	 * @return
	 */
	public VrVisitDtl getObjectByKey(VrVisitDtl entity);
	
	/**
	 * 根据主键删除
	 * @param entity
	 * @return
	 */
	public void deleteById(VrVisitDtl entity);
	
	/**
	 * 根据visitNo
	 * @param entity
	 * @return
	 */
	public void deleteByVisitNo(VrVisitDtl entity);
	
	/**
	 * 根据流程加载拜访记录
	 * @param entity
	 * @return
	 */
	public List<VrVisitDtl> findAllListById(VrVisitDtl entity);
}