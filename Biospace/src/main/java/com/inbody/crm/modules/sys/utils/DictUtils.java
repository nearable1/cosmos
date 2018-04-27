/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.sys.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inbody.crm.common.mapper.JsonMapper;
import com.inbody.crm.common.utils.CacheUtils;
import com.inbody.crm.common.utils.SpringContextHolder;
import com.inbody.crm.modules.sys.dao.DictDao;
import com.inbody.crm.modules.sys.entity.Dict;
import com.inbody.crm.sd.dao.BoBusinessOppDao;
import com.inbody.crm.sd.entity.BoBusinessOpp;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	@Autowired
	private static BoBusinessOppDao boBusinessOppDao = SpringContextHolder.getBean(BoBusinessOppDao.class);
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}

	/*
	 * 下拉框联动，根据parentId取得下拉框列表
	 */
	public static List<Dict> getDictListByParent(String parentId,String type){
		@SuppressWarnings("unchecked")
//		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_DICT_MAP);
//		if (dictMap==null){
//			dictMap = Maps.newHashMap();
//			for (Dict dict : dictDao.findAllList(new Dict())){
//				List<Dict> dictList = dictMap.get(dict.getType());
//				if(parentId.equals(dict.getParentId())){
//					if (dictList != null){
//						dictList.add(dict);
//					}else{
//						dictMap.put(dict.getType(), Lists.newArrayList(dict));
//					}
//				}
//			}
//			CacheUtils.put(CACHE_DICT_MAP, dictMap);
//		}
//		List<Dict> dictList = dictMap.get(type);
		
		Dict dict = new Dict();
		dict.setType(type);
		dict.setParentId(parentId);
		List<Dict> dictList =  dictDao.findList(dict);
		
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	/*
	 * 下拉框联动，根据parentId取得下拉框列表
	 */
	public static List<BoBusinessOpp> getBussinessOpByParent(String parentId){
		BoBusinessOpp bean = new BoBusinessOpp();
		bean.setEndCustomerId(parentId);
		List<BoBusinessOpp> dictList = boBusinessOppDao.findListByCustomer(bean);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}
	
}
