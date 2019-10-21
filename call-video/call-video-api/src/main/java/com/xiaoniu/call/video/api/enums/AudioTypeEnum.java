package com.xiaoniu.call.video.api.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 音频分类
 *
 * @author liuyinkai
 * @date 2019-07-24 21:44
 */
public enum AudioTypeEnum implements IEnum<Integer> {
    WEEK_HOT(1, "一周最火"),
    TIK_TOK_POP(2, "抖音神曲"),
    NET_FRIEND_RECOMMENDED(3, "网友推荐"),
    NOSTALGIC_CLASSIC(4, "那些年的"),
    INTERNET_POP(5, "网络流行"),
    CHINESE_CLASSIC_SONGS(6, "华语金曲"),
    FUNNY_SONG(7, "个性搞笑"),
    DJ_DANCE_MUSIC(8, "DJ舞曲"),
    ELEGANT_ANTIQUE(9, "优雅古风"),
    POPULAR_RECOMMENDED(10, "人气推荐");

    private Integer value;
    private String desc;


    AudioTypeEnum(Integer value, String desc) {
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

    private static Map<Integer, AudioTypeEnum> mapping = new HashMap<>();

    static {
        Arrays.stream(AudioTypeEnum.values()).forEach(videoTypeEnum -> mapping.put(videoTypeEnum.getValue(), videoTypeEnum));
    }

    public static AudioTypeEnum resolve(int value) {
        return mapping.get(value);
    }
}
