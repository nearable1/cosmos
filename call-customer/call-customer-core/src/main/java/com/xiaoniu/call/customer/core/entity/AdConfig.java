package com.xiaoniu.call.customer.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ad_config")
public class AdConfig {
    /**
     * 主键编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * app名称，1=最来电,2=动态壁纸，3=爱来电，4=铃声多多
     */
    @Column(name = "app_name")
    private Integer appName;

    /**
     * 广告类型，1=穿山甲，2=百度联盟，3=优量汇
     */
    @Column(name = "ad_type")
    private Integer adType;

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
     * 获取app名称，1=最来电,2=动态壁纸，3=爱来电，4=铃声多多
     *
     * @return app_name - app名称，1=最来电,2=动态壁纸，3=爱来电，4=铃声多多
     */
    public Integer getAppName() {
        return appName;
    }

    /**
     * 设置app名称，1=最来电,2=动态壁纸，3=爱来电，4=铃声多多
     *
     * @param appName app名称，1=最来电,2=动态壁纸，3=爱来电，4=铃声多多
     */
    public void setAppName(Integer appName) {
        this.appName = appName;
    }

    /**
     * 获取广告类型，1=穿山甲，2=百度联盟，3=优量汇
     *
     * @return ad_type - 广告类型，1=穿山甲，2=百度联盟，3=优量汇
     */
    public Integer getAdType() {
        return adType;
    }

    /**
     * 设置广告类型，1=穿山甲，2=百度联盟，3=优量汇
     *
     * @param adType 广告类型，1=穿山甲，2=百度联盟，3=优量汇
     */
    public void setAdType(Integer adType) {
        this.adType = adType;
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
}