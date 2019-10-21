package com.xiaoniu.call.video.core.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Video_pyx implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频编号
     */
    private String videoNumber;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频类型(1-首页视频、2-探索视频、3-小视频)
     */
    private Integer videoType;

    /**
     * 封面
     */
    private String videoCover;

    /**
     * 视频地址`1
     */
    private String videoAddress;

    /**
     * 视频来源(1-动态壁纸、2-豹来电、3-嗨来电、4-来电秀、5-火山)
     */
    private Integer videoSource;

    /**
     * 收藏数
     */
    private Integer collectionNumber;

    /**
     * 转发数
     */
    private Integer forwardNumber;

    /**
     * 播放数
     */
    private Integer view;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 状态(0-下线、1-上线)
     */
    private Boolean status;

    /**
     * 发布时间
     */
    private Long releaseTime;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 时长
     */
    private String duration;

    /**
     * 宽
     */
    private Integer width;
    /**
     * 高
     */
    private Integer height;


}
