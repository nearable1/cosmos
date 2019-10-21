package com.xiaoniu.walking.web.core.model.ext;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

public class UserCertInfoExt {
    /**
     * 用户编号
     */
    private String userId;

    /**
     * 注册手机号
     */
    private String phoneNum;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String certCode;

    /**
     * 公司全称
     */
    private String companyName;

    /**
     * 公司类型
     */
    private String companyType;

    /**
     * 身份证正面URL
     */
    private String frontCardUrl;

    /**
     * 身份证反面URL
     */
    private String backCardUrl;

    /**
     * 手持身份证URL
     */
    private String handCardUrl;

    /**
     * 用户状态：1-待审核，2-认证通过，3-认证未通过
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 审核人
     */
    private String auditMan;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核结果备注
     */
    private String remark;

    /**
     * 金币
     */
    private BigDecimal gold;

    /**
     * 积分
     */
    private BigDecimal integral;

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    /**
     * 获取用户编号
     *
     * @return user_id - 用户编号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户编号
     *
     * @param userId 用户编号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取注册手机号
     *
     * @return phone_num - 注册手机号
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 设置注册手机号
     *
     * @param phoneNum 注册手机号
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取身份证号
     *
     * @return cert_code - 身份证号
     */
    public String getCertCode() {
        return certCode;
    }

    /**
     * 设置身份证号
     *
     * @param certCode 身份证号
     */
    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    /**
     * 获取公司全称
     *
     * @return company_name - 公司全称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置公司全称
     *
     * @param companyName 公司全称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取公司类型
     *
     * @return company_type - 公司类型
     */
    public String getCompanyType() {
        return companyType;
    }

    /**
     * 设置公司类型
     *
     * @param companyType 公司类型
     */
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    /**
     * 获取身份证正面URL
     *
     * @return front_card_url - 身份证正面URL
     */
    public String getFrontCardUrl() {
        return frontCardUrl;
    }

    /**
     * 设置身份证正面URL
     *
     * @param frontCardUrl 身份证正面URL
     */
    public void setFrontCardUrl(String frontCardUrl) {
        this.frontCardUrl = frontCardUrl;
    }

    /**
     * 获取身份证反面URL
     *
     * @return back_card_url - 身份证反面URL
     */
    public String getBackCardUrl() {
        return backCardUrl;
    }

    /**
     * 设置身份证反面URL
     *
     * @param backCardUrl 身份证反面URL
     */
    public void setBackCardUrl(String backCardUrl) {
        this.backCardUrl = backCardUrl;
    }

    /**
     * 获取手持身份证URL
     *
     * @return hand_card_url - 手持身份证URL
     */
    public String getHandCardUrl() {
        return handCardUrl;
    }

    /**
     * 设置手持身份证URL
     *
     * @param handCardUrl 手持身份证URL
     */
    public void setHandCardUrl(String handCardUrl) {
        this.handCardUrl = handCardUrl;
    }

    /**
     * 获取用户状态：1-待审核，2-认证通过，3-认证未通过
     *
     * @return state - 用户状态：1-待审核，2-认证通过，3-认证未通过
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置用户状态：1-待审核，2-认证通过，3-认证未通过
     *
     * @param state 用户状态：1-待审核，2-认证通过，3-认证未通过
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取审核人
     *
     * @return audit_man - 审核人
     */
    public String getAuditMan() {
        return auditMan;
    }

    /**
     * 设置审核人
     *
     * @param auditMan 审核人
     */
    public void setAuditMan(String auditMan) {
        this.auditMan = auditMan;
    }

    /**
     * 获取审核时间
     *
     * @return audit_time - 审核时间
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 设置审核时间
     *
     * @param auditTime 审核时间
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 获取审核结果备注
     *
     * @return remark - 审核结果备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置审核结果备注
     *
     * @param remark 审核结果备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}