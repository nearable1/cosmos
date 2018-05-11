/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.gym.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.modules.gym.entity.Gym;
import com.inbody.crm.modules.gym.dao.GymDao;

/**
 * 健身知识Service
 * @author liuyigeng
 * @version 2018-05-02
 */
@Service
@Transactional(readOnly = true)
public class GymService extends CrudService<GymDao, Gym> {

	public Gym get(String id) {
		return super.get(id);
	}
	
	public List<Gym> findList(Gym gym) {
		return super.findList(gym);
	}
	
	public Page<Gym> findPage(Page<Gym> page, Gym gym) {
		return super.findPage(page, gym);
	}
	
	@Transactional(readOnly = false)
	public void save(Gym gym) {
		super.save(gym);
	}
	
	@Transactional(readOnly = false)
	public void delete(Gym gym) {
		super.delete(gym);
	}
	
}