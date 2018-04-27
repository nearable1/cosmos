package com.inbody.crm.common.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.persistence.TermDao;
import com.inbody.crm.common.persistence.TermEntity;
import com.inbody.crm.modules.sys.utils.UserUtils;

public abstract class TermService<D extends TermDao<T>, T extends TermEntity<T>>
		extends CrudService<D, T> {
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return get(id, UserUtils.getUser().getCurrentLocaleId());
	}
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id, String locale) {
		T entity = dao.get(id, locale);
		if(entity == null && !locale.equals(Global.DEFAULT_LOCALE)){
			entity = dao.get(id, Global.DEFAULT_LOCALE);
			if(entity != null){
				entity.setLocaleId(locale);
				entity.setIsNewRecord(true);
			}
		}
		return entity;
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity.getId(), entity.getCurrentUser().getCurrentLocaleId());
	}
	
	/**
	 * 获取对应id的全期间，区域数据列表
	 * @param id
	 * @return
	 */
	public List<T> getTermList(String id)
	{
		return dao.getTermList(id);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		T check = dao.get(entity.getId(), entity.getLocaleId());
		if(check == null)
			entity.setIsNewRecord(true);
		if (entity.getIsNewRecord() || entity.getIsNewTerm()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	/**
	 * 保存数据（插入或更新）（根据期间、区域进行区分）
	 * @param entity
	 * @param termList
	 */
	public void save(T entity, List<T> termList){
		
	}
}
