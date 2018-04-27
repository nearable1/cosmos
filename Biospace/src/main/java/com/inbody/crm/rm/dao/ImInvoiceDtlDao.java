/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.dao;

import java.math.BigDecimal;
import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.rm.entity.ImInvoiceDtl;

/**
 * 报价单发票DAO接口
 * @author yangj
 * @version 2017-10-08
 */
@MyBatisDao("quotaInvoiceDtlDao")
public interface ImInvoiceDtlDao extends CrudDao<ImInvoiceDtl> {

    /** 根据报价单id取得所有发票明细数据 */
    List<ImInvoiceDtl> getInvoiceDtlByQuotaId(String quotaId);

    /** 根据发票id取得当前发票明细数据 */
    List<ImInvoiceDtl> getInvoiceDtlByAppid(String appid);

    /** 根据发票id删除所有发票明细数据 */
    int deleteInvoiceDtlByAppid(String appid);

    /** 根据发票id取得所有发票明细行发票总金额 */
    BigDecimal getInvoiceTotalAmountByAppid(String appid);

    /** 根据报价单id取得报价单所有已审核发票明细总金额 */
    BigDecimal getQuotaApplyedInvoiceTotalAmount(String quotaId);
}