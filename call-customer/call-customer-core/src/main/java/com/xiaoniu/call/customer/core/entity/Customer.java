package com.xiaoniu.call.customer.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "customer")
public class Customer {
    /**
     * 客户编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 账号类型(1-QQ、2-微信)
     */
    @Column(name = "account_type")
    private Integer accountType;

    /**
     * 昵称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 签名
     */
    @Column(name = "signature")
    private String signature;

    /**
     * 头像地址
     */
    @Column(name = "avatar_address")
    private String avatarAddress;

    /**
     * 用户状态（0=禁用，1=正常）
     */
    @Column(name = "cust_state")
    private Boolean custState;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最近登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 获取客户编号
     *
     * @return id - 客户编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户编号
     *
     * @param id 客户编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取账号类型(1-QQ、2-微信)
     *
     * @return account_type - 账号类型(1-QQ、2-微信)
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * 设置账号类型(1-QQ、2-微信)
     *
     * @param accountType 账号类型(1-QQ、2-微信)
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取签名
     *
     * @return signature - 签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 设置签名
     *
     * @param signature 签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 获取头像地址
     *
     * @return avatar_address - 头像地址
     */
    public String getAvatarAddress() {
        return avatarAddress;
    }

    /**
     * 设置头像地址
     *
     * @param avatarAddress 头像地址
     */
    public void setAvatarAddress(String avatarAddress) {
        this.avatarAddress = avatarAddress;
    }

    /**
     * 获取用户状态（0=禁用，1=正常）
     *
     * @return cust_state - 用户状态（0=禁用，1=正常）
     */
    public Boolean getCustState() {
        return custState;
    }

    /**
     * 设置用户状态（0=禁用，1=正常）
     *
     * @param custState 用户状态（0=禁用，1=正常）
     */
    public void setCustState(Boolean custState) {
        this.custState = custState;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * 获取最近登录时间
     *
     * @return last_login_time - 最近登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最近登录时间
     *
     * @param lastLoginTime 最近登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}