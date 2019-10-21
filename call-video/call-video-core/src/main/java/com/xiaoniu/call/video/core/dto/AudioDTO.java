package com.xiaoniu.call.video.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 音频返回信息
 *
 * @author liuyinkai
 * @date 2019-07-25
 */
@Getter
@Setter
@ToString
public class AudioDTO implements Serializable {

    private static final long serialVersionUID = 1040560877154001945L;

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
     * AC007:个性搞笑, AC008:DJ舞曲, AC009:优雅古风, AC010:人气推荐)
     */
    private String categoryNumber;

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
     * 我平台真实播放数
     */
    private Integer realListenNumber;

    /**
     * 3000-5000随机虚拟试听播放数
     */
    private Integer virtualListenNumber;

    /**
     * 前端展示的播放数 = realListenNumber + virtualListenNumber
     */
    private Integer showListenNumber;

    /**
     * 设为铃声数
     */
    private Integer setRingToneNumber;

    /**
     * 标签列表--（用于排行榜分类：AT001-热门榜，AT002-新歌榜，AT003-抖音榜）
     */
    private List<String> tags;




}
