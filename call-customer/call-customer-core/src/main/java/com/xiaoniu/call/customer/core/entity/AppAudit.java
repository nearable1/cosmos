package com.xiaoniu.call.customer.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "app_audit")
public class AppAudit {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 过审项
     */
    @Column(name = "audit_item")
    private String auditItem;

    /**
     * 渠道
     */
    @Column(name = "channel")
    private String channel;

    /**
     * 版本
     */
    @Column(name = "version")
    private String version;

    /**
     * 状态（1=启用，0=关闭）
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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
     * 状态（1=启用，0=关闭）
     */
    @Column(name = "app_name")
    private Integer appName;

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
     * 获取过审项
     *
     * @return audit_item - 过审项
     */
    public String getAuditItem() {
        return auditItem;
    }

    /**
     * 设置过审项
     *
     * @param auditItem 过审项
     */
    public void setAuditItem(String auditItem) {
        this.auditItem = auditItem;
    }

    /**
     * 获取渠道
     *
     * @return channel - 渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置渠道
     *
     * @param channel 渠道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取版本
     *
     * @return version - 版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置版本
     *
     * @param version 版本
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取状态（1=启用，0=关闭）
     *
     * @return state - 状态（1=启用，0=关闭）
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态（1=启用，0=关闭）
     *
     * @param state 状态（1=启用，0=关闭）
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getAppName() {
        return appName;
    }

    public void setAppName(Integer appName) {
        this.appName = appName;
    }
}