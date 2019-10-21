package com.xiaoniu.call.video.api.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author :LiuYinkai
 * @date :2019-08-02 17:54.
 */
@Setter
@Getter
@ToString
public class AudioVO implements Serializable {
    /**
     * 音频编号
     */
    private String audioNumber;

    /**
     * 音频标题
     */
    private String title;

    /**
     * 类别编号(AC001:一周最火, AC002:抖音神曲, AC003:网友推荐, AC004:那些年的, AC005:网络流行, AC006:华语金曲,
     * AC007:个性搞笑, AC008:DJ舞曲, AC009:优雅古风, AC010:人气推荐, AC011:新歌榜, AC012: 抖音榜, AC013: 热门榜)
     */
    private String categoryNumber;

    /**
     * 后台编辑框变更的分类编码
     */
    private String tempCategoryNumber;

    /**
     * 音频位置（1-首页彩铃秀 ）
     */
    private Integer audioType;

    /**
     * 演唱者
     */
    private String singer;

    /**
     * 封面
     */
    private String audioCover;

    /**
     * 音频地址
     */
    private String audioAddress;

    /**
     * 音频来源(待产品经理和爬虫工程师确定)
     */
    private Integer audioSource;

    /**
     * 渠道来源原始试听播放数
     */
    private Integer channelListenNumber;

    /**
     * 我平台真实播放数
     */
    private Integer realListenNumber;

    /**
     * 3000-5000随机虚拟试听播放数
     */
    private Integer virtualListenNumber;

    /**
     * 设为铃声数
     */
    private Integer setRingToneNumber;

    /**
     * 标签列表--（用于排行榜分类：AT001-热门榜，AT002-新歌榜，AT003-抖音榜）
     */
    private List<String> tags;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 状态(0-下线、1-上线)
     */
    private Boolean status;

    /**
     * 0-审核未通过  1-审核通过
     */
    private Integer check;

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
     * 彩铃设置率
     */
    private String ringtoneSettingRate;
}
