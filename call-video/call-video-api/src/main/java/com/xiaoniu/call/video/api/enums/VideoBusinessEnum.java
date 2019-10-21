package com.xiaoniu.call.video.api.enums;

import com.xiaoniu.architecture.commons.api.IBusinessEnum;
import com.xiaoniu.architecture.commons.api.IEnum;

/**
 * 视频业务枚举
 *
 * @author wuwen
 * @date 2019-07-02 15:56
 */
public enum VideoBusinessEnum implements IBusinessEnum<String> {
    ALREADY_LIKE_VIDEO("V1000", "您已经点击过喜欢了", "您已经点击过喜欢了"),
    LIKE_VIDEO_FAILURE("V1001", "视频喜欢数增加失败", "喜欢视频失败"),
    CANCEL_VIDEO_LIKE("V1002", "您已取消喜欢视频", "您已取消喜欢视频"),
    DELETE_LIKE_RECORD_FAILURE("V1003", "删除喜欢的视频失败", "取消视频喜欢失败"),
    VIDEO_LIKE_NUMBER_REDUCTION_FAILURE("V1004", "视频喜欢数减少失败", "取消视频喜欢失败"),
    VIDEO_SHARING_FAILED("V1005", "视频分享数增加失败", "视频分享失败"),
    VIDEO_PLAYBACK_FAILED("V1006", "视频播放数增加失败", "视频播放失败"),
    SET_CALLER_FAILED("V1007", "视频设置来电秀数增加失败", "来电秀设置失败"),
    SET_CALLER_RINGTONE_FAILED("V1008", "视频设置来电铃声数增加失败", "来电铃声设置失败"),
    VIDEO_DOES_NOT_EXIST("V1009", "视频信息不存在", "视频信息不存在"),
    LAO_WANG_VIDEO_PLAYBACK_FAILED("V1010", "老王视频播放数增加失败", "视频播放失败"),
    SET_WALLPAPER_FAILED("V1011", "视频设置壁纸秀数增加失败", "壁纸秀设置失败"),
    SET_SCREENSAVER_FAILED("V1012", "视频设置屏保数增加失败", "屏保秀设置失败"),
    SET_RINGTONESHOW_FAILED("V1013", "视频设置彩铃秀数增加失败", "彩铃秀设置失败")
    ;

    private String value;
    private String desc;
    private String businessDesc;

    VideoBusinessEnum(String value, String desc, String businessDesc) {
        this.value = value;
        this.desc = desc;
        this.businessDesc = businessDesc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getBusinessDesc() {
        return businessDesc;
    }

    @Override
    public boolean matches(String value) {
        return this.value.equals(value);
    }

    @Override
    public boolean matches(IEnum<String> iEnum) {
        if (null == iEnum) {
            return false;
        }
        return matches(iEnum.getValue());
    }
}
