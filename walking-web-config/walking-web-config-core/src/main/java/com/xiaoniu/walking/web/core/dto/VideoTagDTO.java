package com.xiaoniu.walking.web.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class VideoTagDTO {
    /**
     * 标签编号
     */
    private String tagNumber;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 所属视频列表
     */
    private List<VideoDTO> videos;
}