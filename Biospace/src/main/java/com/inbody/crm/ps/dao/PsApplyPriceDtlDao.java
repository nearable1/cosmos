/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ps.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.ps.entity.PsApplyPriceDtl;

/**
 * 主子表生成DAO接口
 * @author Nssol
 * @version 2017-07-20
 */
@MyBatisDao
public interface PsApplyPriceDtlDao extends CrudDao<PsApplyPriceDtl> {
	/**
	 * 案件结束处理：将申请单中的数据复制到last表
	 * @param applyId
	 */
	public void onProcessEnd(String applyId);

	public List<PsApplyPriceDtl> searchList(PsApplyPriceDtl psApplyPriceDtl);
	
	/**
	 * 查找代理商目标中对应协议编号数量目标的所有物料信息
	 * 返回物料编码
	 * @param agreementId
	 * @return
	 */
	public List<String> findAgentTargetMaterial(String agreementId);
}