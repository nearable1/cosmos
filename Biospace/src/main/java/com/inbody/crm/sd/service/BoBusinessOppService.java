/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.sd.dao.BoBusinessOppDao;
import com.inbody.crm.sd.dao.BoBusinessOppDtlDao;
import com.inbody.crm.sd.dao.ImInvoiceDao;
import com.inbody.crm.sd.entity.BoBusinessOpp;
import com.inbody.crm.sd.entity.BoBusinessOppDtl;
import com.inbody.crm.sd.entity.CmCustomerInfo;

/**
 * 主子表生成Service
 * @author Nssol
 * @version 2017-07-20
 */
@Service
@Transactional(readOnly = true)
public class BoBusinessOppService extends CrudService<BoBusinessOppDao, BoBusinessOpp> {

	@Autowired
	private BoBusinessOppDtlDao boBusinessOppDtlDao;
	@Autowired
	private BoBusinessOppDao boBusinessOppDao;
	@Autowired
	private ImInvoiceDao imInvoiceDao;
	
	public BoBusinessOpp get(String id) {
		BoBusinessOpp boBusinessOpp = super.get(id);
		boBusinessOpp.setBoBusinessOppDtlList(boBusinessOppDtlDao.findList(new BoBusinessOppDtl(boBusinessOpp)));
		return boBusinessOpp;
	}
	
	public List<BoBusinessOpp> findList(BoBusinessOpp boBusinessOpp) {
		return super.findList(boBusinessOpp);
	}
	
	public List<BoBusinessOpp> findListByCustomer(BoBusinessOpp boBusinessOpp) {
		return boBusinessOppDao.findListByCustomer(boBusinessOpp);
	}
	
	
	public List<BoBusinessOpp> findListByCustomerId(BoBusinessOpp boBusinessOpp) {
		return boBusinessOppDao.findListByCustomerId(boBusinessOpp);
	}
	
	public Page<BoBusinessOpp> findPage(Page<BoBusinessOpp> page, BoBusinessOpp boBusinessOpp) {
		return super.findPage(page, boBusinessOpp);
	}

	public List<BoBusinessOpp> findExportList(BoBusinessOpp boBusinessOpp) {
		List<BoBusinessOpp> businessList =  super.findList(boBusinessOpp);
		for(BoBusinessOpp businessOpp : businessList){
			businessOpp.setExpTurnoverMonthStr(DateUtils.formatDate(businessOpp.getExpTurnoverMonth(), "yyyy-MM"));
		}
		return businessList;
	}
	
	@Transactional(readOnly = false)
	public void save(BoBusinessOpp boBusinessOpp) {
		super.save(boBusinessOpp);
		
		//明细数据改为全删全增
		boBusinessOppDtlDao.deleteByBusinessNo(boBusinessOpp.getBusinessOppNo());
		int lineNo = 0;
		for (BoBusinessOppDtl boBusinessOppDtl : boBusinessOpp.getBoBusinessOppDtlList()){
			if (BoBusinessOppDtl.DEL_FLAG_NORMAL.equals(boBusinessOppDtl.getDelFlag())){
				lineNo ++;
				boBusinessOppDtl.setLineNo(String.valueOf(lineNo));
				boBusinessOppDtl.setBusinessOppNo(boBusinessOpp.getBusinessOppNo());
				boBusinessOppDtl.preInsert();
				boBusinessOppDtlDao.insert(boBusinessOppDtl);
			}
//			if (boBusinessOppDtl.getId() == null){
//				continue;
//			}
//			if (BoBusinessOppDtl.DEL_FLAG_NORMAL.equals(boBusinessOppDtl.getDelFlag())){
//				if (StringUtils.isBlank(boBusinessOppDtl.getId())){
//					boBusinessOppDtl.setBusinessOppNo(boBusinessOpp.getBusinessOppNo());
//					boBusinessOppDtl.preInsert();
//					boBusinessOppDtlDao.insert(boBusinessOppDtl);
//				}else{
//					boBusinessOppDtl.preUpdate();
//					boBusinessOppDtlDao.update(boBusinessOppDtl);
//				}
//			}else{
//				boBusinessOppDtlDao.delete(boBusinessOppDtl);
//			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BoBusinessOpp boBusinessOpp) {
		super.delete(boBusinessOpp);
		boBusinessOppDtlDao.delete(new BoBusinessOppDtl(boBusinessOpp));
	}

	@Transactional(readOnly = false)
	public void updateBoBusinessOpp(BoBusinessOpp boBusinessOpp) {
		boBusinessOppDao.updateSalesPlan(boBusinessOpp);
	}
	public CmCustomerInfo getCustomerInfo(String customerId) {
		return imInvoiceDao.getCustomerInfo(customerId);
	}
	
//	public PsPriceInfo getPsPriceInfo(PsPriceInfo psPriceInfo) {
//		return imInvoiceDao.getPsPriceInfo(psPriceInfo);
//	}
	
}