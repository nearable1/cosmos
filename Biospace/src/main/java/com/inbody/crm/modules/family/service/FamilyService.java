/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.family.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.modules.family.dao.FamilyDao;
import com.inbody.crm.modules.family.entity.Family;
import com.inbody.crm.modules.family.entity.FamilyTwo;

/**
 * 商品类型Service
 * @author liuyigeng
 * @version 2018-05-05
 */
@Service
@Transactional(readOnly = true)
public class FamilyService extends CrudService<FamilyDao, Family> {

	public Family get(String id) {
		return super.get(id);
	}
	
	public List<Family> findList(Family family) {
		return super.findList(family);
	}
	
	public List<FamilyTwo> findFamily(String id) {
		return super.findFamily(id);
	}
	
	public Page<Family> findPage(Page<Family> page, Family family) {
		return super.findPage(page, family);
	}
	
	@Transactional(readOnly = false)
	public void save(Family family) {
		super.save(family);
	}
	
	@Transactional(readOnly = false)
	public void delete(Family family) {
		super.delete(family);
	}
	
}