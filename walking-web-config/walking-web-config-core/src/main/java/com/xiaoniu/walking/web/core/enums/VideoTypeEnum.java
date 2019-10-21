package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 视频位置类型
 *
 * @author liuyinkai
 * @date 2019-08-26
 */
public enum VideoTypeEnum implements IEnum<Integer> {
    HOMEPAGE_VIDEO(1, "首页视频"),
    EXPLORE_VIDEO(2, "探索视频"),
    MINI_VIDEO(3, "小视频"),
    ;

    private Integer value;
    private String desc;

    VideoTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
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

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    private static Map<Integer, VideoTypeEnum> mapping = new HashMap<>();

    static {
        Arrays.stream(VideoTypeEnum.values()).forEach(videoTypeEnum -> mapping.put(videoTypeEnum.getValue(), videoTypeEnum));
    }
    public static VideoTypeEnum resolve(int value) {
        return mapping.get(value);
    }
}