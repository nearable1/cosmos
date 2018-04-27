/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.entity;

import java.util.Date;
import java.util.List;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 邮件提醒Entity
 * 
 * @author yangj
 * @version 2017-10-31
 */
public class MailRemind extends DataEntity<MailRemind> {

    private static final long serialVersionUID = 8845076151061180446L;

    /** 到期日 */
    private Date dueDate;

    /** 直接负责人 */
    private String chargePersonId;

    /** 直接负责人email */
    private String chargeEmail;

    /** 组长 */
    private String groupLeaderId;

    /** 组长email */
    private String groupLeaderEmail;

    /** 提醒关键字 */
    private String keyword;

    /** 提醒url key */
    private String urlKey;
    
    /** 访问url */
    private String url;

    /** 过期天数 */
    private Integer dueDays;

    /** 开始提醒天数 */
    private Integer startDays;

    /** 提醒周期/天 */
    private Integer cycleDays;

    /** 被提醒人角色 */
    private List<String> roles;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getChargePersonId() {
        return chargePersonId;
    }

    public void setChargePersonId(String chargePersonId) {
        this.chargePersonId = chargePersonId;
    }

    public String getChargeEmail() {
        return chargeEmail;
    }

    public void setChargeEmail(String chargeEmail) {
        this.chargeEmail = chargeEmail;
    }

    public String getGroupLeaderId() {
        return groupLeaderId;
    }

    public void setGroupLeaderId(String groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
    }

    public String getGroupLeaderEmail() {
        return groupLeaderEmail;
    }

    public void setGroupLeaderEmail(String groupLeaderEmail) {
        this.groupLeaderEmail = groupLeaderEmail;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(Integer dueDays) {
        this.dueDays = dueDays;
    }

    public Integer getStartDays() {
        return startDays;
    }

    public void setStartDays(Integer startDays) {
        this.startDays = startDays;
    }

    public Integer getCycleDays() {
        return cycleDays;
    }

    public void setCycleDays(Integer cycleDays) {
        this.cycleDays = cycleDays;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}