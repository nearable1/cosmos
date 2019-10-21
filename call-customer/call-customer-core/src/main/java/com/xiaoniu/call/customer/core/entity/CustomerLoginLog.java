package com.xiaoniu.call.customer.core.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class CustomerLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键编号
     */
    private Long id;

    /**
     * 客户编号
     */
    private Long customerId;

    /**
     * 请求流水号
     */
    private String requestId;

    /**
     * 用户注册来源(1-Android、2-iOS、3-PC、4-H5、5-Wechat、6-QQ)
     */
    private Integer source;

    /**
     * 手机系统类型(1-Android，2-IOS)
     */
    private Integer systenType;

    /**
     * 手机操作系统版本号
     */
    private String systemVersion;

    /**
     * 手机型号
     */
    private String phoneModel;

    /**
     * 应用下载渠道
     */
    private String appDownloadChannel;

    /**
     * 登陆ip地址
     */
    private String loginIpAddress;

    /**
     * 手机设备码
     */
    private String deviceCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
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
     * 获取请求流水号
     *
     * @return request_id - 请求流水号
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * 设置请求流水号
     *
     * @param requestId 请求流水号
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * 获取用户注册来源(1-Android、2-iOS、3-PC、4-H5、5-Wechat、6-QQ)
     *
     * @return source - 用户注册来源(1-Android、2-iOS、3-PC、4-H5、5-Wechat、6-QQ)
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置用户注册来源(1-Android、2-iOS、3-PC、4-H5、5-Wechat、6-QQ)
     *
     * @param source 用户注册来源(1-Android、2-iOS、3-PC、4-H5、5-Wechat、6-QQ)
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
     * 获取手机系统类型(1-Android，2-IOS)
     *
     * @return systen_type - 手机系统类型(1-Android，2-IOS)
     */
    public Integer getSystenType() {
        return systenType;
    }

    /**
     * 设置手机系统类型(1-Android，2-IOS)
     *
     * @param systenType 手机系统类型(1-Android，2-IOS)
     */
    public void setSystenType(Integer systenType) {
        this.systenType = systenType;
    }

    /**
     * 获取手机操作系统版本号
     *
     * @return system_version - 手机操作系统版本号
     */
    public String getSystemVersion() {
        return systemVersion;
    }

    /**
     * 设置手机操作系统版本号
     *
     * @param systemVersion 手机操作系统版本号
     */
    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    /**
     * 获取手机型号
     *
     * @return phone_model - 手机型号
     */
    public String getPhoneModel() {
        return phoneModel;
    }

    /**
     * 设置手机型号
     *
     * @param phoneModel 手机型号
     */
    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    /**
     * 获取应用下载渠道
     *
     * @return app_download_channel - 应用下载渠道
     */
    public String getAppDownloadChannel() {
        return appDownloadChannel;
    }

    /**
     * 设置应用下载渠道
     *
     * @param appDownloadChannel 应用下载渠道
     */
    public void setAppDownloadChannel(String appDownloadChannel) {
        this.appDownloadChannel = appDownloadChannel;
    }

    /**
     * 获取登陆ip地址
     *
     * @return login_ip_address - 登陆ip地址
     */
    public String getLoginIpAddress() {
        return loginIpAddress;
    }

    /**
     * 设置登陆ip地址
     *
     * @param loginIpAddress 登陆ip地址
     */
    public void setLoginIpAddress(String loginIpAddress) {
        this.loginIpAddress = loginIpAddress;
    }

    /**
     * 获取手机设备码
     *
     * @return device_code - 手机设备码
     */
    public String getDeviceCode() {
        return deviceCode;
    }

    /**
     * 设置手机设备码
     *
     * @param deviceCode 手机设备码
     */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
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