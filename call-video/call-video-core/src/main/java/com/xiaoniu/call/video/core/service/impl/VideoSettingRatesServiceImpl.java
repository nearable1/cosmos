package com.xiaoniu.call.video.core.service.impl;

import com.xiaoniu.call.video.core.entity.VideoSettingRates;
import com.xiaoniu.call.video.core.mapper.VideSettingRatesMapper;
import com.xiaoniu.call.video.core.service.VideoSettingRatesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 视频管理端业务实现
 *
 * @author wuwen
 * @date 2019-07-15 21:16
 */
@Service
@Log4j2
public class VideoSettingRatesServiceImpl implements VideoSettingRatesService {

    @Autowired
    private VideSettingRatesMapper videSettingRatesMapper;

    @Override
    public VideoSettingRates findByVideoNumber(String videoNumber) {
        return videSettingRatesMapper.findByVideoNumber(videoNumber);
    }

    @Override
    public int updateByVideoNumber(VideoSettingRates videoSettingRates) {
        return videSettingRatesMapper.updateByVideoNumber(videoSettingRates);
    }

    @Override
    public int insertNew(VideoSettingRates videoSettingRates) {
        return videSettingRatesMapper.insertNew(videoSettingRates);
    }
}
