/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.persistence;

import java.util.List;
import java.util.Map;

import com.inbody.crm.common.entity.MailRemind;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;

/**
 * 履历DAO接口
 * 
 * @author yangj
 * @version 2017-10-24
 */
@MyBatisDao
public interface MailRemindDao {

    /** 取得即将过期协议提醒信息 */
    List<MailRemind> getExpiringAgreement(MailRemind mailRemind);

    /** 取得合同收款滞纳提醒信息 */
    List<MailRemind> getLatePaymentContract(MailRemind mailRemind);

    /** 取得报价单收款滞纳提醒信息 */
    List<MailRemind> getLatePaymentQuota(MailRemind mailRemind);

    /** 取得安装信息未返回提醒信息 */
    List<MailRemind> getInstallNoFeedbackRemind(MailRemind mailRemind);

    /** 取得样机借用到期提醒信息 */
    List<MailRemind> getBorrowExpiresRemind(MailRemind mailRemind);

    /** 根据角色取得收件人一览 */
    List<String> getRecipientByRoles(Map<String, Object> qParam);
}