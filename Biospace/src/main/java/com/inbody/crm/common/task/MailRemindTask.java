package com.inbody.crm.common.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.inbody.crm.common.entity.MailRemind;
import com.inbody.crm.common.service.MailRemindService;

/**
 * 邮件提醒自动运行任务
 * 
 * @author yangj
 * @version 2017-11-03
 */
public class MailRemindTask {

    private static final Logger logger = LoggerFactory.getLogger(MailRemindTask.class);

    @Autowired
    private MailRemindService mailRemindService;

    /**
     * 协议到期邮件提醒自动运行任务执行方法
     */
    public void agrtExpireRemind() {
        logger.info("协议到期邮件提醒任务开始。");

        // 被提醒人角色
        List<String> roles = Lists.newArrayList();
        // 销售部长
//        roles.add("crm-04");
        // 事业部部长
        roles.add("crm_01");
        // 运营组员
//      roles.add("crm-16");
        // 销售管理组组长
        roles.add("crm-12");
        // 运营组长
//        roles.add("crm-15");
        // 销售管理组组员
        roles.add("crm-13");
        // 管理部长
//        roles.add("crm-14");
        roles.add("crm-11");
        // 总经理
        roles.add("admin");

        MailRemind qRemind = new MailRemind();
        // 提醒开始天数：到期日前30天
        qRemind.setStartDays(30);
        // 提醒周期7天
        qRemind.setCycleDays(7);
        // 被提醒人角色
        qRemind.setRoles(roles);

        try {
            mailRemindService.agreementExpireRemind(qRemind);
        } catch (Exception e) {
            logger.error("协议到期邮件提醒任务运行异常", e);
            e.printStackTrace();
        }

        logger.info("协议到期邮件提醒任务结束。");
    }

    /**
     * 合同收款滞纳邮件提醒自动运行任务执行方法
     */
    public void contrOvrdPayRemind() {
        logger.info("合同收款滞纳邮件提醒任务开始。");

        // 收件人角色
        List<String> roles = Lists.newArrayList();
        // 财务组长
//        roles.add("crm-17");
        // 财务组员
//        roles.add("crm-18");
        roles.add("crm-15");
        // 管理部长
//        roles.add("crm-14");
        roles.add("crm-11");

        // 提醒配置
        MailRemind qRemind = new MailRemind();
        // 提醒开始天数：过期1天
        qRemind.setStartDays(-1);
        // 提醒周期7天
        qRemind.setCycleDays(7);
        // 被提醒人角色
        qRemind.setRoles(roles);

        try {
            mailRemindService.contractLatePaymentRemind(qRemind);
        } catch (Exception e) {
            logger.error("合同收款滞纳邮件提醒任务运行异常", e);
            e.printStackTrace();
        }

        logger.info("合同收款滞纳邮件提醒任务结束。");
    }

    /**
     * 报价单收款滞纳邮件提醒自动运行任务执行方法
     */
    public void quotaOvrdPayRemind() {
        logger.info("报价单收款滞纳邮件提醒任务开始。");

        // 收件人角色
        List<String> roles = Lists.newArrayList();
//        // 工程部部长
//        roles.add("crm-07");
        // CS部部长
        roles.add("crm-05");
        // 财务组长
//        roles.add("crm-17");
        // 财务组员
//        roles.add("crm-18");
        roles.add("crm-15");
        // 管理部长
//        roles.add("crm-14");
        roles.add("crm-11");

        // 提醒配置
        MailRemind qRemind = new MailRemind();
        // 提醒开始天数：过期30天
        qRemind.setStartDays(-30);
        // 提醒周期7天
        qRemind.setCycleDays(7);
        // 被提醒人角色
        qRemind.setRoles(roles);

        try {
            mailRemindService.quotaLatePaymentRemind(qRemind);
        } catch (Exception e) {
            logger.error("报价单收款滞纳邮件提醒任务运行异常", e);
            e.printStackTrace();
        }

        logger.info("报价单收款滞纳邮件提醒任务结束。");
    }

    /**
     * 安装信息未返回邮件提醒自动运行任务执行方法
     */
    public void installNoFeedbackRemind() {
        logger.info("安装信息未返回邮件提醒任务开始。");

        // 收件人角色
        List<String> roles = Lists.newArrayList();
        // 物流组员
//        roles.add("crm-19");
        roles.add("crm_29");
//        // 运营组员
//        roles.add("crm-15");
        // 生产部部长
        roles.add("crm_25");
        // 销售管理组组长
        roles.add("crm-12");

        // 提醒配置
        MailRemind qRemind = new MailRemind();
        // 提醒开始天数：过期1天
        qRemind.setStartDays(-1);
        // 提醒周期7天
        qRemind.setCycleDays(7);
        // 被提醒人角色
        qRemind.setRoles(roles);

        try {
            mailRemindService.installNoFeedbackRemind(qRemind);
        } catch (Exception e) {
            logger.error("安装信息未返回邮件提醒任务运行异常", e);
            e.printStackTrace();
        }

        logger.info("安装信息未返回邮件提醒任务结束。");
    }

    /**
     * 样机借用到期邮件提醒自动运行任务执行方法
     */
    public void borrowExpiresRemind() {
        logger.info("样机借用到期邮件提醒任务开始。");

        // 收件人角色
        List<String> roles = Lists.newArrayList();
        // 物流组员
//      roles.add("crm-19");
        roles.add("crm_29");
//      // 运营组员
//      roles.add("crm-15");
        // 生产部部长
        roles.add("crm_25");
        // 销售管理组组长
        roles.add("crm-12");

        // 提醒配置
        MailRemind qRemind = new MailRemind();
        // 提醒开始天数：过期1天
        qRemind.setStartDays(-1);
        // 提醒周期7天
        qRemind.setCycleDays(7);
        // 被提醒人角色
        qRemind.setRoles(roles);

        try {
            mailRemindService.borrowExpiresRemind(qRemind);
        } catch (Exception e) {
            logger.error("样机借用到期邮件提醒任务运行异常", e);
            e.printStackTrace();
        }

        logger.info("样机借用到期邮件提醒任务结束。");
    }
}