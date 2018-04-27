/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import java.math.BigDecimal;
import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.entity.RmQuotationDtl;

/**
 * 报价单明细DAO接口
 * @author yangj
 * @version 2017-09-04
 */
@MyBatisDao
public interface RmQuotationDtlDao extends CrudDao<RmQuotationDtl> {
    
    /** 根据报价单id取得报价单明细信息 */
    List<RmQuotationDtl> getQuotaDtlListById(String id);
    
    /** 取得报价单明细总金额 */
    BigDecimal getQuotaDtlTotalAmount(String quotaId);
}