package com.xiaoniu.walking.web.core.model.auto;

import javax.persistence.*;

@Table(name = "ad_management_market")
public class AdManagementMarket {


    /**
     * 广告配置编号
     */
    @Column(name = "management_id")
    private Integer managementId;

    /**
     * 市场渠道编号
     */
    private String market;


    /**
     * 获取广告配置编号
     *
     * @return management_id - 广告配置编号
     */
    public Integer getManagementId() {
        return managementId;
    }

    /**
     * 设置广告配置编号
     *
     * @param managementId 广告配置编号
     */
    public void setManagementId(Integer managementId) {
        this.managementId = managementId;
    }

    /**
     * 获取市场渠道编号
     *
     * @return market - 市场渠道编号
     */
    public String getMarket() {
        return market;
    }

    /**
     * 设置市场渠道编号
     *
     * @param market 市场渠道编号
     */
    public void setMarket(String market) {
        this.market = market;
    }
}