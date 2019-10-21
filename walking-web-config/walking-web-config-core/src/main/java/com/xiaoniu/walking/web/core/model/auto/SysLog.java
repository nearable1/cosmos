package com.xiaoniu.walking.web.core.model.auto;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_log")
public class SysLog {
    /**
     * 主键编号
     */
    @Id
    private Long id;

    /**
     * 系统登录操作账号
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 操作模块
     */
    @Column(name = "action_module")
    private String actionModule;

    /**
     * 操作url
     */
    @Column(name = "action_url")
    private String actionUrl;

    /**
     * 操作类型
     */
    @Column(name = "action_type")
    private String actionType;

    /**
     * 操作人ip地址
     */
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * 操作时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作参数
     */
    @Column(name = "action_param")
    private String actionParam;

    /**
     * 获取主键编号
     *
     * @return id - 主键编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键编号
     *
     * @param id 主键编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取系统登录操作账号
     *
     * @return user_id - 系统登录操作账号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置系统登录操作账号
     *
     * @param userId 系统登录操作账号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取操作模块
     *
     * @return action_module - 操作模块
     */
    public String getActionModule() {
        return actionModule;
    }

    /**
     * 设置操作模块
     *
     * @param actionModule 操作模块
     */
    public void setActionModule(String actionModule) {
        this.actionModule = actionModule;
    }

    /**
     * 获取操作url
     *
     * @return action_url - 操作url
     */
    public String getActionUrl() {
        return actionUrl;
    }

    /**
     * 设置操作url
     *
     * @param actionUrl 操作url
     */
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    /**
     * 获取操作类型
     *
     * @return action_type - 操作类型
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * 设置操作类型
     *
     * @param actionType 操作类型
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * 获取操作人ip地址
     *
     * @return ip_address - 操作人ip地址
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 设置操作人ip地址
     *
     * @param ipAddress 操作人ip地址
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 获取操作时间
     *
     * @return create_time - 操作时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置操作时间
     *
     * @param createTime 操作时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取操作参数
     *
     * @return action_param - 操作参数
     */
    public String getActionParam() {
        return actionParam;
    }

    /**
     * 设置操作参数
     *
     * @param actionParam 操作参数
     */
    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }
}