package com.xiaoniu.call.video.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 音频行为类型
 *
 * @author liuyinkai
 * @date 2019-07-25 17:26
 */
public enum AudioBehaviorTypeEnum implements IEnum<Integer> {

    NUMBER_OF_FAVORITES(1, "收藏数"),
    FORWARDING_NUMBER(2, "转发数"),
    PLAY(3, "播放数"),
    SET_TO_RINGTONE(4, "设为铃声"),
    CANCEL_COLLECTION(5, "取消收藏");
    private Integer value;
    private String desc;


    AudioBehaviorTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }


    @Override
    public boolean matches(Integer value) {
        return this.value.equals(value);
    }

    @Override
    public boolean matches(IEnum<Integer> iEnum) {
        if (null == iEnum) {
            return false;
        }
        return matches(iEnum.getValue());
    }

    private static Map<Integer, AudioBehaviorTypeEnum> mapping = new HashMap<>();

    static {
        Arrays.stream(AudioBehaviorTypeEnum.values()).forEach(videoTypeEnum -> mapping.put(videoTypeEnum.getValue(), videoTypeEnum));
    }

    public static AudioBehaviorTypeEnum resolve(int value) {
        return mapping.get(value);
    }
}
