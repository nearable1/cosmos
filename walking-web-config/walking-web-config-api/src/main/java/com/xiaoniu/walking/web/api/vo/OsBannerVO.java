package com.xiaoniu.walking.web.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lihoujing
 * @date 2019/6/5 10:24
 */
@Data
public class OsBannerVO implements Serializable {
    private static final long serialVersionUID = 11223424233234534L;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String imageUrl;


    /**
     * 点击banner跳转地址
     */
    private String forwardUrl;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;




    /**
     * banner位置
     */
    private String bannerPosition;

    /**
     * 状态：1-开启，2-关闭
     */
    private Integer state;

    /**
     * app名称
     */
    private Integer appName;



}
