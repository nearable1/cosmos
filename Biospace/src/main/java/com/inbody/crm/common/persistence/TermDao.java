package com.inbody.crm.common.persistence;

import java.util.List;

/**
 * DAO（带区域和期间）支持类实现
 * 
 * @author nssol.x.lzt
 * @version 2017-05
 * @param <T>
 */
public interface TermDao<T> extends CrudDao<T> {
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id, String locale);
	
	/**
	 * 获取对应id的全期间，区域数据列表
	 * @param id
	 * @return
	 */
	public List<T> getTermList(String id);
	
}
