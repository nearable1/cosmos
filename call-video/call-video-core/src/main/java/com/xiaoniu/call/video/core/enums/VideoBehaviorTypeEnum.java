package com.xiaoniu.call.video.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 视频类型
 *
 * @author wuwen
 * @date 2019-07-02 17:44
 */
public enum VideoBehaviorTypeEnum implements IEnum<Integer> {
    
    NUMBER_OF_FAVORITES(1, "收藏数"),
    FORWARDING_NUMBER(2, "转发数"),
    VIEWS(3, "播放数"),
    SET_TO_TROUBLE(4, "设为闹数"),
    SET_AS_THE_NUMBER_OF_CALLS(5, "设为来电秀数"),
    CANCEL_COLLECTION(6, "取消收藏"),
    SET_WALLPAPER(7, "设置壁纸秀"),
    SET_SCREENSAVER(8, "设置屏保"),
    SET_RINGTONESHOW(9, "设置彩铃");
    private Integer value;
    private String desc;


    VideoBehaviorTypeEnum(Integer value, String desc) {
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

    private static Map<Integer, VideoBehaviorTypeEnum> mapping = new HashMap<>();

    static {
        Arrays.stream(VideoBehaviorTypeEnum.values()).forEach(videoTypeEnum -> mapping.put(videoTypeEnum.getValue(), videoTypeEnum));
    }

    public static VideoBehaviorTypeEnum resolve(int value) {
        return mapping.get(value);
    }
}
