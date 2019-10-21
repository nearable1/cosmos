package com.xiaoniu.call.video.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 视频分页返回数据结构
 *
 * @author wuwen
 * @date 2019-07-15 16:50
 */
@Getter
@Setter
@ToString
public class VideoPyxWebDTO implements Serializable {

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
     * 视频地址
     */
    private String videoAddress;

    /**
     * 视频来源(1-皮皮虾)
     */
    private Integer videoSource;

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
     * 修改时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 收藏数
     */
    private Integer collectionNumberReal;

    /**
     * 转发数
     */
    private Integer forwardNumberReal;

    /**
     * 播放数
     */
    private Integer viewReal;

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
