package com.xiaoniu.walking.web.core.model.auto;

import javax.persistence.*;

@Table(name = "bottom_icon_market")
public class BottomIconMarket {
    /**
     * 主键编号
     */
    @Id
    private Integer id;

    /**
     * icon主键编号
     */
    @Column(name = "icon_id")
    private Integer iconId;

    /**
     * 市场渠道编号
     */
    private String market;

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
     * 获取icon主键编号
     *
     * @return icon_id - icon主键编号
     */
    public Integer getIconId() {
        return iconId;
    }

    /**
     * 设置icon主键编号
     *
     * @param iconId icon主键编号
     */
    public void setIconId(Integer iconId) {
        this.iconId = iconId;
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