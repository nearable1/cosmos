package com.inbody.crm.common.persistence;

import java.util.List;

public interface TreeTermDao<T extends TreeTermEntity<T>> extends TermDao<T> {
	/**
	 * 找到所有子节点
	 * @param entity
	 * @return
	 */
	public List<T> findByParentIdsLike(T entity);

	/**
	 * 更新所有父节点字段
	 * @param entity
	 * @return
	 */
	public int updateParentIds(T entity);
}
