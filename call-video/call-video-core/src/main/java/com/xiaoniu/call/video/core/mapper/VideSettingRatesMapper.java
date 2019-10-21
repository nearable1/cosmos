package com.xiaoniu.call.video.core.mapper;

import com.xiaoniu.call.video.api.bo.VideoSettingRatePageBO;
import com.xiaoniu.call.video.api.vo.VideoSettingRatesVO;
import com.xiaoniu.call.video.core.entity.VideoSettingRates;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideSettingRatesMapper extends Mapper<VideoSettingRates> {

    /**
     * 查询是否存在
     *
     * @param videoNumber
     * @return
     */
    VideoSettingRates findByVideoNumber(String videoNumber);

    /**
     * 更新
     *
     * @param videoSettingRates
     * @return
     */
    int updateByVideoNumber(VideoSettingRates videoSettingRates);

    /**
     * 插入
     *
     * @return
     */
    int insertNew(VideoSettingRates videoSettingRates);

    /**
     * 分页查询
     *
     * @param videoSettingRatePageBO
     * @return
     */
    List<VideoSettingRatesVO> pageList(VideoSettingRatePageBO videoSettingRatePageBO);
}