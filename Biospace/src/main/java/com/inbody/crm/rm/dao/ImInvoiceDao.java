/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.entity.ImInvoice;

/**
 * 报价单发票信息DAO接口
 * @author yangj
 * @version 2017-10-08
 */
@MyBatisDao("quotaInvoiceDao")
public interface ImInvoiceDao extends CrudDao<ImInvoice> {

    /** 根据id取得发票信息 */
    ImInvoice getInvoiceById(String id);

    /** 根据报价单编号取得发票信息 */
    List<ImInvoice> getInvoiceByQuotaNo(String quotaNo);

    /** 根据报价单编号取得创建时间最晚的发票信息 */
    ImInvoice getLastInvoiceByQuotaNo(String quotaNo);

    /** 根据报价单id取得未完成申请发票 */
    ImInvoice getUnfinishedApplyInvoice(String quotaId);
}