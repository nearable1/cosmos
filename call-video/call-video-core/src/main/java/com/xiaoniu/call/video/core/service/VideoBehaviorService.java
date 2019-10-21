package com.xiaoniu.call.video.core.service;

import com.xiaoniu.call.video.core.enums.VideoBehaviorTypeEnum;

/**
 * 视频行为记录
 *
 * @author wuwen
 * @date 2019-07-18 22:27
 */
public interface VideoBehaviorService {

    /**
     * 添加数据
     *
     * @param videoNumber
     * @param videoBehaviorType
     */
    void inster(String videoNumber, VideoBehaviorTypeEnum videoBehaviorType);
}
