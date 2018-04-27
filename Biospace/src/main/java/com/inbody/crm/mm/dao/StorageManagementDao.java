/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.mm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.mm.domain.UnrestoredSearch;

/**
 * 主子表DAO接口
 * @author zhanglulu
 * @version 2017-08-15
 */
@MyBatisDao
public interface StorageManagementDao {

	/**
	 * 待归还查询一览画面，待归还list分页查询
	 * 
	 * @param unrestoredSearch
	 *            待归还查询条件
	 * @return 待归还list
	 */
	List<UnrestoredSearch> findUnrestoredPageList(UnrestoredSearch unrestoredSearch);

}