package com.xiaoniu.call.customer.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "customer_ext")
public class CustomerExt {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 客户编号
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * (注册)手机设备码
     */
    @Column(name = "device_code")
    private String deviceCode;

    /**
     * (注册)手机操作系统(0-Android、1-IOS)
     */
    @Column(name = "system_type")
    private Integer systemType;

    /**
     * (注册)手机操作系统版本号
     */
    @Column(name = "system_version")
    private String systemVersion;

    /**
     * (注册)APP版本号
     */
    @Column(name = "app_version")
    private String appVersion;

    /**
     * (注册)手机型号
     */
    @Column(name = "phone_model")
    private String phoneModel;

    /**
     * 用户注册渠道
     */
    @Column(name = "register_channel")
    private String registerChannel;

    /**
     * (注册)App下载渠道
     */
    @Column(name = "download_channel")
    private String downloadChannel;

    /**
     * (注册)ip地址
     */
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * 用户注册来源(1-Android、2-IOS、3-PC、4-H5、5-Wechat、6-QQ)
     */
    @Column(name = "source")
    private Integer source;

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
     * 获取客户编号
     *
     * @return customer_id - 客户编号
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * 设置客户编号
     *
     * @param customerId 客户编号
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取(注册)手机设备码
     *
     * @return device_code - (注册)手机设备码
     */
    public String getDeviceCode() {
        return deviceCode;
    }

    /**
     * 设置(注册)手机设备码
     *
     * @param deviceCode (注册)手机设备码
     */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    /**
     * 获取(注册)手机操作系统(0-Android、1-IOS)
     *
     * @return system_type - (注册)手机操作系统(0-Android、1-IOS)
     */
    public Integer getSystemType() {
        return systemType;
    }

    /**
     * 设置(注册)手机操作系统(0-Android、1-IOS)
     *
     * @param systemType (注册)手机操作系统(0-Android、1-IOS)
     */
    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    /**
     * 获取(注册)手机操作系统版本号
     *
     * @return system_version - (注册)手机操作系统版本号
     */
    public String getSystemVersion() {
        return systemVersion;
    }

    /**
     * 设置(注册)手机操作系统版本号
     *
     * @param systemVersion (注册)手机操作系统版本号
     */
    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    /**
     * 获取(注册)APP版本号
     *
     * @return app_version - (注册)APP版本号
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置(注册)APP版本号
     *
     * @param appVersion (注册)APP版本号
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取(注册)手机型号
     *
     * @return phone_model - (注册)手机型号
     */
    public String getPhoneModel() {
        return phoneModel;
    }

    /**
     * 设置(注册)手机型号
     *
     * @param phoneModel (注册)手机型号
     */
    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    /**
     * 获取用户注册渠道
     *
     * @return register_channel - 用户注册渠道
     */
    public String getRegisterChannel() {
        return registerChannel;
    }

    /**
     * 设置用户注册渠道
     *
     * @param registerChannel 用户注册渠道
     */
    public void setRegisterChannel(String registerChannel) {
        this.registerChannel = registerChannel;
    }

    /**
     * 获取(注册)App下载渠道
     *
     * @return download_channel - (注册)App下载渠道
     */
    public String getDownloadChannel() {
        return downloadChannel;
    }

    /**
     * 设置(注册)App下载渠道
     *
     * @param downloadChannel (注册)App下载渠道
     */
    public void setDownloadChannel(String downloadChannel) {
        this.downloadChannel = downloadChannel;
    }

    /**
     * 获取(注册)ip地址
     *
     * @return ip_address - (注册)ip地址
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 设置(注册)ip地址
     *
     * @param ipAddress (注册)ip地址
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 获取用户注册来源(1-Android、2-IOS、3-PC、4-H5、5-Wechat、6-QQ)
     *
     * @return source - 用户注册来源(1-Android、2-IOS、3-PC、4-H5、5-Wechat、6-QQ)
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置用户注册来源(1-Android、2-IOS、3-PC、4-H5、5-Wechat、6-QQ)
     *
     * @param source 用户注册来源(1-Android、2-IOS、3-PC、4-H5、5-Wechat、6-QQ)
     */
    public void setSource(Integer source) {
        this.source = source;
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