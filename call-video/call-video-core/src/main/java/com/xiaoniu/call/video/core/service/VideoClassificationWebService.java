package com.xiaoniu.call.video.core.service;

import com.xiaoniu.call.video.api.dto.VideoClassificationWebDTO;

import java.util.List;

/**
 * 视频分类
 *
 * @author wuwen
 * @date 2019-07-16 11:18
 */
public interface VideoClassificationWebService {

    /**
     * 查询列表
     *
     * @return
     */
    List<VideoClassificationWebDTO> selectList();

    /**
     * 修改
     *
     * @param videoClassificationWebDTO
     */
    void update(VideoClassificationWebDTO videoClassificationWebDTO);

    /**
     * 添加
     *
     * @param videoClassificationWebDTO
     */
    void insert(VideoClassificationWebDTO videoClassificationWebDTO);
}
