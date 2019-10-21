package com.xiaoniu.walking.web.core.model.auto;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 主键编号
     */
    @Id
    private Integer id;

    /**
     * 系统登录账号
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 绑定手机号
     */
    @Column(name = "phone_num")
    private String phoneNum;

    /**
     * 头像地址
     */
    @Column(name = "head_image_url")
    private String headImageUrl;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 性别：0-女，1-男
     */
    private Integer sex;

    /**
     * 账户状态：1-正常，2-冻结
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 获取主键编号
     *
     * @return id - 主键编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键编号
     *
     * @param id 主键编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取系统登录账号
     *
     * @return user_id - 系统登录账号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置系统登录账号
     *
     * @param userId 系统登录账号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取绑定手机号
     *
     * @return phone_num - 绑定手机号
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * 设置绑定手机号
     *
     * @param phoneNum 绑定手机号
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * 获取头像地址
     *
     * @return head_image_url - 头像地址
     */
    public String getHeadImageUrl() {
        return headImageUrl;
    }

    /**
     * 设置头像地址
     *
     * @param headImageUrl 头像地址
     */
    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
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
     * 获取登录密码
     *
     * @return password - 登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录密码
     *
     * @param password 登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取性别：0-女，1-男
     *
     * @return sex - 性别：0-女，1-男
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别：0-女，1-男
     *
     * @param sex 性别：0-女，1-男
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取账户状态：1-正常，2-冻结
     *
     * @return status - 账户状态：1-正常，2-冻结
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置账户状态：1-正常，2-冻结
     *
     * @param status 账户状态：1-正常，2-冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}