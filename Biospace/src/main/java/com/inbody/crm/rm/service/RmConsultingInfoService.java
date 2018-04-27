/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.modules.oa.dao.OaNotifyDao;
import com.inbody.crm.modules.oa.dao.OaNotifyRecordDao;
import com.inbody.crm.modules.oa.entity.OaNotify;
import com.inbody.crm.modules.oa.entity.OaNotifyRecord;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.rm.dao.RmConsultingInfoDao;
import com.inbody.crm.rm.domain.RmConsultingExcel;
import com.inbody.crm.rm.domain.RmConsultingSearch;
import com.inbody.crm.rm.entity.RmConsultingInfo;

/**
 * 单表生成Service
 * 
 * @author yangj
 * @version 2017-08-16
 */
@Service
@Transactional(readOnly = true)
public class RmConsultingInfoService
        extends CrudService<RmConsultingInfoDao, RmConsultingInfo> {

    @Autowired
    private CommonService commonService;

	@Autowired
	private OaNotifyRecordDao oaNotifyRecordDao;

	@Autowired
	private OaNotifyDao oaNotifyDao;

    public RmConsultingInfo get(String id) {
        return super.get(id);
    }

    public List<RmConsultingInfo> findList(RmConsultingInfo rmConsultingInfo) {
        return super.findList(rmConsultingInfo);
    }

    public Page<RmConsultingSearch> findPageList(Page<RmConsultingSearch> page,
            RmConsultingSearch search) {

        search.setPage(page);
        List<RmConsultingSearch> list = dao.findListBySelective(search);
        page.setList(list);
        return page;
    }

    @Transactional(readOnly = false)
    public RmConsultingInfo saveConsulting(RmConsultingInfo rmConsultingInfo) {

        // id为空保存
        if (StringUtils.isBlank(rmConsultingInfo.getId())) {
            // 咨询编号生成
            // "ZX"+YYMMDD+四位流水（0001~9999）以年流水
            String consultingNo = "ZX" + DateUtils.getDate("yyMMdd") + commonService
                    .getNextSequence(CommonConstants.TRANSACTION_CONSULTING, DateUtils.getDate("yyyy"), 4);
            if (StringUtils.isBlank(rmConsultingInfo.getConsultingType())) {
                rmConsultingInfo.setConsultingType(null);
            }
            if (StringUtils.isBlank(rmConsultingInfo.getConsultingStatus())) {
                rmConsultingInfo.setConsultingStatus(null);
            }
            rmConsultingInfo.setConsultingNo(consultingNo);
            super.save(rmConsultingInfo);
            
            if (StringUtils.equals(rmConsultingInfo.getConsultingStatus(), "4")) {
            	OaNotify oaNotify = new OaNotify();
            	oaNotify.setType("5");
            	oaNotify.setTitle(rmConsultingInfo.getId());
            	oaNotify.setContent(consultingNo);
            	oaNotify.setStatus(CommonConstants.YES);
            	oaNotify.setDelFlag(CommonConstants.NO);
            	
            	oaNotify.preInsert();
            	oaNotifyDao.insert(oaNotify);
            	
            	OaNotifyRecord oaNotifyRecord = new OaNotifyRecord();
            	oaNotifyRecord.setOaNotify(oaNotify);
            	oaNotifyRecord.setUser(UserUtils.get(rmConsultingInfo.getResponsiblePersonId()));
            	oaNotifyRecord.setReadFlag(CommonConstants.NO);
            	
            	oaNotifyRecord.preInsert();
            	oaNotifyRecordDao.insert(oaNotifyRecord);
            }

            return rmConsultingInfo;

        } else {
            // id不为空更新
            // 数据库更新前数据取得
            RmConsultingInfo dbConsultingInfo = dao.get(rmConsultingInfo.getId());
            // db数据为空时
            if (dbConsultingInfo == null) {
                throw new ServiceException("保存失败：数据不整合。");
            }

            // 更新排他处理
            if (DateUtils.compareDate(rmConsultingInfo.getUpdateDate(),
                    dbConsultingInfo.getUpdateDate()) != 0) {
                throw new ServiceException("保存失败：数据已经被更改，请取得最新数据后再操作。");
            }
            
            if (StringUtils.equals(rmConsultingInfo.getConsultingStatus(), "4")) {

            	OaNotify oaNotifysearch = new OaNotify();
            	oaNotifysearch.setTitle(rmConsultingInfo.getId());
            	OaNotifyRecord oaNotifyRecordsearch = new OaNotifyRecord();
            	oaNotifyRecordsearch.setOaNotify(oaNotifysearch);
            	oaNotifyRecordsearch.setUser(UserUtils.get(rmConsultingInfo.getResponsiblePersonId()));
            	oaNotifyRecordsearch.setReadFlag(CommonConstants.NO);
            	int count = oaNotifyRecordDao.findCountByUser(oaNotifyRecordsearch);

            	if (count == 0) {

                	OaNotify oaNotify = new OaNotify();
                	oaNotify.setType("5");
                	oaNotify.setTitle(rmConsultingInfo.getId());
                	oaNotify.setContent(dbConsultingInfo.getConsultingNo());
                	oaNotify.setStatus(CommonConstants.YES);
                	oaNotify.setDelFlag(CommonConstants.NO);
                	
                	oaNotify.preInsert();
                	oaNotifyDao.insert(oaNotify);
                	
                	OaNotifyRecord oaNotifyRecord = new OaNotifyRecord();
                	oaNotifyRecord.setOaNotify(oaNotify);
                	oaNotifyRecord.setUser(UserUtils.get(rmConsultingInfo.getResponsiblePersonId()));
                	oaNotifyRecord.setReadFlag(CommonConstants.NO);
                	
                	oaNotifyRecord.preInsert();
                	oaNotifyRecordDao.insert(oaNotifyRecord);
                
            	}
            }

            // 更新
            // 咨询日期
            dbConsultingInfo.setConsultingDate(rmConsultingInfo.getConsultingDate());
            // 咨询型号
            dbConsultingInfo.setMaterialNo(rmConsultingInfo.getMaterialNo());
            // 问题分类
            dbConsultingInfo.setConsultingType(rmConsultingInfo.getConsultingType());
            // 处理状态
            dbConsultingInfo.setConsultingStatus(rmConsultingInfo.getConsultingStatus());
            // 负责工程师
            dbConsultingInfo.setResponsiblePersonId(rmConsultingInfo.getResponsiblePersonId());
            // 单位名称
            dbConsultingInfo.setCustomerName(rmConsultingInfo.getCustomerName());
            // 联系人
            dbConsultingInfo.setContactsName(rmConsultingInfo.getContactsName());
            // 电话
            dbConsultingInfo.setTelephone(rmConsultingInfo.getTelephone());
            // 备注
            dbConsultingInfo.setNewRemarks(rmConsultingInfo.getNewRemarks());
            dbConsultingInfo.preUpdate();
            dao.update(dbConsultingInfo);

            return dbConsultingInfo;
        }
    }

    /**
     * 无S/N咨询删除
     * 
     * @param id
     *            无S/N咨询记录id
     */
    @Transactional(readOnly = false)
    public void deleteConsulting(RmConsultingInfo rmConsultingInfo) {
    	
    	List<OaNotify> oaNotifyList = oaNotifyDao.findOaNotifyListByTitle(rmConsultingInfo.getId());
    	
    	if (oaNotifyList != null && oaNotifyList.size() > 0) {
    		for (OaNotify oaNotify : oaNotifyList) {
    			oaNotifyDao.delete(oaNotify);
    		}
    	}

        // 数据库更新前数据取得
        RmConsultingInfo dbConsultingInfo = dao.get(rmConsultingInfo.getId());

        // db数据为空时
        if (dbConsultingInfo == null) {
            throw new ServiceException("删除失败：删除记录不存在。");
        }

        // 更新排他处理
        if (DateUtils.compareDate(rmConsultingInfo.getUpdateDate(),
                dbConsultingInfo.getUpdateDate()) != 0) {
            throw new ServiceException("保存失败：数据已经被更改，请取得最新数据后再操作。");
        }

        // 删除
        dao.delete(dbConsultingInfo);
    }

    /**
     * 无S/N咨询处理状态更新
     * 
     * @param rmConsultingInfo
     *            无S/N咨询记录
     */
    @Transactional(readOnly = false)
    public RmConsultingInfo updateConsultingStatus(RmConsultingInfo rmConsultingInfo) {

    	rmConsultingInfo.setConsultingStatus("2");
        // 更新
    	rmConsultingInfo.preUpdate();
        dao.update(rmConsultingInfo);
        
        return rmConsultingInfo;
    }

    /**
     * 无S/N咨询一览导出数据取得
     * 
     * @param pmPurchaseOrdSearch
     *            导出采购订单一览查询条件
     * @return 采购订单一览
     */
    public List<RmConsultingExcel> exportConsultingList(RmConsultingSearch search) {

        List<RmConsultingSearch> resultList = dao.findListBySelective(search);

        RmConsultingExcel consExcel = null;
        List<RmConsultingExcel> consExcelList = Lists.newArrayList();
        for (RmConsultingSearch cons : resultList) {
            consExcel = new RmConsultingExcel();
            consExcel.setConsultingNo(cons.getConsultingNo());
            consExcel.setConsultingType(cons.getConsultingType());
            consExcel.setMaterialName(cons.getMaterialName());
            consExcel.setConsultingDate(
                    DateUtils.formatDate(cons.getConsultingDate(), "yyyy-MM-dd"));
            consExcel.setCreateName(cons.getCreateBy().getName());
            consExcel.setConsultingStatus(cons.getConsultingStatus());
            consExcel.setResponsiblePersonName(StringUtils.defaultString(cons.getResponsiblePersonName()));
            consExcel.setCustomerName(cons.getCustomerName());
            consExcel.setContactsName(cons.getContactsName());
            consExcel.setTelephone(cons.getTelephone());

            consExcelList.add(consExcel);
        }
        return consExcelList;
    }

}