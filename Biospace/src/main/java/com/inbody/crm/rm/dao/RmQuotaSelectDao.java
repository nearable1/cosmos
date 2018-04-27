/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.domain.QuotaSelect;

/**
 * 报价单DAO接口
 * @author yangj
 * @version 2017-09-04
 */
@MyBatisDao
public interface RmQuotaSelectDao extends CrudDao<QuotaSelect> {
    /** 报价单明细选择框配件物料取得 */
    List<QuotaSelect> getQuotaMaterialDictList(QuotaSelect q);

    /** 报价单明细选择框服务物料取得 */
    List<QuotaSelect> getQuotaServiceDictList(QuotaSelect q);

    /** 报价单明细选择新sn取得 */
    List<QuotaSelect> getQuotaNewSnDictList(QuotaSelect q);
}