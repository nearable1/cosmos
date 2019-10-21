package com.xiaoniu.call.video.core.service;

import com.xiaoniu.call.video.core.entity.VideoSettingRates;

/**
 * 视频设置率
 *
 * @author liuyinkai
 * @date 2019-08-29
 */
public interface VideoSettingRatesService {

    /**
     * 查询
     *
     * @return
     */
    VideoSettingRates findByVideoNumber(String videoNumber);

    /**
     * 更新
     *
     * @return
     */
    int updateByVideoNumber(VideoSettingRates videoSettingRates);

    /**
     * 插入
     *
     * @return
     */
    int insertNew(VideoSettingRates videoSettingRates);

}
