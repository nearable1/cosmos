/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.vr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.vr.dao.VrVisitDtlDao;
import com.inbody.crm.vr.entity.VrVisitDtl;

/**
 * 主子表生成Service
 * @author Nssol
 * @version 2017-07-20
 */
@Service
@Transactional(readOnly = true)
public class VrVisitDtlService extends CrudService<VrVisitDtlDao, VrVisitDtl> {

	
	public VrVisitDtl get(String id) {
		VrVisitDtl vrVisitDtl = super.get(id);
		return vrVisitDtl;
	}
	
	public List<VrVisitDtl> findAllParentList(VrVisitDtl vrVisitDtl) {
		return dao.findAllParentList(vrVisitDtl);
	}
	
	public VrVisitDtl getObjectByKey(VrVisitDtl vrVisitDtl) {
		return dao.getObjectByKey(vrVisitDtl);
	}
	
	public List<VrVisitDtl> findAllListById(VrVisitDtl vrVisitDtl) {
		return dao.findAllListById(vrVisitDtl);
	}
	
	public Page<VrVisitDtl> findPage(Page<VrVisitDtl> page, VrVisitDtl vrVisitDtl) {
		return super.findPage(page, vrVisitDtl);
	}
	
	@Transactional(readOnly = false)
	public void save(VrVisitDtl rVisitDtl) {
		super.save(rVisitDtl);
	}
	
	@Transactional(readOnly = false)
	public void delete(VrVisitDtl vrVisitDtl) {
		super.delete(vrVisitDtl);
	}
	
}