package com.xiaoniu.call.customer.core.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "customer_manager")
public class CustomerManager {
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
     * 等级(1-管理员)
     */
    @Column(name = "level")
    private Integer level;

    /**
     * 状态（1=启用，0=关闭）
     */
    @Column(name = "state")
    private Integer state;

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
     * 获取等级(1-管理员)
     *
     * @return level - 等级(1-管理员)
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置等级(1-管理员)
     *
     * @param level 等级(1-管理员)
     */
    public void setLevel(Integer level) {
        this.level = level;
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