/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.persistence;

import java.util.List;

import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;


/**
 * 合同明细DAO接口
 * @author yangj
 * @version 2017-09-08
 */
@MyBatisDao
public interface CoAttachmentsDao extends CrudDao<CoAttachments> {
	/** 根据关联id取得附件信息一览 */
	List<CoAttachments> findListByAssId(String assId);

	/** 根据id删除附件信息 */
	int deleteById(String id);

	/** 删除关联所对应的所有附件信息 */
	int deleteByAssId(String assId);
}