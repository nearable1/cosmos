package com.xiaoniu.call.video.core.service;

import com.xiaoniu.call.video.core.enums.AudioBehaviorTypeEnum;

/**
 * 音频行为记录
 *
 * @author liuyinkai
 * @date 2019-07-25
 */
public interface AudioBehaviorService {

    /**
     * 添加数据
     *
     * @param audioNumber
     * @param audioBehaviorTypeEnum
     */
    void recordAudioBehavior(String audioNumber, AudioBehaviorTypeEnum audioBehaviorTypeEnum);
}
