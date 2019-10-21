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
public class VideoWebDTO implements Serializable {

    private static final long serialVersionUID = -1502374083359059389L;

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
     * 类别编号
     */
    private String categoryNumber;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 封面
     */
    private String videoCover;

    /**
     * 视频地址
     */
    private String videoAddress;

    /**
     * GIF地址
     */
    private String gifAddress;

    /**
     * 音频地址
     */
    private String audioAddress;

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
     * 设为闹钟数
     */
    private Integer alarmNumber;

    /**
     * 设为来电秀数
     */
    private Integer callNumber;

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
     * 设为屏保数
     */
    private Integer screenSaverNumber;

    /**
     * 设为壁纸数
     */
    private Integer wallpaperNumber;

    /**
     * 设为彩铃数
     */
    private Integer ringtoneShowNumber;

    /**
     * 标签列表
     */
    private List<String> videoDbTags;

    /**
     * 标签列表
     */
    private List<String> videoTypeTags;

    /**
     * 标签列表-翻译
     */
    private String videoTypeTagsStr;

    /**
     * 运营推荐标签
     */
    private List<String> videoDbOperTags;

    /**
     * 来电秀设置率
     */
    private String callShowSettingRate;

    /**
     * 壁纸设置率
     */
    private String wallpaperSettingRate;

    /**
     * 彩铃设置率
     */
    private String ringtoneSettingRate;
}
