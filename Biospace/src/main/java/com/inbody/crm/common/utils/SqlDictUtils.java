package com.inbody.crm.common.utils;

import java.util.List;

import com.google.common.collect.Lists;
import com.inbody.crm.common.entity.SqlDictEntity;
import com.inbody.crm.modules.sys.dao.DictDao;
import com.inbody.crm.modules.sys.entity.Dict;

public class SqlDictUtils {
	
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";

	/**
	 * 通过sql取得下拉列表
	 */
	public static List<Dict> getSqlDictList(String type) {
		SqlDictEntity sqlDict = SpringContextHolder.getBean(type);
		List<Dict> sqlDictList = dictDao.findSqlListByType(sqlDict);
		if (sqlDictList == null) {
			sqlDictList = Lists.newArrayList();
		}
		return sqlDictList;
	}

}
