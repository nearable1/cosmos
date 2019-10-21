package com.xiaoniu.call.video.api.enums;

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
public enum VideoTypeEnum implements IEnum<String> {
    HOME_VIDEO("1", "首页视频"),
    EXPLORATION_VIDEO("2", "探索视频"),
    SMALL_VIDEO("3", "小视频");

    private String value;
    private String desc;


    VideoTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
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

    private static Map<String, VideoTypeEnum> mapping = new HashMap<>();

    static {
        Arrays.stream(VideoTypeEnum.values()).forEach(videoTypeEnum -> mapping.put(videoTypeEnum.getValue(), videoTypeEnum));
    }

    public static VideoTypeEnum resolve(int value) {
        return mapping.get(value);
    }
}
