package com.xiaoniu.walking.web.core.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * icon对象
 *
 * @author lihoujing
 * @date 2019/9/25 23:55
 */
@Data
public class BottonIconBO implements Serializable {
    private static final long serialVersionUID = 88814252969229836L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 一级icon名称
     */
    private String preIconName;

    /**
     * 二级icon名称
     */
    private String iconName;

    /**
     * app名称：1-爱步行
     */
    private Integer appName;

    /**
     * app版本
     */
    private String appVersion;


    /**
     * 1-有效，2-无效
     */
    private Integer state;

    /**
     * 点击前icon图片
     */
    private String preIconImg;

    /**
     * 点击后icon图片
     */
    private String aftIconImg;

    /**
     * 跳转链接
     */
    private String linkUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 创建人
     */
    private String createMan;

    /**
     * 修改人
     */
    private String modifyMan;

    /**
     * 备注
     */
    private String remark;

    /**
     * icon位置
     */
    private Integer position;


    private String createTimeStr;

    /**
     * 市场渠道
     */
    private String[] market;

    /**
     * 版本作用范围：1-大于，2-大于等于，3-等于，4-小于，5-小于等于，6-不等于
     */
    private Integer rangeVersion;
}
