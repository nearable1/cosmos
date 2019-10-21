package com.xiaoniu.call.video.api.enums;

import com.xiaoniu.architecture.commons.api.IBusinessEnum;
import com.xiaoniu.architecture.commons.api.IEnum;

/**
 * 音频业务枚举
 *
 * @author liuyinkai
 * @date 2019-07-24 21:20
 */
public enum AudioBusinessEnum implements IBusinessEnum<String> {
    INCREASE_AUDIO_PLAY_NUM("A1000", "增加音频试听次数", "增加音频试听次数"),
    INCREASE_AUDIO_PLAY_NUM_FAIL("A1001", "增加音频试听次数失败", "增加音频试听次数失败"),
    INCREASE_SET_RINGTONE_NUM("A1002", "增加设置彩铃数", "增加设置彩铃数"),
    INCREASE_SET_RINGTONE_NUM_FAIL("A1003", "增加设置彩铃数失败", "增加设置彩铃数失败"),
    AUDIO_CATEGORY_REPEAT("A1004","后台要更改的音频分类已存在该歌曲","当前音频已在该分类存在"),
    ;

    private String value;
    private String desc;
    private String businessDesc;

    AudioBusinessEnum(String value, String desc, String businessDesc) {
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