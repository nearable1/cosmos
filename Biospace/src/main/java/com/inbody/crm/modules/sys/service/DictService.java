/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.CacheUtils;
import com.inbody.crm.modules.sys.dao.DictDao;
import com.inbody.crm.modules.sys.entity.Dict;
import com.inbody.crm.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}
	
	/**
	 * 获取指定类型的所有显示值
	 * @param type
	 * @return
	 */
	public String[] findValueListByType(String type)
	{
		Dict dic = new Dict();
		dic.setType(type);
		List<Dict> list = dao.findList(dic);
		if(list != null && list.size() > 0)
		{
			String[] result = new String[list.size()];
			for(int i=0;i<list.size();i++)
				result[i] = list.get(i).getLabel();
			return result;
		}
		else{
			return null;
		}
	}

}
