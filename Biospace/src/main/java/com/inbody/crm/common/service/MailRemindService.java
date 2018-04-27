package com.inbody.crm.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.constants.TemplatePath;
import com.inbody.crm.common.entity.MailRemind;
import com.inbody.crm.common.persistence.MailRemindDao;
import com.inbody.crm.common.utils.ListUtils;
import com.inbody.crm.common.utils.SendMail;
import com.inbody.crm.common.utils.StringUtils;

@Service
@Transactional(readOnly = true)
public class MailRemindService extends BaseService {

    /** 访问context */
    private String ctx = Global.getHost() + Global.getAdminPath();

    @Autowired
    private MailRemindDao mailRemindDao;

    /**
     * 协议到期邮件提醒
     * 
     * @param fileId
     *            文件ID（附件表记录id）
     * @return 附件信息
     */
    @Transactional(readOnly = true)
    public void agreementExpireRemind(MailRemind qRemind) {

        // 取得符合条件的即将过期的协议
        List<MailRemind> mailRemindList = mailRemindDao.getExpiringAgreement(qRemind);

        // 发送对象取得
        if (ListUtils.size(mailRemindList) == 0) {
            return;
        }

        // 角色收件人取得
        List<String> rolesTo = getRecipientByRoles(qRemind.getRoles());

        // 邮件提醒
        for (MailRemind remind : mailRemindList) {
            // url设定
            remind.setUrl(ctx + "/cm/cm001/newAndEdit?customerId=" + remind.getUrlKey());
            // 发送邮件
            sendMail("协议到期-" + remind.getKeyword(),
                    TemplatePath.AGREEMENT_EXPIRE_REMIND.value(), remind,
                    remind.getChargeEmail(), remind.getGroupLeaderEmail(), rolesTo);
        }
    }

    /**
     * 合同收款滞纳邮件提醒
     * 
     * @param fileId
     *            文件ID（附件表记录id）
     * @return 附件信息
     */
    @Transactional(readOnly = true)
    public void contractLatePaymentRemind(MailRemind qRemind) {

        // 取得合同收款滞纳提醒信息
        List<MailRemind> mailRemindList = mailRemindDao.getLatePaymentContract(qRemind);

        // 发送对象取得
        if (ListUtils.size(mailRemindList) == 0) {
            return;
        }

        // 角色收件人取得
        List<String> rolesTo = getRecipientByRoles(qRemind.getRoles());

        // 邮件提醒
        for (MailRemind remind : mailRemindList) {
            // url设定
            remind.setUrl(ctx + "/sd/soOrder/form?id=" + remind.getUrlKey()
                    + "&dataType=order");
            // 发送邮件
            sendMail("合同收款滞纳-合同" + remind.getKeyword(),
                    TemplatePath.CONTRACT_LATE_PAYMENT_REMIND.value(), remind,
                    remind.getChargeEmail(), remind.getGroupLeaderEmail(), rolesTo);
        }
    }

    /**
     * 报价单收款滞纳邮件提醒
     * 
     * @param fileId
     *            文件ID（附件表记录id）
     * @return 附件信息
     */
    @Transactional(readOnly = true)
    public void quotaLatePaymentRemind(MailRemind qRemind) {

        // 报价单收款滞纳提醒信息取得
        List<MailRemind> mailRemindList = mailRemindDao.getLatePaymentQuota(qRemind);

        // 发送对象取得
        if (ListUtils.size(mailRemindList) == 0) {
            return;
        }

        // 角色收件人取得
        List<String> rolesTo = getRecipientByRoles(qRemind.getRoles());

        // 邮件提醒
        for (MailRemind remind : mailRemindList) {
            // url设定
            remind.setUrl(ctx + "/rm/quotation/final/form?id=" + remind.getUrlKey());
            // 发送邮件
            sendMail("报价单收款滞纳-维修记录" + remind.getKeyword(),
                    TemplatePath.QUOTA_LATE_PAYMENT_REMIND.value(), remind,
                    remind.getChargeEmail(), remind.getGroupLeaderEmail(), rolesTo);
        }
    }

    /**
     * 合同安装信息未返回邮件提醒
     * 
     * @param fileId
     *            文件ID（附件表记录id）
     * @return 附件信息
     */
    @Transactional(readOnly = true)
    public void installNoFeedbackRemind(MailRemind qRemind) {

        // 合同安装信息未返回提醒信息取得
        List<MailRemind> mailRemindList = mailRemindDao
                .getInstallNoFeedbackRemind(qRemind);

        // 发送对象取得
        if (ListUtils.size(mailRemindList) == 0) {
            return;
        }

        // 角色收件人取得
        List<String> rolesTo = getRecipientByRoles(qRemind.getRoles());

        // 邮件提醒
        for (MailRemind remind : mailRemindList) {
            // url设定
            remind.setUrl(
                    ctx + "/sd/sm/sm011/list?orderNo=" + remind.getUrlKey());
            // 发送邮件
            sendMail("安装信息未返回-合同" + remind.getKeyword(),
                    TemplatePath.INSTALL_NO_FEEDBACK_REMIND.value(), remind,
                    remind.getChargeEmail(), remind.getGroupLeaderEmail(), rolesTo);
        }
    }

    /**
     * 样机借用到期提醒
     * 
     * @param fileId
     *            文件ID（附件表记录id）
     * @return 附件信息
     */
    @Transactional(readOnly = true)
    public void borrowExpiresRemind(MailRemind qRemind) {

        // 取样机借用到期提醒信息取得
        List<MailRemind> mailRemindList = mailRemindDao.getBorrowExpiresRemind(qRemind);

        // 发送对象取得
        if (ListUtils.size(mailRemindList) == 0) {
            return;
        }

        // 角色收件人取得
        List<String> rolesTo = getRecipientByRoles(qRemind.getRoles());

        // 邮件提醒
        for (MailRemind remind : mailRemindList) {
            // url设定
            remind.setUrl(ctx + "/mm/storageManagement/unrestoredList");
            // 发送邮件
            sendMail("样机借用到期-" + remind.getKeyword(),
                    TemplatePath.BORROW_EXPIRES_REMIND.value(), remind,
                    remind.getChargeEmail(), remind.getGroupLeaderEmail(), rolesTo);
        }
    }

    /**
     * 发送模板邮件
     * 
     * @param subject
     *            邮件标题
     * @param templatePath
     *            邮件模片名称
     * @param model
     *            模板数据对象
     * @param chargeTo
     *            邮件发送对象（业务员/负责人）
     * @param groupLdTo
     *            邮件发送对象（组长）
     * @param rolesTo
     *            邮件发送对象（角色）
     */
    public void sendMail(String subject, String templatePath, Object model,
            String chargeTo, String groupLdTo, List<String> rolesTo) {

        // 邮件发送对象创建
        List<String> mailTo = new ArrayList<String>();
        // 业务员/负责人
        if (!StringUtils.isBlank(chargeTo)) {
            mailTo.add(chargeTo);
        }
        // 组长
        if (!StringUtils.isBlank(groupLdTo)) {
            mailTo.add(groupLdTo);
        }
        // 角色对象邮件
        mailTo.addAll(rolesTo);

        // 收件人一览为空时
        if (ListUtils.size(mailTo) == 0) {
            return;
        }

        // 发送邮件
//        SendMailUtil.sendFtlMail(subject, templatePath, model,
//                mailTo.toArray(new String[]{}));
        new SendMail(subject, templatePath, model,
                mailTo.toArray(new String[]{})).run();
    }

    /**
     * 根据角色取得对应的收件人地址
     * 
     * @param roles
     *            角色list
     * @return 收件人地址list
     */
    public List<String> getRecipientByRoles(List<String> roles) {
        Map<String, Object> qMap = new HashMap<String, Object>();
        qMap.put("roles", roles);
        // 角色收件人取得
        List<String> rolesTo = mailRemindDao.getRecipientByRoles(qMap);
        return rolesTo;
    }
}
