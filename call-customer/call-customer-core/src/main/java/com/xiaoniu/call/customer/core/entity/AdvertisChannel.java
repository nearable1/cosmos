package com.xiaoniu.call.customer.core.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "advertis_channel")
public class AdvertisChannel {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 渠道编号
     */
    @Column(name = "channel_number")
    private String channelNumber;

    /**
     * 渠道名称
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 状态(0-关闭、1-启动)
     */
    @Column(name = "status")
    private Byte status;

    /**
     * 投放开始时间
     */
    @Column(name = "delivery_start_time")
    private Date deliveryStartTime;

    /**
     * 投放结束时间
     */
    @Column(name = "delivery_end_time")
    private Date deliveryEndTime;

    /**
     * 点击量
     */
    @Column(name = "click_volume")
    private Integer clickVolume;

    /**
     * 曝光量
     */
    @Column(name = "exposure")
    private Integer exposure;

    /**
     * 收益
     */
    @Column(name = "income")
    private BigDecimal income;

    /**
     * 操作人
     */
    @Column(name = "operator")
    private String operator;

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
     * 获取渠道编号
     *
     * @return channel_number - 渠道编号
     */
    public String getChannelNumber() {
        return channelNumber;
    }

    /**
     * 设置渠道编号
     *
     * @param channelNumber 渠道编号
     */
    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    /**
     * 获取渠道名称
     *
     * @return channel_name - 渠道名称
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置渠道名称
     *
     * @param channelName 渠道名称
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 获取状态(0-关闭、1-启动)
     *
     * @return status - 状态(0-关闭、1-启动)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态(0-关闭、1-启动)
     *
     * @param status 状态(0-关闭、1-启动)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取投放开始时间
     *
     * @return delivery_start_time - 投放开始时间
     */
    public Date getDeliveryStartTime() {
        return deliveryStartTime;
    }

    /**
     * 设置投放开始时间
     *
     * @param deliveryStartTime 投放开始时间
     */
    public void setDeliveryStartTime(Date deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    /**
     * 获取投放结束时间
     *
     * @return delivery_end_time - 投放结束时间
     */
    public Date getDeliveryEndTime() {
        return deliveryEndTime;
    }

    /**
     * 设置投放结束时间
     *
     * @param deliveryEndTime 投放结束时间
     */
    public void setDeliveryEndTime(Date deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
    }

    /**
     * 获取点击量
     *
     * @return click_volume - 点击量
     */
    public Integer getClickVolume() {
        return clickVolume;
    }

    /**
     * 设置点击量
     *
     * @param clickVolume 点击量
     */
    public void setClickVolume(Integer clickVolume) {
        this.clickVolume = clickVolume;
    }

    /**
     * 获取曝光量
     *
     * @return exposure - 曝光量
     */
    public Integer getExposure() {
        return exposure;
    }

    /**
     * 设置曝光量
     *
     * @param exposure 曝光量
     */
    public void setExposure(Integer exposure) {
        this.exposure = exposure;
    }

    /**
     * 获取收益
     *
     * @return income - 收益
     */
    public BigDecimal getIncome() {
        return income;
    }

    /**
     * 设置收益
     *
     * @param income 收益
     */
    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
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
}