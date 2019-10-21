package com.xiaoniu.call.video.api.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 视频设置率
 *
 * @author :LiuYinkai
 * @date :2019-08-27 13:57.
 */
@Getter
@Setter
@ToString
public class VideoSettingRatesVO implements Serializable {

    /**
     * 视频编号
     */
    private String videoNumber;

    /**
     * 来电秀设置率
     */
    private BigDecimal callShowSettingRate;

    /**
     * 壁纸设置率
     */
    private BigDecimal wallpaperSettingRate;

    /**
     * 铃声设置率
     */
    private BigDecimal ringtoneSettingRate;


}
