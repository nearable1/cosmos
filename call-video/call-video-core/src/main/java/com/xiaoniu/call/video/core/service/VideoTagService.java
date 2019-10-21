package com.xiaoniu.call.video.core.service;

import com.xiaoniu.call.video.core.dto.VideoTagDTO;

import java.util.List;
import java.util.Map;

/**
 * 视频标签
 *
 * @author wuwen
 * @date 2019-07-04 19:30
 */
public interface VideoTagService {

    /**
     * 查询所属列表和视频
     *
     * @return
     */
    List<VideoTagDTO> selectListAndVideo();

    Map<String, String> tagMap();
}
