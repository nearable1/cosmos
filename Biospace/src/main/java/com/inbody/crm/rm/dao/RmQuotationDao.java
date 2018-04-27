/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.domain.QuotaSelect;
import com.inbody.crm.rm.entity.CmCustomerInfo;
import com.inbody.crm.rm.entity.RmQuotation;

/**
 * 报价单DAO接口
 * @author yangj
 * @version 2017-09-04
 */
@MyBatisDao
public interface RmQuotationDao extends CrudDao<RmQuotation> {

    /** 根据报价单id取得报价单信息 */
    RmQuotation getQuotaById(String id);

    /** 报价单配件标准价格取得 */
    BigDecimal getAccStandardPrice(String material);

    /** 报价单明细选择框配件物料取得 */
    List<QuotaSelect> getQuotaMaterialDictList(QuotaSelect q);

    /** 报价单明细选择框服务物料取得 */
    List<QuotaSelect> getQuotaServiceDictList(QuotaSelect q);

    /** 报价单明细选择新sn取得 */
    List<QuotaSelect> getQuotaNewSnDictList(QuotaSelect q);

    /** 根据维修编号取得预报价单id */
    String getPreQuotaIdByRepairNo(String repairNo);

    /** 根据维修编号取得预报价单对应的sn号  */
    String getQuotaSnByRepairNo(String repairNo);
    
    /** 根据维修编号取得预报价单对应的sn号型号  */
    String getQuotaSnModelByRepairNo(String repairNo);

    /** 获取报价单开票客户信息 */
    CmCustomerInfo getCustomerInfo(String customerId);

    /** 取得报价单明细sn对应的生产日期 */
    Date getSnProductionDate(String snNo);
}